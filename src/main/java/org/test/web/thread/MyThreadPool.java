package org.test.web.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyThreadPool {

    /**
     * 线程池(大池)
     */
    private static final ThreadPoolExecutor bigPool = new ThreadPoolExecutor(50, 100, 2, TimeUnit.MINUTES,
            new LinkedBlockingDeque<>(10000),
            new ThreadPoolExecutor.CallerRunsPolicy());
    /**
     * 线程池(小池)
     */
    private static final ExecutorService smallPool = new ThreadPoolExecutor(25, 50, 5, TimeUnit.MINUTES,
            new LinkedBlockingDeque<>(100),
            new RunsInBigPoolPolicy());

    private static Logger logger = LoggerFactory.getLogger(MyThreadPool.class);

    static {
        //大池的核心线程数可以被回收
        bigPool.allowCoreThreadTimeOut(true);
    }

    /**
     * 异步执行
     *
     * @param runnable
     */
    public static void execute(Runnable runnable) {
        if (runnable == null) {
            return;
        }

        if (smallPool != null && !smallPool.isShutdown()) {
            smallPool.execute(runnable);
        } else if (bigPool != null && !bigPool.isShutdown()) {
            bigPool.execute(runnable);
        } else {
            runnable.run(); //主线程直接调用

            logger.error("线程池异常，主线程直接执行了任务...");
        }
    }


    /**
     * <br>任务运行在大池里的策略</br>
     * 由于CallerRunsPolicy会造成主线程阻塞，所以把小池处理不了的任务丢到大池里执行;
     */
    public static class RunsInBigPoolPolicy implements RejectedExecutionHandler {

        public RunsInBigPoolPolicy() {
        }

        @Override
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
            if (runnable == null) {
                return;
            }

            if (bigPool != null && !bigPool.isShutdown()) {
                bigPool.execute(runnable);

                logger.warn("a task is switched into big pool...");
            } else {
                runnable.run(); //主线程直接调用

                logger.error("线程池异常，主线程直接执行了任务...");
            }

        }
    }
}
