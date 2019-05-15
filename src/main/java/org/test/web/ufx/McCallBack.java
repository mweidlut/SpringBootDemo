package org.test.web.ufx;

import java.util.logging.Logger;

import com.hundsun.mcapi.interfaces.ISubCallback;
import com.hundsun.mcapi.subscribe.MCSubscribeParameter;
import com.hundsun.t2sdk.interfaces.share.event.IEvent;

public class McCallBack  implements ISubCallback{
	public static int g_iCount = 0;
	private Logger logger = Logger.getLogger("McCallBack");

    @Override
    public void OnReceived(String topicName, IEvent event) {
        // TODO Auto-generated method stub
    	logger.info("收到消息主推");
    	g_iCount++;
    	//if(g_iCount%100 == 0)
    	logger.info("Count:"+g_iCount);
    	logger.info(topicName);
        //DatasetService.printDataset(event.getEventDatas().getDataset(0));
        UFXUtil.PrintMsg(event.getEventDatas());
    }

    @Override
    public void OnRecvTickMsg(MCSubscribeParameter param,String tickMsgInfo) {
        // TODO Auto-generated method stub
        
    }

}
