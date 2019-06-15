package org.test.web.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * User: Thinkpad
 * Date: 2019/5/6 21:40
 */
//@Service
public class SpringBatchDemoBootstrap implements InitializingBean {

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PlatformTransactionManager transactionManager;


    @Override
    public void afterPropertiesSet() throws Exception {

        // 创建reader
        FlatFileItemReader<FileContent> fileReader = new FlatFileItemReader<>();
        fileReader.setResource(new FileSystemResource("src/main/resources/batch-data.csv"));
        fileReader.setLineMapper(new SimpleLineMapper());

        // 创建processor
        SimpleItemProcessor itemProcessor = new SimpleItemProcessor();

        // 创建writer
        FlatFileItemWriter<FileContent> fileWriter = new FlatFileItemWriter<>();
        fileWriter.setResource(new FileSystemResource("src/main/resources/batch-data2.csv"));
        fileWriter.setLineAggregator(new SimpleLineAggregator());

        // 创建Step
        StepBuilderFactory stepBuilderFactory = new StepBuilderFactory(jobRepository, transactionManager);
        Step step = stepBuilderFactory.get("step")
                .<FileContent, FileContent>chunk(1)
                .reader(fileReader)       // 读操作
                .processor(itemProcessor) // 处理操作
                .writer(fileWriter)       // 写操作
                .build();

        // 创建Job
        JobBuilderFactory jobBuilderFactory = new JobBuilderFactory(jobRepository);
        Job job = jobBuilderFactory.get("job")
                .start(step)
                .build();

        // 启动任务
        jobLauncher.run(job, new JobParameters());
    }

}
