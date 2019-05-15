package org.test.web.batch;

import org.springframework.batch.item.file.transform.LineAggregator;

/**
 * User: Thinkpad
 * Date: 2019/5/6 21:38
 */
public class HelloLineAggregator implements LineAggregator<DeviceCommand> {

    @Override
    public String aggregate(DeviceCommand deviceCommand) {

        StringBuffer sb = new StringBuffer();

        sb.append(deviceCommand.getId());

        sb.append(",");

        sb.append(deviceCommand.getStatus());

        return sb.toString();
    }

}

