package com.example.qih008.make24;

import org.mariuszgromada.math.mxparser.*;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.support.design.widget.Snackbar;

import com.example.qih008.make24.databinding.ActivityMainBinding;

import java.util.Random;

import static com.example.qih008.make24.MakeNumber.getSolution;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final Random rand = new Random();
    private int numberSucceeded;
    private int numberSkipped;
    private int numberAttempt;
    private int r1 = 0;
    private int r2 = 0;
    private int r3 = 0;
    private int r4 = 0;
    private String result = null;


    private DrawerLayout mDrawerLayout;
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        loadActivity(intent);

    }

    private void loadActivity(Intent intent){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        numberAttempt = 1;
        mPrefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
        numberSucceeded = mPrefs.getInt("numberSucceeded", 0);
        numberSkipped = mPrefs.getInt("numberSkipped", 0);


        // init top 4 textviews
        binding.editAttempt.setText(String.valueOf(numberAttempt));
        binding.editSucceeded.setText(String.valueOf(numberSucceeded));
        binding.editSkipped.setText(String.valueOf(numberSkipped));
        binding.editTime.start();



        if(intent == null || intent.getIntArrayExtra("NumberList") == null) {
            r1 = rand.nextInt(9) + 1;
            r2 = rand.nextInt(9) + 1;
            r3 = rand.nextInt(9) + 1;
            r4 = rand.nextInt(9) + 1;

            // check if this 4 numbers have a solution
            result = getSolution(r1, r2, r3, r4);
            while (result == null) {
                r1 = rand.nextInt(9) + 1;
                r2 = rand.nextInt(9) + 1;
                r3 = rand.nextInt(9) + 1;
                r4 = rand.nextInt(9) + 1;
                result = getSolution(r1, r2, r3, r4);
            }
            Log.wtf("myWTF", result);

        }
        else{

            int[] numberList = intent.getIntArrayExtra("NumberList");
            r1 = numberList[0];
            r2 = numberList[1];
            r3 = numberList[2];
            r4 = numberList[3];
        }
        result = getSolution(r1, r2, r3, r4);

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
                    SharedPreferences.Editor ed = mPrefs.edit();
                    ed.putInt("numberSucceeded", numberSucceeded + 1);
                    ed.commit();
                }

        }
        });

        // Set up navigation drawer and handle click
        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        switch (menuItem.getItemId()) {
                            case R.id.nav_show:
                                SharedPreferences.Editor ed = mPrefs.edit();
                                ed.putInt("numberSkipped", numberSkipped + 1);
                                ed.commit();
                                if(result != null)
                                    showmeDialog1(result);
                                else{
                                    showmeDialog1("");
                                }
                                return true;
                            case R.id.nav_assign:
                                callPicker();
                                return true;
                        }

                        return true;
                    }
                });

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    public void openDialog(String s){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Binggo! " + s + "=24");
        alertDialogBuilder.setPositiveButton("Next Puzzle",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        loadActivity(null);
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * On selecting action bar icons
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_delete:
                binding.mainScreen.setText("");
                binding.number1.setEnabled(true);
                binding.number2.setEnabled(true);
                binding.number3.setEnabled(true);
                binding.number4.setEnabled(true);
                return true;
            case R.id.action_skip:
                SharedPreferences.Editor ed = mPrefs.edit();
                ed.putInt("numberSkipped", numberSkipped + 1);
                ed.commit();
                loadActivity(null);
                return true;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showmeDialog1(String s){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        if(s.equals(""))
            alertDialogBuilder.setMessage("Sorry, there are actually no solutions");
        else
            alertDialogBuilder.setMessage("The solution is: " + s + "=24");
        alertDialogBuilder.setPositiveButton("Next Puzzle",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        loadActivity(null);
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    public void callPicker(){
        Intent intent = new Intent(this, PickerActivity.class);
        startActivity(intent);
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.wtf("myWTF", "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//        SharedPreferences.Editor ed = mPrefs.edit();
//        ed.putInt("numberSucceeded", numberSucceeded);
//        ed.putInt("numberSkipped", numberSkipped);
//        ed.commit();
//    }

}
