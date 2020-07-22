package com.thoughtworks.myapplication.repository.user;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.thoughtworks.myapplication.repository.user.entity.User;

import io.reactivex.Completable;
import io.reactivex.Maybe;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface DBUserDataSource extends UserDataSource {
    @Query("SELECT * FROM user WHERE name = :username")
    Maybe<User> findByName(String username);

    @Insert(onConflict = REPLACE)
    Completable save(User user);
}
