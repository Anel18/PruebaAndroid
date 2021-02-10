package com.cynthia.examenandroid.app.login.presenter;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cynthia.examenandroid.R;
import com.cynthia.examenandroid.app.login.LoginPresenterContract;
import com.cynthia.examenandroid.app.login.LoginViewContract;
import com.cynthia.examenandroid.business.models.ExError;
import com.cynthia.examenandroid.business.models.ExLogin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPresenter implements LoginPresenterContract {

    private LoginViewContract view;
    private Context context;

    private FirebaseAuth mAuth;
    private static final String TAG = "LoginPresenter";

    public LoginPresenter(LoginViewContract view, Context context) {
        this.view = view;
        this.context = context;

        mAuth = FirebaseAuth.getInstance();

        if (userIsLogged()) {
            view.onSuccessLogin();
        }
    }

    private boolean userIsLogged() {
        FirebaseUser userSigned = this.mAuth.getCurrentUser();
        return userSigned != null;
    }

    @Override
    public void doLogin(ExLogin with) {
        if (!isUserValid(with.getEmail())) {
            view.showUSerError(context.getString(R.string.error_user));
            return;
        } else {
            view.clearUserError();
        }

        if (!isPasswordValid(with.getPassword())) {
            view.showPasswordError(context.getString(R.string.error_password));
            return;
        } else {
            view.clearPasswordError();
        }
        mAuth.signInWithEmailAndPassword(with.getEmail(),with.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.i( TAG,"user[uuid: " + task.getResult().getUser().getUid()+ " ]");
                    view.onSuccessLogin();
                } else if (task.getException() != null) {
                    ExError cbError = new ExError("Error", task.getException());
                    view.onFailedLogin(cbError);
                }
            }
        });

        // view.onSuccessLogin();
        Log.i(TAG, "doLogin(): ending method");
        //Log.i("msg","se recibio info");
    }


    private boolean isUserValid(@Nullable String user) {
        // check email regex
        return user != null && user.length() >= 5;
    }

    private boolean isPasswordValid(@Nullable String password) {
        return password != null && password.length() >= 5;
    }


}
