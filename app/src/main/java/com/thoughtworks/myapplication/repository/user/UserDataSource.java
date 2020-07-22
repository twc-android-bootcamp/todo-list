package com.thoughtworks.myapplication.repository.user;


import com.thoughtworks.myapplication.repository.user.entity.User;

import io.reactivex.Completable;
import io.reactivex.Maybe;

public interface UserDataSource {
    Maybe<User> findByName(String userId);
    Completable save(User user);
}
