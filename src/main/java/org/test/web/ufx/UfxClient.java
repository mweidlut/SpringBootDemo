//package org.test.web.ufx;
//
//
//import cn.hutool.json.JSONUtil;
//import com.archforce.atp.da.common.config.LoadDataProperties;
//import com.archforce.atp.da.common.config.UfxProperties;
//import com.archforce.atp.da.common.enums.UfxResponseType;
//import com.archforce.atp.da.common.exception.UfxException;
//import com.archforce.atp.da.common.util.ExceptionUtil;
//import com.archforce.atp.da.common.util.LogUtils;
//import com.archforce.atp.da.common.utils.IpUtil;
//import com.google.common.collect.Sets;
//import com.hundsun.t2sdk.common.core.context.ContextUtil;
//import com.hundsun.t2sdk.common.share.dataset.DatasetService;
//import com.hundsun.t2sdk.impl.client.T2Services;
//import com.hundsun.t2sdk.interfaces.IClient;
//import com.hundsun.t2sdk.interfaces.T2SDKException;
//import com.hundsun.t2sdk.interfaces.core.context.IServiceContext;
//import com.hundsun.t2sdk.interfaces.core.event.IEventFactory;
//import com.hundsun.t2sdk.interfaces.share.dataset.IDataset;
//import com.hundsun.t2sdk.interfaces.share.dataset.IDatasets;
//import com.hundsun.t2sdk.interfaces.share.event.IEvent;
//import java.io.File;
//import java.security.Security;
//import java.util.Objects;
//import java.util.Set;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.DisposableBean;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.stereotype.Service;
//
//@Service
//@EnableConfigurationProperties({UfxProperties.class})
//public class UfxClient
//        implements InitializingBean, DisposableBean
//{
//    @Autowired
//    private UfxProperties ufxProperties;
////    @Autowired
////    private LoadDataProperties loadDataProperties;
//    private T2Services server = T2Services.getInstance();
//    private IClient client = null;
//    private String token = null;
//    private Set<String> notLoginType = Sets.newHashSet(new String[] { UfxResponseType.TOKEN_VALID.getCode(), UfxResponseType.SESSION_NOT_INIT.getCode() });
//
//    public void afterPropertiesSet()
//    {
//        try
//        {
////            if (this.loadDataProperties.getDataFromFile().booleanValue())
////            {
////                LogUtils.info(getClass(), "国信ufx接口", "国信da上场服务将从'{}{functionID}.json'文件中读取数据", new Object[] { this.loadDataProperties.getDataBasePath() });
////                return;
////            }
////            if (this.loadDataProperties.checkUfx().booleanValue())
//            {
//                LogUtils.info(getClass(), "国信ufx接口", "当前查询模式为ufx模式,开始加载ufx");
//
//                initUfxThenLogin(true);
//            }
//        }
//        catch (Exception e)
//        {
//            LogUtils.error(getClass(), "国信ufx接口", "初始化失败，原因：" + ExceptionUtil.getMessage(e));
//        }
//    }
//
//    public void destroy()
//    {
//        try
//        {
//            if (Objects.nonNull(this.server)) {
//                this.server.stop();
//            }
//        }
//        catch (Exception e)
//        {
//            LogUtils.error(getClass(), "国信ufx接口", "停止server时发生异常：" + ExceptionUtil.getMessage(e));
//        }
//    }
//
//    private void initUfxThenLogin(boolean isRetryLogin)
//            throws Exception
//    {
//        long initStart = System.currentTimeMillis();
//        String configPath = this.ufxProperties.getConfigPath();
//        configPath = "D:\\workspace_4_idea\\SpringBootDemo\\src\\main\\resources\\config\\t2sdk-config.xml";
//
//        checkFilePath(configPath);
//
//        this.server.setT2sdkConfigString(configPath);
//        this.server.init();
//
//        this.server.start();
//
//        Thread.sleep(3000L);
//
//        this.client = this.server.getClient("as_ufx");
//        long initEnd = System.currentTimeMillis();
//        LogUtils.info(getClass(), "国信ufx接口", "client初始化耗时: {}ms", new Object[] { Long.valueOf(initEnd - initStart) });
//
//        long loginStart = System.currentTimeMillis();
//        login(isRetryLogin);
//        long loginEnd = System.currentTimeMillis();
//        LogUtils.info(getClass(), "国信ufx接口", "login耗时: {}ms", new Object[] { Long.valueOf(loginEnd - loginStart) });
//    }
//
//    private void login(boolean isRetry)
//            throws Exception
//    {
//        LogUtils.info(getClass(), "国信ufx接口", "ufxClient开始登陆");
//
//        IDatasets result = isRetry ? callServiceWithRetry(this.ufxProperties.getFuncLogin(), buildLoginParam()) : callServiceOnce(this.ufxProperties.getFuncLogin(), buildLoginParam());
//        IDataset head = result.getDataset(0);
//        int errCode = head.getInt("ErrCode");
//        if (errCode != 0) {
//            throw new UfxException(head.getString("ErrMsg"));
//        }
//        if (head.getInt("DataCount") != 0)
//        {
//            IDataset biz = result.getDataset(1);
//            this.token = biz.getString("user_token");
//        }
//        LogUtils.info(getClass(), "国信ufx接口", "登陆成功，获取Session[{}]", new Object[] { this.token });
//    }
//
//    public String getToken()
//    {
//        return this.token;
//    }
//
//    public IDatasets callServiceOnce(String funcno, IDataset dataset)
//            throws Exception
//    {
//        long callServiceOnceStart = System.currentTimeMillis();
//
//        IEvent event = ContextUtil.getServiceContext().getEventFactory().getEventByAlias(funcno, 0);
//        event.putEventData(dataset);
//        IEvent rsp = this.client.sendReceive(event, this.ufxProperties.getTimeout().longValue());
//        long callServiceOnceEnd = System.currentTimeMillis();
//        LogUtils.info(getClass(), "国信ufx接口", "callServiceOnce耗时: {}ms", new Object[] { Long.valueOf(callServiceOnceEnd - callServiceOnceStart) });
//        if (rsp.getReturnCode() == 0) {
//            return rsp.getEventDatas();
//        }
//        throw new UfxException(rsp.getErrorNo(), rsp.getErrorInfo());
//    }
//
//    public IDatasets callServiceWithRetry(String funcno, IDataset dataset)
//            throws Exception
//    {
//        long callServiceWithRetryStart = System.currentTimeMillis();
//
//        IEvent event = ContextUtil.getServiceContext().getEventFactory().getEventByAlias(funcno, 0);
//        event.putEventData(dataset);
//        IDatasets retDatasets = query(event);
//        long callServiceOnceEnd = System.currentTimeMillis();
//        LogUtils.info(getClass(), "国信ufx接口", "callServiceWithRetry耗时: {}ms", new Object[] { Long.valueOf(callServiceOnceEnd - callServiceWithRetryStart) });
//        return retDatasets;
//    }
//
//    static
//    {
//        Security.setProperty("jdk.tls.disabledAlgorithms", "");
//    }
//
//    private IDataset buildLoginParam()
//    {
//        IDataset dataset = DatasetService.getDefaultInstance().getDataset();
//        dataset.addColumn("operator_no");
//        dataset.addColumn("password");
//        dataset.addColumn("mac_address");
//        dataset.addColumn("op_station");
//        dataset.addColumn("ip_address");
//        dataset.addColumn("authorization_id");
//        dataset.appendRow();
//        dataset.updateString("operator_no", this.ufxProperties.getOperatorNo());
//        dataset.updateString("password", this.ufxProperties.getPassword());
//        dataset.updateString("mac_address", StringUtils.isNotBlank(this.ufxProperties.getMacAddress()) ? this.ufxProperties.getMacAddress() : IpUtil.getSystemMac());
//        dataset.updateString("op_station", this.ufxProperties.getOpStation());
//        dataset.updateString("ip_address", StringUtils.isNotBlank(this.ufxProperties.getIpAddress()) ? this.ufxProperties.getIpAddress() : IpUtil.getSystemIP());
//        dataset.updateString("authorization_id", this.ufxProperties.getAuthorizationId());
//        return dataset;
//    }
//
//    private IDatasets query(IEvent event)
//            throws Exception
//    {
//        for (int i = 1; i <= this.ufxProperties.getMaxRetry(); i++)
//        {
//            IEvent rsp;
//            LogUtils.info(getClass(), "国信ufx接口", "开始当前第{}次查询,最大查询重试次数{},参数{}", new Object[] { Integer.valueOf(i), Integer.valueOf(this.ufxProperties.getMaxRetry()), JSONUtil.toJsonStr(event.getEventDatas()) });
//            try
//            {
//                rsp = this.client.sendReceive(event, this.ufxProperties.getTimeout().longValue());
//                LogUtils.info(getClass(), "国信ufx接口", "client.sendReceive success returnCode={}, ErrorNo={}", new Object[] { Integer.valueOf(rsp.getReturnCode()), rsp.getErrorNo() });
//            }
//            catch (T2SDKException e)
//            {
//                LogUtils.info(getClass(), "国信ufx接口", "client.sendReceive failed message {}", new Object[] { ExceptionUtil.getMessage(e) });
//                continue;
//            }
//
//            if (rsp.getReturnCode() == 0) {
//                return rsp.getEventDatas();
//            }
//            if (i == this.ufxProperties.getMaxRetry()) {
//                throw new UfxException(rsp.getErrorNo(), rsp.getErrorInfo());
//            }
//            if (UfxResponseType.TIMEOUT.getCode().equals(rsp.getErrorNo())) {
//                try
//                {
//                    Thread.sleep(1500L);
//                }
//                catch (InterruptedException e)
//                {
//                    LogUtils.warn(getClass(), "国信ufx接口", "InterruptedException{}", new Object[] { e.getMessage() });
//                }
//            } else if (this.notLoginType.contains(rsp.getErrorNo())) {
//                try
//                {
//                    initUfxThenLogin(false);
//                }
//                catch (Exception e)
//                {
//                    LogUtils.warn(getClass(), "国信ufx接口", "token过期，尝试重新初始化异常{}", new Object[] { ExceptionUtil.getMessage(e) });
//                }
//            } else {
//                throw new UfxException(rsp.getErrorNo(), rsp.getErrorInfo());
//            }
//        }
//        throw new UfxException("内部错误");
//    }
//
//    private void checkFilePath(String path)
//            throws Exception
//    {
//        if (StringUtils.isBlank(path)) {
//            throw new UfxException("ufx配置文件地址为空");
//        }
//        if (!StringUtils.endsWithIgnoreCase(path, ".xml")) {
//            throw new UfxException("ufx配置文件不是xml格式");
//        }
//        File file = new File(path);
//        if (!file.exists()) {
//            throw new UfxException("ufx配置文件地址不存在，path = " + path);
//        }
//        if (!file.isFile()) {
//            throw new UfxException("ufx配置不是文件，path = " + path);
//        }
//    }
//}