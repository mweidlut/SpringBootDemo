package org.test.web.batch;

import org.springframework.batch.core.configuration.ListableJobLocator;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * User: Thinkpad
 * Date: 2019/5/6 21:28
 */
@Configuration
public class BatchConfig {

    @Bean
    public ResourcelessTransactionManager transactionManager(){
        return new ResourcelessTransactionManager();
    }

    @Bean
    public MapJobRepositoryFactoryBean jobRepository(@Autowired PlatformTransactionManager transactionManager){
        MapJobRepositoryFactoryBean repositoryFactoryBean = new MapJobRepositoryFactoryBean();
        repositoryFactoryBean.setTransactionManager(transactionManager);
        return repositoryFactoryBean;
    }

    @Bean
    public SimpleJobLauncher jobLauncher(@Autowired JobRepository jobRepository){
        SimpleJobLauncher launcher = new SimpleJobLauncher();
        launcher.setJobRepository(jobRepository);
        return launcher;
    }

    @Bean
    public ListableJobLocator listableJobLocator(){
        return new MapJobRegistry();
    }
}
