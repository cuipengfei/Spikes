package com.example.pfcui.trydatabinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.pfcui.trydatabinding.databinding.ActivityMainBinding;
import com.example.pfcui.trydatabinding.models.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(
                this, R.layout.activity_main);
        binding.setUser(new User("this is data model", "set from activity"));
    }
}
