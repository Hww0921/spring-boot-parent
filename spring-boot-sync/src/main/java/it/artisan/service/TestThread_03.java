package it.artisan.service;

import lombok.SneakyThrows;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 创建线程的四种方式2：
 * 1.实现callable接口
 * 2.FutureTask task = new FutureTask(我们新写的实现了callable的接口的类)
 * 3.new Thread(task).start();
 *
 * --------TODO --------------
 * 和runnble不同的是，callable有返回值 返回值 使用FutureTask包装一下
 */
public class TestThread_03 {
    @SneakyThrows
    public static void main(String[] args) {
        System.out.println("main-------start-------");
        FutureTask<String> futureTask = new FutureTask<>(new CallableThread03());
        new Thread(futureTask).start();
        System.out.println(futureTask.get());
        System.out.println("main-------end-------");

    }
}

/**
 * 定义一个类实现 callable 接口
 */
class CallableThread03 implements Callable<String> {
    @SneakyThrows
    @Override
    public String call() throws Exception {
        Thread.sleep(1000);
        String name = Thread.currentThread().getName();
        System.out.println(name + ":doing......");
        return "韩雯雯";
    }
}
