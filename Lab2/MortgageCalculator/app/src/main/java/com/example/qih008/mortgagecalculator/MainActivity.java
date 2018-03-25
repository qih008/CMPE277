package com.example.qih008.mortgagecalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.qih008.mortgagecalculator.databinding.ActivityMainBinding;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private DrawerLayout mDrawerLayout;
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadActivity();
    }

    private void loadActivity(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mPrefs = getSharedPreferences("my_prefs", MODE_PRIVATE);

        // avoid automatically appear android keyboard when activity start
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // auto edit input amount format
        binding.numberPrice.addTextChangedListener(new NumberTextWatcherForThousand(binding.numberPrice));
        binding.numberPayment.addTextChangedListener(new NumberTextWatcherForThousand(binding.numberPayment));
        // To get the input as plain Double Text
        //NumberTextWatcherForThousand.trimCommaOfString(editText.getText().toString())


        // Calculate the mortgage

        binding.buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s_property_price = NumberTextWatcherForThousand.trimCommaOfString(binding.numberPrice.getText().toString());
                String s_down_payment = NumberTextWatcherForThousand.trimCommaOfString(binding.numberPayment.getText().toString());
                String s_apr = binding.numberAPR.getText().toString();

                if(s_property_price.isEmpty() || s_down_payment.isEmpty() || s_apr.isEmpty()){
                    Snackbar.make(view, "Missing required fields. Please check!", Snackbar.LENGTH_SHORT).show();
                    binding.numberMonthly.setText("");
                }
                else {
                    Double n_apr = Double.parseDouble(s_apr);
                    Double n_property_price = Double.parseDouble(s_property_price);
                    Double n_down_payment = Double.parseDouble(s_down_payment);
                    String s_year = binding.spinnerTerm.getSelectedItem().toString();
                    int year = 15;
                    if(s_year.charAt(0) == '3')                      // check spinner value
                        year = 30;

                    Double r = n_apr / 12 / 100;                      // monthly percentage rage
                    Double P = n_property_price - n_down_payment;     // total loan amount
                    int n = year * 12;                                // total payment month
                    Double temp = Math.pow(1 + r, n) - 1;             // function: M = P * r(1+r)^n / ((1+r)^n - 1)
                    Double M = P * r * Math.pow(1 + r, n) / temp;     // monthly payment
                    int monthly_payment = (int) Math.round(M);       // round up to int
                    binding.numberMonthly.setText(Integer.toString(monthly_payment));
                    //Log.wtf("myWTF", n_property_price + "  " + n_down_payment + "  " + n_apr + "  " + year);
                    //Log.wtf("myWTF", r + "  " + P + "  " + n + "  " + temp);
                }
            }
        });

        // handling save mortgage calculation
        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s_property_price = NumberTextWatcherForThousand.trimCommaOfString(binding.numberPrice.getText().toString());
                String s_down_payment = NumberTextWatcherForThousand.trimCommaOfString(binding.numberPayment.getText().toString());
                String s_apr = binding.numberAPR.getText().toString();
                String monthly_payment = binding.numberMonthly.getText().toString();
                String address = binding.editStreet.getText().toString();
                String city = binding.editCity.getText().toString();
                String zipcode = binding.editZipcode.getText().toString();

                // check require field must be fill out
                if(s_property_price.isEmpty() || s_down_payment.isEmpty() || s_apr.isEmpty() || address.isEmpty()
                        || city.isEmpty() || zipcode.isEmpty() || monthly_payment.isEmpty()){
                    Snackbar.make(view, "Missing required fields. Please check!", Snackbar.LENGTH_SHORT).show();
                }
                else{
                    Double loan = Double.parseDouble(s_property_price) - Double.parseDouble(s_down_payment);
                    String type = binding.spinnerType.getSelectedItem().toString();
                    String state = binding.spinnerState.getSelectedItem().toString();

                    String data = address + ":" + city + ":" + state + ":" + zipcode + ":" + type + ":" + loan.toString() + ":" + s_apr + ":" + monthly_payment;

//                    Set<String> set = new HashSet<>();
//                    set.add(address);
//                    set.add(city);
//                    set.add(state);
//                    set.add(zipcode);
//                    set.add(type);
//                    set.add(loan.toString());
//                    set.add(s_apr);
//                    set.add(monthly_payment);

                    SharedPreferences.Editor ed = mPrefs.edit();
//                    ed.putStringSet(address+zipcode, set);
                    ed.putString(address+zipcode, data);
                    ed.commit();

                }
            }
        });



        // test for saved data
        binding.buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, ?> values = mPrefs.getAll();
                if(values.size() != 0) {
                    for (Map.Entry entry : values.entrySet()) {
                        String key = (String) entry.getKey();
                        Log.wtf("myWTF", key);
                        String tempStr = mPrefs.getString(key,"");
                        //String tempStr = (String) entry.getValue();
                        String[] tempAry = tempStr.split(":");
                        for (String s : tempAry) {
                            Log.wtf("myWTF", s);
                        }
                        Log.wtf("myWTF", "            ");
                    }
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
                            case R.id.nav_map:
                                callMap();
                                return true;
                        }

                        return true;
                    }
                });

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);


        // Set up spinner

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.types_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        binding.spinnerType.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(this, R.array.years_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerTerm.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(this, R.array.states_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerState.setAdapter(adapter);
    }


    // Create action bar
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
            case R.id.action_new:
                loadActivity();
                return true;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void callMap(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
