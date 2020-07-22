package com.thoughtworks.myapplication.repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.thoughtworks.myapplication.repository.user.entity.User;
import com.thoughtworks.myapplication.repository.user.DBUserDataSource;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DBUserDataSource userDBDataSource();
}