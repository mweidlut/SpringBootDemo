package org.test.web.concurrent;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * User: ROOT
 * Date: 2017/12/3 18:14
 */

public class SemaphoreTest {

    @Test
    public void semaphoreTest() {
        // 线程池
        ExecutorService exec = Executors.newCachedThreadPool();
        // 只能5个线程同时访问
        final Semaphore semp = new Semaphore(5);



        // 模拟20个客户端访问
        for (int index = 0; index < 20; index++) {
            final int NO = index;
            Runnable run = new Runnable() {
                public void run() {
                    try {
                        // 获取许可
                        semp.acquire();
                        System.out.println("Accessing: " + NO);

                        Thread.sleep((long) (Math.random() * 10000));

                        // 访问完后，释放 ，如果屏蔽下面的语句，则在控制台只能打印5条记录，之后线程一直阻塞
                        semp.release();
                    } catch (InterruptedException e) {
                    }
                }
            };

            exec.execute(run);
        }
        // 退出线程池
        exec.shutdown();
    }
}