package org.tensorflow.demo;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.IndianCalendar;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Launcher_Activity extends AppCompatActivity {

    public static final String inputFormat = "HH:mm";

    private Date date;
    private Date dateCompareOne;
    private Date dateCompareTwo;

    private String compareStringOne = "6:18";
    private String compareStringTwo = "6:21";

    SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat);

    Handler handler;
    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    //FirebaseAnalytics mFirebaseAnalytics;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_launcher);


        SharedPreferences settings = getSharedPreferences("mysettings",
                Context.MODE_PRIVATE);
        if(settings.getBoolean("FIRSTRUN", true)){
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("calorie", "0");
            editor.putBoolean("FIRSTRUN", false);
            editor.commit();
        }

        handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                Calendar now = Calendar.getInstance();

                int hour = now.get(Calendar.HOUR);
                int minute = now.get(Calendar.MINUTE);

                try {
                    date = inputParser.parse(hour + ":" + minute);
                } catch (ParseException e) {
                    date = new Date(0);
                    e.printStackTrace();
                }
                try {
                    dateCompareOne = inputParser.parse(compareStringOne);
                } catch (ParseException e) {
                    date = new Date(0);
                    e.printStackTrace();
                }
                try {
                    dateCompareTwo = inputParser.parse(compareStringTwo);
                } catch (ParseException e) {
                    date = new Date(0);
                    e.printStackTrace();
                }
                SharedPreferences settings = getSharedPreferences("mysettings",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();

                //current time is after comapareOne
                if ( dateCompareTwo.before( date ) && settings.getBoolean("CLOCKRESET",true)) {
                    editor.putBoolean("CLOCKRESET",false);
                    editor.putString("calorie", "0");
                    editor.commit();
                    //Toast.makeText(Launcher_Activity.this,"HE HE",Toast.LENGTH_LONG).show();
                }
                // current time is after compareTwo and before compareOne
                else if ( dateCompareTwo.after(date) && dateCompareOne.before(date)){
                    editor.putBoolean("CLOCKRESET",true);
                    editor.commit();
                }
            }
        };

        handler.postDelayed(r, 500);


        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        // Obtain the FirebaseAnalytics instance.
        //mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(Launcher_Activity.this, DetectorActivity.class);
                ActivityOptions options =
                        ActivityOptions.makeCustomAnimation(Launcher_Activity.this, R.anim.fade_in, R.anim.fade_out);
                Launcher_Activity.this.startActivity(mainIntent, options.toBundle());
                Launcher_Activity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}