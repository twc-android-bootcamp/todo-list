package com.thoughtworks.myapplication;

import android.app.Application;

import androidx.room.Room;

import com.thoughtworks.myapplication.repository.AppDatabase;
import com.thoughtworks.myapplication.repository.user.UserDataSource;
import com.thoughtworks.myapplication.repository.user.UserRepository;
import com.thoughtworks.myapplication.repository.user.UserRepositoryImpl;

public class MainApplication extends Application {
    private UserRepository userRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        userRepository = new UserRepositoryImpl(userDataSource());
    }


    public UserDataSource userDataSource() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, this.getClass().getSimpleName()).build();
        return db.userDBDataSource();
    }

    public UserRepository userRepository() {
        return userRepository;
    }
}
