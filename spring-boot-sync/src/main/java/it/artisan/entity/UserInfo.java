package it.artisan.entity;

import lombok.ToString;

/**
 * @author dkp
 * @create 2022-11-03 20:42
 */
@ToString
public class UserInfo {
    private String id;
    private String name;
    private Integer age;

    public UserInfo(){}
    public UserInfo(String s, String str, int i) {
        this.id = s;
        this.name = str;
        this.age = i;
    }

}
