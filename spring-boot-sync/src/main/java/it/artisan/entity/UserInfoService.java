package it.artisan.entity;

public class UserInfoService {

    public UserInfo getUserInfo(Long userId) throws InterruptedException {
        // 模拟调用耗时
        Thread.sleep(300);
        // 一般是查数据库，或者远程调用返回的
        System.out.println("-------stat ----------");
        return new UserInfo("666", "捡田螺的小男孩", 27);
    }
}



