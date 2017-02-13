package com.example.risha.wwesuperstarsii;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,SensorEventListener {



    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity
    MediaPlayer cenaPlayer;
    MediaPlayer hhhPlayer;
    MediaPlayer punkPlayer;
    MediaPlayer deanPlayer;
    MediaPlayer romanPlayer;
    MediaPlayer utakerPlayer;
    ImageView wrestlerImage;


    private long  curTime,lastUpdate;
    private float x,y,z,last_x,last_y,last_z;

    private final static long updatePeriod = 500;
    private static  final int shakeThreshold = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        this.initialize();

        cenaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.cena);
        deanPlayer = MediaPlayer.create(getApplicationContext(),R.raw.dean);
        hhhPlayer = MediaPlayer.create(getApplicationContext(),R.raw.hhh);
        punkPlayer = MediaPlayer.create(getApplicationContext(),R.raw.punk);
        romanPlayer = MediaPlayer.create(getApplicationContext(),R.raw.roman);
        utakerPlayer = MediaPlayer.create(getApplicationContext(),R.raw.utaker);

        wrestlerImage = (ImageView)findViewById(R.id.wrestlerImage);

        cenaPlayer.start();
        punkPlayer.start();
        deanPlayer.start();
        hhhPlayer.start();
        utakerPlayer.start();
        romanPlayer.start();
    }


    protected void initialize()
    {
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        curTime = lastUpdate = (long)0.0;
        x = y = z = last_x = last_y = last_z = (float)0.0;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {


        Random rn = new Random();
        int random = rn.nextInt(6) + 1;

        long curTime = System.currentTimeMillis();

        if((curTime - lastUpdate) > updatePeriod){

            long diffTime = (curTime - lastUpdate);

            x = event.values[0];
            y = event.values[1];
            z = event.values[2];

            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter

            if (mAccel > 12) {
                // Toast toast = Toast.makeText(getApplicationContext(), "Device has shaken.", Toast.LENGTH_LONG);
                // toast.show();
                if(random == 1) {
                    cenaPlayer.start();
                    wrestlerImage.setImageResource(R.drawable.john);
                    punkPlayer.pause();
                    deanPlayer.pause();
                    hhhPlayer.pause();
                    utakerPlayer.pause();
                    romanPlayer.pause();
                }else if(random == 2){
                    deanPlayer.start();
                    wrestlerImage.setImageResource(R.drawable.dean);
                    cenaPlayer.pause();
                    punkPlayer.pause();
                    hhhPlayer.pause();
                    utakerPlayer.pause();
                    romanPlayer.pause();
                }else if(random == 3){
                    hhhPlayer.start();
                    wrestlerImage.setImageResource(R.drawable.hhh);
                    cenaPlayer.pause();
                    punkPlayer.pause();
                    deanPlayer.pause();
                    utakerPlayer.pause();
                    romanPlayer.pause();
                }else if(random == 4){
                    punkPlayer.start();
                    wrestlerImage.setImageResource(R.drawable.punk);
                    cenaPlayer.pause();
                    deanPlayer.pause();
                    hhhPlayer.pause();
                    utakerPlayer.pause();
                    romanPlayer.pause();
                }else if(random == 5){
                    romanPlayer.start();
                    wrestlerImage.setImageResource(R.drawable.roman);
                    cenaPlayer.pause();
                    punkPlayer.pause();
                    deanPlayer.pause();
                    hhhPlayer.pause();
                    utakerPlayer.pause();
                }else if(random == 6){
                    utakerPlayer.start();
                    wrestlerImage.setImageResource(R.drawable.utaker);
                    cenaPlayer.pause();
                    punkPlayer.pause();
                    deanPlayer.pause();
                    hhhPlayer.pause();
                    romanPlayer.pause();
                }

            }
            last_x = x;
            last_y = y;
            last_z = z;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_stop) {

            cenaPlayer.stop();
            punkPlayer.stop();
            deanPlayer.stop();
            hhhPlayer.stop();
            utakerPlayer.stop();
            romanPlayer.stop();

        } else if (id == R.id.nav_exit) {


            finish();
            cenaPlayer.stop();
            punkPlayer.stop();
            deanPlayer.stop();
            hhhPlayer.stop();
            utakerPlayer.stop();
            romanPlayer.stop();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
