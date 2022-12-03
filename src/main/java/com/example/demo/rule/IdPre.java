package com.example.demo.rule;

import com.example.demo.wo.User;

import java.util.function.Predicate;

public class IdPre implements Predicate<User> {

    @Override
    public boolean test(User user) {
        return user.getUserId() != 10;
    }
}
