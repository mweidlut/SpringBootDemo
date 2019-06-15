package org.test.web.batch;

import org.springframework.batch.item.file.LineMapper;

/**
 * User: Thinkpad
 * Date: 2019/5/6 21:36
 */
public class SimpleLineMapper implements LineMapper<FileContent> {

    @Override
    public FileContent mapLine(String line, int lineNumber) throws Exception {
        // 逗号分割每一行数据
        String[] args = line.split(",");

        FileContent content = new FileContent();
        content.setId(args[0]);
        content.setStatus(args[1]);

        return content;
    }

}
