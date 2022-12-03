package com.example.demo.rule;

import com.example.demo.wo.User;

import java.util.function.Predicate;

public class UserNamePre implements Predicate<User> {
    @Override
    public boolean test(User o) {
        return o.getUserName().equals("张三");
    }
}
