package com.thoughtworks.myapplication;

import android.app.Application;

import com.thoughtworks.myapplication.repository.user.UserRepository;
import com.thoughtworks.myapplication.repository.user.UserRepositoryImpl;

import static org.mockito.Mockito.mock;

public class MainApplication extends Application {
    private UserRepository userRepository;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public UserRepository userRepository() {
        if (userRepository == null) {
            userRepository = mock(UserRepositoryImpl.class);
        }
        return userRepository;
    }
}
