package com.cynthia.examenandroid.app.login;

import com.cynthia.examenandroid.business.models.ExError;

public interface LoginViewContract {
    void showPasswordError(String message);

    void showUSerError(String message);

    void clearPasswordError();

    void clearUserError();

    void onSuccessLogin();

    void onFailedLogin(ExError error);
}
