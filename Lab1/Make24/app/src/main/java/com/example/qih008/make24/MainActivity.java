package com.example.qih008.make24;

import org.mariuszgromada.math.mxparser.*;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.support.design.widget.Snackbar;

import com.example.qih008.make24.databinding.ActivityMainBinding;

import java.util.Random;

import static com.example.qih008.make24.MakeNumber.getSolution;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final Random rand = new Random();
    private int numberSucceeded = 0;
    private int numberSkipped = 0;
    private int numberAttempt = 1;
    private int r1 = 0;
    private int r2 = 0;
    private int r3 = 0;
    private int r4 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadActivity();
    }

    private void loadActivity(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        numberAttempt = 1;

        r1 = rand.nextInt(9) + 1;
        r2 = rand.nextInt(9) + 1;
        r3 = rand.nextInt(9) + 1;
        r4 = rand.nextInt(9) + 1;

        // check if this 4 numbers have a solution
        String result = getSolution(r1,r2,r3,r4);
        while(result == null){
            r1 = rand.nextInt(9) + 1;
            r2 = rand.nextInt(9) + 1;
            r3 = rand.nextInt(9) + 1;
            r4 = rand.nextInt(9) + 1;
            result = getSolution(r1,r2,r3,r4);
        }
        Log.wtf("myWTF",result);

        // init top 4 textviews
        binding.editAttempt.setText(String.valueOf(numberAttempt));
        binding.editSucceeded.setText(String.valueOf(numberSucceeded));
        binding.editSkipped.setText(String.valueOf(numberSkipped));
        binding.editTime.start();

        //Log.wtf("myWTF", String.valueOf(r1));

        // give random 4 numbers
        binding.number1.setText(String.valueOf(r1));
        binding.number2.setText(String.valueOf(r2));
        binding.number3.setText(String.valueOf(r3));
        binding.number4.setText(String.valueOf(r4));

        binding.buttonDone.setEnabled(false);

        binding.number1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = binding.mainScreen.getText() + String.valueOf(r1);
                binding.mainScreen.setText(temp);
                binding.number1.setEnabled(false);
                binding.buttonDone.setEnabled(true);
            }
        });

        binding.number2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = binding.mainScreen.getText() + String.valueOf(r2);
                binding.mainScreen.setText(temp);
                binding.number2.setEnabled(false);
                binding.buttonDone.setEnabled(true);
            }
        });

        binding.number3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = binding.mainScreen.getText() + String.valueOf(r3);
                binding.mainScreen.setText(temp);
                binding.number3.setEnabled(false);
                binding.buttonDone.setEnabled(true);
            }
        });

        binding.number4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = binding.mainScreen.getText() + String.valueOf(r4);
                binding.mainScreen.setText(temp);
                binding.number4.setEnabled(false);
                binding.buttonDone.setEnabled(true);
            }
        });

        binding.buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = binding.mainScreen.getText() + "+";
                binding.mainScreen.setText(temp);
                binding.buttonDone.setEnabled(true);
            }
        });

        binding.buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = binding.mainScreen.getText() + "-";
                binding.mainScreen.setText(temp);
                binding.buttonDone.setEnabled(true);
            }
        });

        binding.buttonMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = binding.mainScreen.getText() + "*";
                binding.mainScreen.setText(temp);
                binding.buttonDone.setEnabled(true);
            }
        });

        binding.buttonDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = binding.mainScreen.getText() + "/";
                binding.mainScreen.setText(temp);
                binding.buttonDone.setEnabled(true);
            }
        });

        binding.buttonLeftP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = binding.mainScreen.getText() + "(";
                binding.mainScreen.setText(temp);
                binding.buttonDone.setEnabled(true);
            }
        });

        binding.buttonRightP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = binding.mainScreen.getText() + ")";
                binding.mainScreen.setText(temp);
                binding.buttonDone.setEnabled(true);
            }
        });

        binding.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = binding.mainScreen.getText() + "";

                if(temp.length() != 0) {
                    String after = temp.substring(0, temp.length() - 1);
                    binding.mainScreen.setText(after);
                    binding.buttonDone.setEnabled(true);

                    char c = temp.charAt(temp.length() - 1);
                    if (c == String.valueOf(r1).charAt(0) && !binding.number1.isEnabled())
                        binding.number1.setEnabled(true);
                    else if (c == String.valueOf(r2).charAt(0) && !binding.number2.isEnabled())
                        binding.number2.setEnabled(true);
                    else if (c == String.valueOf(r3).charAt(0) && !binding.number3.isEnabled())
                        binding.number3.setEnabled(true);
                    else if (c == String.valueOf(r4).charAt(0) && !binding.number4.isEnabled())
                        binding.number4.setEnabled(true);
                }
            }
        });

        binding.buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = binding.mainScreen.getText() + "";
                binding.buttonDone.setEnabled(false);

                numberAttempt += 1;

                // use mXparser lib at Github to calculate expression
                Expression e = new Expression(temp);
                boolean res = Math.abs(e.calculate() - 24) < 0.0000001;

                if(!res){
                    Snackbar.make(view, "Incorrect. Please try again!",
                            Snackbar.LENGTH_SHORT).show();
                    binding.editAttempt.setText(String.valueOf(numberAttempt));
                }
                else {
                    openDialog(temp);
                    numberSucceeded += 1;
                }

        }
        });
    }

    public void openDialog(String s){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Binggo! " + s + "=24");
        alertDialogBuilder.setPositiveButton("Next Puzzle",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        loadActivity();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
