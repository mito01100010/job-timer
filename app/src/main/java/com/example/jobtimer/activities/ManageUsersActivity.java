package com.example.jobtimer.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jobtimer.R;

public class ManageUsersActivity extends AppCompatActivity {

//    private TextView textViewUserEmail;
//    private TextView textViewUserPassword;
    private Button buttonAddUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);
        //getSupportActionBar().setTitle("Manage User");

        buttonAddUser = (Button) findViewById(R.id.buttonAddUser);
        buttonAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

    }

    public void openDialog(){
         AddUserDialog addUserDialog = new AddUserDialog();
         addUserDialog.show(getSupportFragmentManager(), "Add User Dialog");
    }
}
