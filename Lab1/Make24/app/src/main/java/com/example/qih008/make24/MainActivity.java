package com.example.qih008.make24;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.qih008.make24.databinding.ActivityMainBinding;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final Random rand = new Random();
    private int numberSucceeded = 0;
    private int numberSkipped = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        int numberAttempt = 1;

        int r1 = rand.nextInt(9) + 1;
        int r2 = rand.nextInt(9) + 1;
        int r3 = rand.nextInt(9) + 1;
        int r4 = rand.nextInt(9) + 1;

        binding.editAttempt.setText(String.valueOf(numberAttempt));
        binding.editSucceeded.setText(String.valueOf(numberSucceeded));
        binding.editSkipped.setText(String.valueOf(numberSkipped));
        binding.editTime.start();
        //Log.wtf("myWTF", String.valueOf(r1));
        binding.number1.setText(String.valueOf(r1));
        binding.number2.setText(String.valueOf(r2));
        binding.number3.setText(String.valueOf(r3));
        binding.number4.setText(String.valueOf(r4));
    }
}
