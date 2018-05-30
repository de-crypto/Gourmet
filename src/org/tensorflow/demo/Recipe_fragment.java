package org.tensorflow.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by decrypto on 24/5/18.
 */

public class Recipe_fragment extends Fragment {
    private static final String TAG = "Recipe_fragment";
    public String final_foodname;

    //Initialize the Database
    DBhandler mydatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_fragment,container,false);

        // to get foodname in the viewpager
        FoodInfo activity = (FoodInfo) getActivity();
        final_foodname = activity.send_foodname();

        // create the database
        mydatabase = new DBhandler(view.getContext(),null,null,1);

        String myrecipe = mydatabase.get_recipeinfo(final_foodname);

        TextView txtview = (TextView) view.findViewById(R.id.txt_recipe);

        if(myrecipe!=null){
            txtview.setText(myrecipe);
        }

        txtview.setMovementMethod(new ScrollingMovementMethod());
        view.setNestedScrollingEnabled(true);

        //Toast.makeText(view.getContext(),final_foodname,Toast.LENGTH_LONG).show();

        return view;
    }
}
