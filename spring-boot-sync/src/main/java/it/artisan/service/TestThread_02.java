package it.artisan.service;

import lombok.SneakyThrows;

/**
 * 创建线程的四种方式2：
 * 1.创建一个类实现Runable接口
 * 2.主线程中显示的new Thread(我们自己定义的实现runnable接口的类)
 * 3.启动线程
 */
public class TestThread_02 {
    public static void main(String[] args) {
        System.out.println("main-------start-------");
        RunnableThread02 runnableThread02 = new RunnableThread02();
        Thread thread = new Thread(runnableThread02);
        thread.start();
        System.out.println("main-------end-------");

    }
}

class RunnableThread02 implements Runnable{
    @SneakyThrows
    @Override
    public void run() {
        Thread.sleep(1000);
        String name = Thread.currentThread().getName();
        System.out.println(name + ":doing......");
    }
}
