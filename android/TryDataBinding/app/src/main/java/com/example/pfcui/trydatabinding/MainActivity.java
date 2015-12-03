package com.example.pfcui.trydatabinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.pfcui.trydatabinding.databinding.ActivityMainBinding;
import com.example.pfcui.trydatabinding.models.User;

public class MainActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(
                this, R.layout.activity_main);
        user = new User("this is data model", "set from activity");
        binding.setUser(user);
    }

    public void changeName(View view) {
        user.setFirstName("this is changed");
        user.setLastName("this is also changed");
    }
}
