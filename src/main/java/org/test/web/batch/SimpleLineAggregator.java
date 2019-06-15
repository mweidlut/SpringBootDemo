package org.test.web.batch;

import org.springframework.batch.item.file.transform.LineAggregator;

/**
 * User: Thinkpad
 * Date: 2019/5/6 21:38
 */
public class SimpleLineAggregator implements LineAggregator<FileContent> {

    @Override
    public String aggregate(FileContent content) {
        StringBuffer sb = new StringBuffer();

        sb.append(content.getId());
        sb.append(",");
        sb.append(content.getStatus());

        return sb.toString();
    }

}

