package com.thoughtworks.myapplication;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.thoughtworks.myapplication.repository.AppDatabase;
import com.thoughtworks.myapplication.repository.user.UserRepository;
import com.thoughtworks.myapplication.repository.user.UserRepositoryImpl;
import com.thoughtworks.myapplication.repository.user.entity.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.schedulers.Schedulers;


@RunWith(AndroidJUnit4.class)
public class UserRepositoryTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AppDatabase appDatabase;

    private UserRepository userRepository;

    @Before
    public void setUp() {
        appDatabase = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().getTargetContext(),
                AppDatabase.class).build();
        userRepository = new UserRepositoryImpl(appDatabase.userDBDataSource());
    }

    @After
    public void tearDown() {
        appDatabase.close();
    }

    @Test
    public void should_find_correct_user() {
        User savedUser = new User();
        savedUser.setName("sjyuan");
        savedUser.setPassword("123");
        savedUser.setId("123");
        appDatabase.userDBDataSource().save(savedUser).subscribeOn(Schedulers.io()).subscribe();
        userRepository.findByName("sjyuan").test()
                .assertValue(user -> user.getId().equals(savedUser.getId()));
    }
}