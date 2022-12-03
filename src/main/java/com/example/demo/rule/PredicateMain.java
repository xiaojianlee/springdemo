package com.example.demo.rule;

import com.example.demo.wo.User;

public class PredicateMain {

    public static void main(String[] args) {
        User user = new User();
        user.setUserId(1);
        user.setUserName("张三");
        boolean test = new UserNamePre().and(new IdPre()).test(user);
        System.out.println(test);
        user.setUserName("李四");
        boolean test1 = new UserNamePre().and(new IdPre()).test(user);
        System.out.println(test1);
        user.setUserId(10);
        boolean test2 = new UserNamePre().and(new IdPre()).test(user);
        System.out.println(test2);

    }
}
