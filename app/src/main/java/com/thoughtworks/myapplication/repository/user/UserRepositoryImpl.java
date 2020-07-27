package com.thoughtworks.myapplication.repository.user;

import com.thoughtworks.myapplication.repository.user.entity.User;

import io.reactivex.Maybe;

public class UserRepositoryImpl implements UserRepository {
    private UserDataSource dataSource;

    public UserRepositoryImpl(UserDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Maybe<User> findByName(String name) {
        return dataSource.findByName(name);
    }
}