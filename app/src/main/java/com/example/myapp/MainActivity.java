package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button btn_login;
    Button btn_redict;

    FirebaseUser firebaseUser;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        email = findViewById(R.id.text_email);
        password = findViewById(R.id.text_password);
        btn_login = findViewById(R.id.btn_login);
        btn_redict = findViewById(R.id.btn_redict_reg);

        if(firebaseUser != null) {
            Intent intent = new Intent(MainActivity.this, Home.class);
            startActivity(intent);
            finish();
        }

        auth = FirebaseAuth.getInstance();

        btn_redict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Register.class));
                finish();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text_email = email.getText().toString();
                String text_pass = password.getText().toString();

                if(TextUtils.isEmpty(text_email) || TextUtils.isEmpty(text_pass)) {
                    Toast.makeText(MainActivity.this, "Поля Пусты!", Toast.LENGTH_SHORT).show();
                } else {
                    auth.signInWithEmailAndPassword(text_email, text_pass).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(MainActivity.this, Home.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "Проблема с сервером авторизации", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}