package com.cynthia.examenandroid.app.login.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.cynthia.examenandroid.R;
import com.cynthia.examenandroid.app.login.LoginPresenterContract;
import com.cynthia.examenandroid.app.login.LoginViewContract;
import com.cynthia.examenandroid.app.login.presenter.LoginPresenter;
import com.cynthia.examenandroid.business.models.ExError;
import com.cynthia.examenandroid.business.models.ExLogin;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginFragment extends Fragment implements LoginViewContract {

    private EditText emailText;
    private EditText passwordText;
    private Button loginButton;
    // Class dependencies
    private LoginPresenterContract presenter = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container, false);
        this.emailText = view.findViewById(R.id.username);
        this.passwordText = view.findViewById(R.id.password);
        this.loginButton = view.findViewById(R.id.login);
        this.presenter = new LoginPresenter(this, getContext());


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Log.i("msg","click");
                String user = emailText.getText().toString();
                String password = passwordText.getText().toString();
                ExLogin rrLogin = new ExLogin(user, password);
                presenter.doLogin(rrLogin);
            }
        });
        return view;
    }


    @Override
    public void showPasswordError(String message) {
        passwordText.setError(message);
    }

    @Override
    public void showUSerError(String message) {
        emailText.setError(message);
    }

    @Override
    public void clearPasswordError() {
        passwordText.setError(null);

    }

    @Override
    public void clearUserError() {
        emailText.setError(null);
    }

    @Override
    public void onSuccessLogin() {
        Log.i("msg","entrando a success");
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.action_loginFragment_to_menuFragment);


    }

    @Override
    public void onFailedLogin(ExError error) {
        if (error.getThrowable() instanceof FirebaseAuthInvalidUserException) {
            emailText.setError("Email es incorrecto");
            loginButton.setEnabled(true);
        } else if (error.getThrowable() instanceof FirebaseAuthInvalidCredentialsException) {
            passwordText.setError("Contraseña es incorrecta");
            loginButton.setEnabled(true);
        }
    }
}
