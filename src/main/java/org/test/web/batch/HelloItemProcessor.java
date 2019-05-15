package org.test.web.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * User: Thinkpad
 * Date: 2019/5/6 21:37
 */
public class HelloItemProcessor implements ItemProcessor<DeviceCommand, DeviceCommand> {

    private static Logger logger = LoggerFactory.getLogger(HelloItemProcessor.class);

    @Override
    public DeviceCommand process(DeviceCommand deviceCommand) throws Exception {

        // 模拟下发命令给设备
        logger.info("send command to device, id={}", deviceCommand.getId());

        // 更新命令状态
        deviceCommand.setStatus("SENT");

        // 返回命令对象
        return deviceCommand;
    }

}