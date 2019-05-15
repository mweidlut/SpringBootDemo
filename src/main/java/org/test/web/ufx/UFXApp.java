package org.test.web.ufx;

import com.hundsun.t2sdk.common.core.context.ContextUtil;
import com.hundsun.t2sdk.impl.client.T2Services;
import com.hundsun.t2sdk.interfaces.IClient;
import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.hundsun.t2sdk.interfaces.share.dataset.IDataset;
import com.hundsun.t2sdk.interfaces.share.dataset.IDatasets;
import com.hundsun.t2sdk.interfaces.share.event.EventReturnCode;
import com.hundsun.t2sdk.interfaces.share.event.EventTagdef;
import com.hundsun.t2sdk.interfaces.share.event.EventType;
import com.hundsun.t2sdk.interfaces.share.event.IEvent;

import java.security.Security;
import java.util.logging.Logger;

public class UFXApp {
    static {
        Security.setProperty("jdk.tls.disabledAlgorithms", "");
        //System.setProperty("jdk.tls.client.protocols", "TLSv1,TLSv1.1,TLSv1.2,SSLv3");
    }

    private static Logger logger = Logger.getLogger("UFXApp");
    private static final int TIMEOUT = 10000;
//    private static final String CONFIG_FILE_PATH = "/home/archforce/BOS/da/conf/t2sdk-config.xml"; //"D:\\workspace_4_idea\\SpringBootDemo\\src\\main\\resources\\config\\t2sdk-config.xml"
    private static final String CONFIG_FILE_PATH = "D:\\workspace_4_idea\\SpringBootDemo\\src\\main\\resources\\config\\t2sdk-config.xml";

    private T2Services server = T2Services.getInstance();
    private String session = null;

//	private int subscribeid;
//	private ISubscriber subscriber;
    private IClient client = null;

    public static void main(String[] args) {
        UFXApp app = new UFXApp();
        try {
            app.Connect();
            app.login();

            String entrustno = app.entrust();

            app.entrustQry(entrustno);

            app.dealQry(entrustno);

            app.withdraw(entrustno);

        } catch (Exception e) {
            try {
                app.DisConnect();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    /**
     * 链接后台 订阅主推消息
     *
     * @throws Exception
     */
    public void Connect() throws Exception {
        server.setT2sdkConfigString(CONFIG_FILE_PATH);

        logger.info("链接后台服务 订阅主推消息");
        server.init();
        server.start();

        Thread.sleep(1000);

        client = server.getClient("as_ufx");
    }

    /**
     * 取消订阅 断开链接
     *
     * @throws Exception
     */
    public void DisConnect() throws Exception {
        logger.info("后台链接断开");
        server.stop();
    }

    /**
     * 登陆获取session
     *
     * @throws Exception
     */
    public void login() throws Exception {
        logger.info("开始登陆");
        IDatasets result = CallSerivce(10001, UFXUtil.GetLoginPack());
        IDataset head = result.getDataset(0);
        int errCode = head.getInt("ErrorCode");
        if (errCode != 0) {
            throw new Exception(head.getString("ErrorMsg"));
        } else {
            if (head.getInt("DataCount") != 0) {
                IDataset biz = result.getDataset(1);
                session = biz.getString("user_token");
            }
        }
        logger.info("登陆成功获取Session[" + session + "]");
    }

    /**
     * 普通买卖委托(单笔股票买卖)
     */
    public String entrust() throws Exception {
        logger.info("委托下达");
        IDatasets result = CallSerivce(91001, UFXUtil.GetEntrustPack(session));
        UFXUtil.PrintMsg(result);
        IDataset biz = result.getDataset(1);
        return biz.getString("entrust_no");
    }

    /**
     * 委托查询
     */
    public void entrustQry(String enrtustno) throws Exception {
        logger.info("委托查询");
        //同步调用
        //IDatasets result = CallSerivce(32001,
        //UFXUtil.GetEntrustQryPack(session, enrtustno));
        //UFXUtil.PrintMsg(result);
        //异步调用
        CallSerivceA(32001, UFXUtil.GetEntrustQryPack(session, enrtustno));
    }

    /**
     * 成交查询
     */
    public void dealQry(String enrtustno) throws Exception {
        logger.info("成交查询");
        IDatasets result = CallSerivce(33001,
                UFXUtil.GetDealQryPack(session, enrtustno));
        UFXUtil.PrintMsg(result);
    }

    /**
     * 撤单
     */
    public void withdraw(String enrtustno) throws Exception {
        logger.info("委托撤单");
        IDatasets result = CallSerivce(91101,
                UFXUtil.GetWithdrawPack(session, enrtustno));
        UFXUtil.PrintMsg(result);
    }

    /**
     * 同步调用
     *
     * @param funcno
     * @param dataset
     * @return
     * @throws T2SDKException
     */
    public IDatasets CallSerivce(int funcno, IDataset dataset)
            throws T2SDKException {
        IDatasets result = null;

        IEvent event = ContextUtil.getServiceContext().getEventFactory()
                .getEventByAlias(String.valueOf(funcno), EventType.ET_REQUEST);
        event.putEventData(dataset);
        IEvent rsp = client.sendReceive(event, TIMEOUT);
        // 先判断返回值
        if (rsp.getReturnCode() != EventReturnCode.I_OK) { // 返回错误
            throw new T2SDKException(rsp.getErrorNo(), rsp.getErrorInfo());
        } else {
            result = rsp.getEventDatas();
        }
        return result;
    }

    /**
     * 异步调用
     *
     * @param funcno
     * @param dataset
     * @return
     * @throws T2SDKException
     */
    public void CallSerivceA(int funcno, IDataset dataset)
            throws T2SDKException {
        IEvent event = ContextUtil.getServiceContext().getEventFactory()
                .getEventByAlias(String.valueOf(funcno), EventType.ET_REQUEST);
        event.putEventData(dataset);
        event.setIntegerAttributeValue(EventTagdef.TAG_SENDERID, 5);
        client.send(event);
        // 先判断返回值
    }

}
