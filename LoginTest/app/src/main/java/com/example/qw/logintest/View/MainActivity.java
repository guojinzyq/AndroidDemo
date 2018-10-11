package com.example.qw.logintest.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.qw.logintest.Calculator.CalculatorActivity;
import com.example.qw.logintest.Prosenter.UserPresenter;
import com.example.qw.logintest.Prosenter.UserPresterImp;
import com.example.qw.logintest.R;

public class MainActivity extends AppCompatActivity implements UserView,View.OnClickListener{

    UserPresenter userPresenter;
    EditText account;
    EditText password;
    Button login;
    Button reset;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        account=(EditText) findViewById(R.id.accout);
        password=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.login);
        reset=(Button)findViewById(R.id.reset);
        progressBar=(ProgressBar)findViewById(R.id.progressbar);

        login.setOnClickListener(this);
        reset.setOnClickListener(this);

        userPresenter=new UserPresterImp(this);
        userPresenter.setProgressBarVisiblity(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.login:
                userPresenter.setProgressBarVisiblity(View.VISIBLE);
                login.setEnabled(false);
                reset.setEnabled(false);
                userPresenter.doLogin(account.getText().toString(), password.getText().toString());
                break;
            case R.id.reset:
                userPresenter.clear();
        }
    }

    @Override
    public void onClearText() {
        account.setText("");
        password.setText("");
    }

    @Override
    public void onLoginResult(Boolean result, int code) {
        userPresenter.setProgressBarVisiblity(View.INVISIBLE);
        login.setEnabled(true);
        reset.setEnabled(true);
        if(result){
            Toast.makeText(this,"Login Success",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(MainActivity.this, CalculatorActivity.class);
            startActivity(intent);
            finish();
        }
        else
            Toast.makeText(this,"Login Failed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {
        progressBar.setVisibility(visibility);
    }

}
