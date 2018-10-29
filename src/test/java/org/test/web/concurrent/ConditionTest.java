package org.test.web.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * test java.util.concurrent.locks.Condition
 * User: weimeng
 * Date: 2018/10/29 10:14
 */
public class ConditionTest {

    public static void main(String[] args) {
        final ReentrantLock reentrantLock = new ReentrantLock();
        final Condition condition = reentrantLock.newCondition();

        Thread threadA = new Thread(() -> {
            try {
                reentrantLock.lock();
                System.out.println("A要等一个新信号");
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("A得到一个信号！！");
            reentrantLock.unlock();
        }, "waitThread1");

        threadA.start();


        Thread threadB = new Thread(() -> {
            reentrantLock.lock();
            System.out.println("B拿到锁了");

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("B死睡了三秒钟");

            condition.signalAll();
            System.out.println("B发了一个通知信号！！");
            reentrantLock.unlock();
        }, "signalThread");

        threadB.start();
    }

}
