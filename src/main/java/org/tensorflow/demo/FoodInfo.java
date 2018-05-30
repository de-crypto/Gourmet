package org.tensorflow.demo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.ArcProgress;

public class FoodInfo extends AppCompatActivity {

    String[] myStrings,imagearray;

    public String final_foodname="";

    //Initialize the Database
    DBhandler mydatabase;

    TabLayout tabLayout;

    ViewPager viewPager;

    String calorie;

    private SectionPageAdapter sectionPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_info);

        Intent i = getIntent();
        myStrings = i.getStringArrayExtra("stringarray");
        imagearray = i.getStringArrayExtra("imagearray");
        String foodname = i.getExtras().getString("name");
       /* if(foodname!=null){
            setTitle(foodname);
        }
        else {
            setTitle("Food Information");
        }*/

        final_foodname = foodname.substring(0,foodname.length()-1);

        // create the database
        mydatabase = new DBhandler(this,null,null,1);

        //to show content in each fragment
        NestedScrollView scrollView = (NestedScrollView) findViewById (R.id.nest_scrollview);
        scrollView.setFillViewport (true);

        // Set back button for toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //this line shows back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set title for CollapsingToolbar and toolbar
        toolbar.setTitle(foodname);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        collapsingToolbarLayout.setTitle(foodname);

        // Extracting bitmap from Intent
        Bundle extras = getIntent().getExtras();
        Bitmap bmp = (Bitmap) extras.getParcelable("imagebitmap");

        //Setting imageview
        ImageView image = (ImageView) findViewById(R.id.toolbarImage);
        image.setImageBitmap(bmp );

        String[] str = mydatabase.get_foodinfo(final_foodname);

        calorie = str[0];

        //Finding result
        /*(TextView txt = (TextView) findViewById(R.id.txt_result);
        txt.setText(str);*/

        /*if(final_foodname.compareTo("pizza")==0) {
            Toast.makeText(this, "I did not fucked up", Toast.LENGTH_LONG).show();
        }*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences settings = getSharedPreferences("mysettings",
                        Context.MODE_PRIVATE);
                String cur_cal = settings.getString("calorie", "defaultvalue");

                int x = Integer.parseInt(cur_cal);
                int y = Integer.parseInt(calorie);
                x += y;

                cur_cal = Integer.toString(x);

                SharedPreferences.Editor editor = settings.edit();
                editor.putString("calorie", cur_cal);
                editor.commit();

                Snackbar.make(view, final_foodname + " is added to your diet", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        sectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());

        //set up viewpager with sectionPageAdapter
        viewPager = (ViewPager) findViewById(R.id.myviewpager);
        setupViewPager(viewPager);


        tabLayout=(TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.addTab(tabLayout.newTab().setText("Nutrients"));
//        tabLayout.addTab(tabLayout.newTab().setText("Recipe"));

    }


    // add Fragments to SectionPagerAdapter
    public void setupViewPager(ViewPager viewPager){
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());

        //add fragments to adapter
        adapter.addFragment(new Nutrient_fragment(),"Nutrients");
        adapter.addFragment(new Recipe_fragment(),"Recipe");

        viewPager.setAdapter(adapter);

    }

    // Send foodname to Nutrient and Recipe Fragments
    public String send_foodname(){
        return final_foodname;
    }

    public void GotoMainActivity(){
        Intent j = new Intent(this, MyCollection.class);
        j.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        j.putExtra("stringarray",myStrings);
        j.putExtra("imagearray",imagearray);
        startActivity(j);
        this.finish();
        // overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            GotoMainActivity();
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                GotoMainActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
