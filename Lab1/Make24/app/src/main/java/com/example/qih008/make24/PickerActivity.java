package com.example.qih008.make24;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.example.qih008.make24.databinding.ActivityMainBinding;

public class PickerActivity extends AppCompatActivity {

    NumberPicker noPicker1 = null;
    NumberPicker noPicker2 = null;
    NumberPicker noPicker3 = null;
    NumberPicker noPicker4 = null;
    Button buttonSelect;

    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);

        noPicker1 = findViewById(R.id.numberPicker1);
        noPicker2 = findViewById(R.id.numberPicker2);
        noPicker3 = findViewById(R.id.numberPicker3);
        noPicker4 = findViewById(R.id.numberPicker4);
        buttonSelect = findViewById(R.id.buttonSelect);

        noPicker1.setMaxValue(9);
        noPicker1.setMinValue(1);
        noPicker1.setWrapSelectorWheel(false);

        noPicker2.setMaxValue(9);
        noPicker2.setMinValue(1);
        noPicker2.setWrapSelectorWheel(false);

        noPicker3.setMaxValue(9);
        noPicker3.setMinValue(1);
        noPicker3.setWrapSelectorWheel(false);

        noPicker4.setMaxValue(9);
        noPicker4.setMinValue(1);
        noPicker4.setWrapSelectorWheel(false);

        mPrefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
        int numberSkipped = mPrefs.getInt("numberSkipped", 0);
        SharedPreferences.Editor ed = mPrefs.edit();
        ed.putInt("numberSkipped", numberSkipped + 1);
        ed.commit();

        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] numberList = new int[4];
                numberList[0] = noPicker1.getValue();
                numberList[1] = noPicker2.getValue();
                numberList[2] = noPicker3.getValue();
                numberList[3] = noPicker4.getValue();
                sendNumber(view, numberList);
            }
        });


    }

    public void sendNumber(View view, int[] numberList){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("NumberList", numberList);
        startActivity(intent);
    }
}
