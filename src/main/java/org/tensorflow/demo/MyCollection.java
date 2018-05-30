package org.tensorflow.demo;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MyCollection extends AppCompatActivity {

    String[] st,imgstr;

    private static final int ACTIVITY_NUM = 0;

    //Initialize the Database
    DBhandler mydatabase;

    List<Detector_Rowitem> rowItems;
    ListView mylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);

        setTitle("My Collection");

        // create the database
        mydatabase = new DBhandler(this,null,null,1);

        // Extracting string array from food info
        Intent i = getIntent();
        st = i.getStringArrayExtra("stringarray");
        imgstr = i.getStringArrayExtra("imagearray");

        mylist = (ListView) findViewById(R.id.collection_list);

        //Async task for Food item listview
        new Task().execute();

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
                                return true;
                            case R.id.action_detect:
                                String[] temparray1 = mydatabase.get_DetectedFoodname();
                                if(st!=null && temparray1!=null){
                                    if(temparray1.length>st.length){
                                        st = temparray1;
                                    }
                                }
                                Intent i1 = new Intent(MyCollection.this,DetectorActivity.class);
                                ActivityOptions options1 =
                                        ActivityOptions.makeCustomAnimation(MyCollection.this, R.anim.slide_in_left, R.anim.slide_out_left);
                                if(st!=null) {
                                    i1.putExtra("stringarray", st);
                                }
                                if(imgstr!=null){
                                    i1.putExtra("imagearray",imgstr);
                                }
                                MyCollection.this.startActivity(i1, options1.toBundle());
                                MyCollection.this.finish();
                                return true;
                            case R.id.action_detectedfood:
                                String[] temparray2 = mydatabase.get_DetectedFoodname();
                                if(st!=null && temparray2!=null){
                                    if(temparray2.length>st.length){
                                        st = temparray2;
                                    }
                                }
                                Intent i3 = new Intent(MyCollection.this,DetectedFooditems.class);
                                ActivityOptions options3 =
                                        ActivityOptions.makeCustomAnimation(MyCollection.this, R.anim.slide_in_left, R.anim.slide_out_left);
                                if(st!=null) {
                                    i3.putExtra("stringarray", st);
                                }
                                if(imgstr!=null){
                                    i3.putExtra("imagearray",imgstr);
                                }
                                MyCollection.this.startActivity(i3, options3.toBundle());
                                MyCollection.this.finish();
                                return true;
                            case R.id.action_mydiet:
                                String[] temparray3 = mydatabase.get_DetectedFoodname();
                                if(st!=null && temparray3!=null){
                                    if(temparray3.length>st.length){
                                        st = temparray3;
                                    }
                                }
                                Intent i2 = new Intent(MyCollection.this,MyDiet.class);
                                ActivityOptions options2 =
                                        ActivityOptions.makeCustomAnimation(MyCollection.this, R.anim.slide_in_left, R.anim.slide_out_left);
                                if(st!=null) {
                                    i2.putExtra("stringarray", st);
                                }
                                if(imgstr!=null){
                                    i2.putExtra("imagearray",imgstr);
                                }
                                MyCollection.this.startActivity(i2, options2.toBundle());
                                MyCollection.this.finish();
                                return true;
                        }
                        return true;
                    }
                });
    }

    //Creating AsyncTask for Creating Detected Foods Listview
    private class Task extends AsyncTask<Void,Void,Void> {

        protected Void doInBackground(Void... params) {
            String[] uname = mydatabase.get_Foodfrom_mycollection();
            String[] image_arr = mydatabase.get_imagepath_mycollection(uname);

            rowItems = new ArrayList<Detector_Rowitem>();
            for (int i = 0; i < uname.length; i++) {
                Detector_Rowitem item = new Detector_Rowitem(uname[i],image_arr[i]);
                rowItems.add(item);
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            Collection_CustomListViewAdapter adapter = new Collection_CustomListViewAdapter(MyCollection.this,
                    R.layout.detector_list_item, rowItems);
            mylist.setAdapter(adapter);

            mylist.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            //Extracting Information of selected List item
                            String foodname = String.valueOf(adapterView.getItemAtPosition(i));
                            //view = adapterView.getChildAt(i);
                            //ImageView imgview = (ImageView) adapterView.getChildAt(i).findViewById(R.id.img_detectedfood);
                            ImageView imgview = (ImageView) findViewById(R.id.img_detectedfood);
                            // Creating Bitmap for imageview for Intent
                            imgview.buildDrawingCache();
                            Bitmap btmp_img = imgview.getDrawingCache();

                            Bundle extras = new Bundle();
                            extras.putParcelable("imagebitmap", btmp_img);

                            // Intent to Food Information
                            Intent intent = new Intent(MyCollection.this,FoodInfo.class);
                            if(st!=null) {
                                intent.putExtra("stringarray", st);
                            }
                            if(imgstr!=null){
                                intent.putExtra("imagearray",imgstr);
                            }
                            intent.putExtra("name",foodname);
                            intent.putExtras(extras);
                            startActivity(intent);
                            //Toast.makeText(Chat_activity.this,food,Toast.LENGTH_LONG).show();
                        }
                    }
            );
        }
    }

    public void GotoMainActivity(){
        Intent j = new Intent(this, DetectorActivity.class);
        j.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        j.putExtra("stringarray",st);
        startActivity(j);
        this.finish();
        // overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
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

    /*public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_collection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                //GotoMainActivity();
                return true;
            case R.id.collection_item1:
                Intent i = new Intent(MyCollection.this,ChooseActivtity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                if(st!=null) {
                    i.putExtra("stringarray", st);
                }
                startActivity(i);
                MyCollection.this.finish();
                return true;
            case R.id.collection_item2:
                return true;
            case R.id.collection_item3:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
}
