package org.tensorflow.demo;

import android.animation.Animator;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.github.lzyzsd.circleprogress.ArcProgress;

public class MyDiet extends AppCompatActivity {

    String[] st,imgstr;

    Integer x,z;

    String time;

    //Initialize Database
    DBhandler mydatabase ;

    ArcProgress ap;

    TextView t_cal,t_time;

    Handler handler;

    private static final int ACTIVITY_NUM = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_diet);

        setTitle("My Diet Plan");

        //Create Database
        mydatabase = new DBhandler(MyDiet.this,null,null,1);

        // Extracting string array from food info
        Intent i = getIntent();
        st = i.getStringArrayExtra("stringarray");
        imgstr = i.getStringArrayExtra("imagearray");

        t_cal = (TextView) findViewById(R.id.txt_cal_amt);

        t_time = (TextView) findViewById(R.id.txt_walk_min);

        ap = (ArcProgress) findViewById(R.id.arc_progress);

        SharedPreferences settings = getSharedPreferences("mysettings",
                Context.MODE_PRIVATE);
        final String calorie = settings.getString("calorie", "defaultvalue");

        // Calculate calories %
        x = Integer.parseInt(calorie);
        float y = (float) x/ (float) 2800;
        y *= 100;
        x = (int) y;

        // Calculate Walk % (only 10% of total calories consumes)
        z = Integer.parseInt(calorie);
        float f = (float) z/ (float) 100;
        f *= 6.3;
        z = (int) f;
        time = Integer.toString(z);

        /*String test = Integer.toString(x);
        Toast.makeText(this,"integer : " + test,Toast.LENGTH_LONG).show();*/

        ap.setProgress(0);

        // updating the calories and time values
        handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                ap.setProgress(x);
                t_cal.setText(calorie +" Kcal");
                t_time.setText(time +" min");
                //handler.postDelayed(this, 1100);
            }
        };

        handler.postDelayed(r, 1100);

        BottomNavigationViewEx bottomNavigationView = (BottomNavigationViewEx)
                findViewById(R.id.bottom_navigation);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

        bottomNavigationView.enableAnimation(false);
        bottomNavigationView.enableItemShiftingMode(false);
        bottomNavigationView.enableShiftingMode(false);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_collection:
                                String[] temparray2 = mydatabase.get_DetectedFoodname();
                                if(st!=null && temparray2!=null){
                                    if(temparray2.length>st.length){
                                        st = temparray2;
                                    }
                                }
                                Intent i1 = new Intent(MyDiet.this,MyCollection.class);
                                ActivityOptions options1 =
                                        ActivityOptions.makeCustomAnimation(MyDiet.this, R.anim.slide_in_right, R.anim.slide_out_right);
                                if(st!=null) {
                                    i1.putExtra("stringarray", st);
                                }
                                if(imgstr!=null){
                                    i1.putExtra("imagearray",imgstr);
                                }
                                MyDiet.this.startActivity(i1, options1.toBundle());
                                MyDiet.this.finish();
                                return true;
                            case R.id.action_detect:
                                String[] temparray1 = mydatabase.get_DetectedFoodname();
                                if(st!=null && temparray1!=null){
                                    if(temparray1.length>st.length){
                                        st = temparray1;
                                    }
                                }
                                Intent i2 = new Intent(MyDiet.this,DetectorActivity.class);
                                ActivityOptions options2 =
                                        ActivityOptions.makeCustomAnimation(MyDiet.this, R.anim.slide_in_right, R.anim.slide_out_right);
                                if(st!=null) {
                                    i2.putExtra("stringarray", st);
                                }
                                if(imgstr!=null){
                                    i2.putExtra("imagearray",imgstr);
                                }
                                MyDiet.this.startActivity(i2, options2.toBundle());
                                MyDiet.this.finish();
                                return true;
                            case R.id.action_detectedfood:
                                String[] temparray = mydatabase.get_DetectedFoodname();
                                if(st!=null && temparray!=null){
                                    if(temparray.length>st.length){
                                        st = temparray;
                                    }
                                }
                                Intent i3 = new Intent(MyDiet.this,DetectedFooditems.class);
                                ActivityOptions options3 =
                                        ActivityOptions.makeCustomAnimation(MyDiet.this, R.anim.slide_in_right, R.anim.slide_out_right);

                                if(st!=null) {
                                    i3.putExtra("stringarray", st);
                                }
                                if(imgstr!=null){
                                    i3.putExtra("imagearray",imgstr);
                                }
                                MyDiet.this.startActivity(i3, options3.toBundle());
                                MyDiet.this.finish();
                                return true;
                            case R.id.action_mydiet:
                                return true;
                        }
                        return true;
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            return false;
        }
        return false;
    }
}
