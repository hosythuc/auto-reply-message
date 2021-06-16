package com.hosythuc.autoreplymessager.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hosythuc.autoreplymessager.service.NotificationListener;
import com.hosythuc.autoreplymessager.R;
import com.hosythuc.autoreplymessager.util.NotificationPreferences;
import com.hosythuc.autoreplymessager.util.NotificationUtils;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button btnGetStarted;
    private boolean running;
    private NotificationPreferences notificationPreferences;
    private FragmentContainerView containerView;

    private BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.navigation);
        containerView = findViewById(R.id.content_fragment);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        loadFragment(1);
                        break;
                    case R.id.setting:
                        loadFragment(2);
                        break;

                }
                return true;
            }
        });
        notificationPreferences = NotificationPreferences.getInstance(getApplicationContext());
        loadFragment(1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        running = notificationPreferences.getBoolean(NotificationUtils.RUN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        MenuItem toggleservice = menu.findItem(R.id.switch_active);
        Switch actionView = toggleservice.getActionView().findViewById(R.id.switchForActionBar);
        actionView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Start or stop your Service
                if (isChecked) {
                    Log.d("thuchs", "2");
                    MainActivity.this.startService(new Intent(MainActivity.this, NotificationListener.class));
                    notificationPreferences.putBoolean(NotificationUtils.RUN, true);
                    Toast.makeText(getApplicationContext(), "Đã bật", Toast.LENGTH_LONG).show();

                }
                else {
                    Log.d("thuchs", "1");
                    notificationPreferences.putBoolean(NotificationUtils.RUN, false);
                    MainActivity.this.stopService(new Intent(MainActivity.this, NotificationListener.class));
                    Toast.makeText(getApplicationContext(), "Đã tắt", Toast.LENGTH_LONG).show();
                }
            }
        });

        actionView.setChecked(running);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, item.getItemId() + "");
        return super.onOptionsItemSelected(item);
    }

    private void loadFragment(int pos) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (pos) {
            case 1:
                getSupportActionBar().setTitle("Home");
                transaction.replace(R.id.content_fragment, HomeFragment.newInstance());
                transaction.addToBackStack(null);
                break;
            case 2:
                getSupportActionBar().setTitle("Setting");
                transaction.replace(R.id.content_fragment, SettingFragment.newInstance());
                transaction.addToBackStack(null);

                break;
        }
        transaction.commit();
    }

}