package com.example.login2;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class UserListActivity extends AppCompatActivity {

    TextView tvUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        tvUserList = findViewById(R.id.tvUserList);

        // Obtener los datos pasados desde MainActivity6
        String userData = getIntent().getStringExtra("userData");
        if (userData != null) {
            tvUserList.setText(userData);
        }
    }
}
