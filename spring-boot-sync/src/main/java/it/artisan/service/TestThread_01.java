package it.artisan.service;

import lombok.SneakyThrows;

/**
 * @author dkp
 * @create 2022-10-30 18:30
 */
public class TestThread_01 {



    /**
     * 创建线程的四种方式：
     * 1.继承Thread类
     */
    public static void main(String[] args) {
        System.out.println("main-------start--------------");

        Thread01 thread01 = new Thread01();
        thread01.start();
        System.out.println("main-------end--------------");
    }
}
class Thread01 extends Thread{
    @SneakyThrows
    @Override
    public void run() {
        Thread.sleep(5000);

        System.out.println(Thread01.currentThread().getName() + ":doing----------");
    }
}