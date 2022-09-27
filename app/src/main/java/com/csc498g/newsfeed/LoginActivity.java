package com.csc498g.newsfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = this.getSharedPreferences("com.csc498g.newsfeed", Context.MODE_PRIVATE);

    }

    public void login(View v) {

        EditText loginEdit = findViewById(R.id.loginEdit);
        sp.edit().putString("username", loginEdit.getText().toString()).apply();

    }
}