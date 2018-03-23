package com.example.qih008.mortgagecalculator;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.qih008.mortgagecalculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private DrawerLayout mDrawerLayout;
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);



        // auto edit input amount format
        binding.numberPrice.addTextChangedListener(new NumberTextWatcherForThousand(binding.numberPrice));
        binding.numberPayment.addTextChangedListener(new NumberTextWatcherForThousand(binding.numberPayment));
        // To get the input as plain Double Text
        //NumberTextWatcherForThousand.trimCommaOfString(editText.getText().toString())





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
//                        switch (menuItem.getItemId()) {
//                            case R.id.nav_show:
//                                SharedPreferences.Editor ed = mPrefs.edit();
//                                ed.putInt("numberSkipped", numberSkipped + 1);
//                                ed.commit();
//                                if(result != null)
//                                    showmeDialog1(result);
//                                else{
//                                    showmeDialog1("");
//                                }
//                                return true;
//                            case R.id.nav_assign:
//                                callPicker();
//                                return true;
//                        }

                        return true;
                    }
                });

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
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
//            case R.id.action_skip:
//                SharedPreferences.Editor ed = mPrefs.edit();
//                ed.putInt("numberSkipped", numberSkipped + 1);
//                ed.commit();
//                loadActivity(null);
//                return true;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
