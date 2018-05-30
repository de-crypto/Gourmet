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

public class Nutrient_fragment extends Fragment{
    private static final String TAG = "Nutrient_fragment";
    public String final_foodname;

    //Initialize the Database
    DBhandler mydatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nutrient_fragment,container,false);

        // create the database
        mydatabase = new DBhandler(view.getContext(),null,null,1);

        // to get foodname in the viewpager
        FoodInfo activity = (FoodInfo) getActivity();
        final_foodname = activity.send_foodname();

        // Extract info from database for foodname
        String[] str = mydatabase.get_foodinfo(final_foodname);

        TextView t1 = (TextView) view.findViewById(R.id.txt_calorie);
        TextView t2 = (TextView) view.findViewById(R.id.txt_protein);
        TextView t3 = (TextView) view.findViewById(R.id.txt_carbo);
        TextView t4 = (TextView) view.findViewById(R.id.txt_fat);
        TextView t5 = (TextView) view.findViewById(R.id.txt_choles);
        TextView t6 = (TextView) view.findViewById(R.id.txt_sodium);
        TextView t7 = (TextView) view.findViewById(R.id.txt_iron);

        if(str!=null) {
            t1.setText(str[0]);
            t2.setText(str[1]);
            t3.setText(str[2]);
            t4.setText(str[3]);
            t5.setText(str[4]);
            t6.setText(str[5]);
            t7.setText(str[6]);
            //Toast.makeText(view.getContext(),str[0] + ", " + str[1],Toast.LENGTH_LONG).show();
        }

        view.setNestedScrollingEnabled(true);

        //Toast.makeText(view.getContext(),final_foodname,Toast.LENGTH_LONG).show();

        return view;
    }
}
