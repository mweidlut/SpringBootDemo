package org.test.web.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * User: Thinkpad
 * Date: 2019/5/6 21:37
 */
public class SimpleItemProcessor implements ItemProcessor<FileContent, FileContent> {

    private static Logger logger = LoggerFactory.getLogger(SimpleItemProcessor.class);

    @Override
    public FileContent process(FileContent content) throws Exception {
        logger.info("开始处理数据, id={}", content.getId());

        // 更新状态
        content.setStatus("DONE");

        return content;
    }

}