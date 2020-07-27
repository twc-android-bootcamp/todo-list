package com.thoughtworks.myapplication.repository.user;

import com.thoughtworks.myapplication.repository.user.entity.User;

import io.reactivex.Maybe;

public interface UserRepository {
    Maybe<User> findByName(String name);
}
