package com.thoughtworks.myapplication.ui.login;

import android.annotation.SuppressLint;
import android.util.Patterns;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.thoughtworks.myapplication.R;
import com.thoughtworks.myapplication.repository.user.UserRepository;
import com.thoughtworks.myapplication.repository.user.entity.User;

import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();

    private UserRepository userRepository;

    void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    void observeLoginFormState(LifecycleOwner lifecycleOwner, Observer<LoginFormState> observer) {
        loginFormState.observe(lifecycleOwner, observer);
    }

    void observeLoginResult(LifecycleOwner lifecycleOwner, Observer<LoginResult> observer) {
        loginResult.observe(lifecycleOwner, observer);
    }

    @SuppressLint("CheckResult")
    public void login(String username, String password) {
        Maybe<User> result = userRepository.findByName(username);
        result.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> loginResult.setValue(new LoginResult(R.string.login_failed)))
                .subscribe(user -> {
                    if (user == null) {
                        loginResult.postValue(new LoginResult(R.string.login_failed));
                        return;
                    }
                    if (user.getPassword().equals(password)) {
                        loginResult.postValue(new LoginResult(new LoggedInUserView(user.getName())));
                        return;
                    }
                    loginResult.postValue(new LoginResult(R.string.login_failed));
                });
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 2;
    }
}