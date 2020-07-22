package com.thoughtworks.myapplication;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.thoughtworks.myapplication.repository.user.UserRepository;
import com.thoughtworks.myapplication.repository.user.entity.User;
import com.thoughtworks.myapplication.ui.login.HomeActivity;
import com.thoughtworks.myapplication.ui.login.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.internal.operators.maybe.MaybeCreate;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.when;


@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void should_login_successfully() {
        MainApplication applicationContext = (MainApplication) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        UserRepository userRepository = applicationContext.userRepository();
        User user = new User();
        user.setId("123");
        user.setPassword("123");
        user.setName("sjyuan");
        user.setLastName("Yuan");
        when(userRepository.findByName("sjyuan")).thenReturn(new MaybeCreate(emitter -> emitter.onSuccess(user)));

        onView(withId(R.id.username)).perform(typeText("sjyuan"));
        onView(withId(R.id.password)).perform(typeText("123"));
        onView(withId(R.id.login)).perform(click());
        onView(withText("Welcome !sjyuan")).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
        intended(hasComponent(HomeActivity.class.getName()));
    }

    @Test
    public void should_login_failed() {
        MainApplication applicationContext = (MainApplication) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        UserRepository userRepository = applicationContext.userRepository();
        User user = new User();
        user.setId("123");
        user.setPassword("12345");
        user.setName("sjyuan");
        user.setLastName("Yuan");
        when(userRepository.findByName("sjyuan")).thenReturn(new MaybeCreate(emitter -> emitter.onSuccess(user)));

        onView(withId(R.id.username)).perform(typeText("sjyuan"));
        onView(withId(R.id.password)).perform(typeText("123456"));
        onView(withId(R.id.login)).perform(click());
        onView(withText(R.string.login_failed)).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }
}