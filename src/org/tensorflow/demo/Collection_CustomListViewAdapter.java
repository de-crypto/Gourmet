package org.tensorflow.demo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

/**
 * Created by decrypto on 23/4/18.
 */
/**
 * Created by decrypto on 22/4/18.
 */


public class Collection_CustomListViewAdapter extends ArrayAdapter<Detector_Rowitem> {

    Context context;

    public Collection_CustomListViewAdapter(Context context, int resourceId,
                                          List<Detector_Rowitem> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView txtDesc;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Detector_Rowitem rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.detector_list_item, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.txt_detectedfood);
            holder.imageView = (ImageView) convertView.findViewById(R.id.img_detectedfood);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        // holder.txtDesc.setText(rowItem.getDesc());
        String profile_destination = rowItem.getImageid();
        holder.txtTitle.setText(rowItem.getFoodname());
        if(profile_destination==null || profile_destination.compareTo("acb")==0)
        {
            //Do Nothing ;)
        }
        else {
            /*File imgFile = new File(profile_destination);
            if (imgFile.exists()) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inScaled = false;
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(), options);
                holder.imageView.setImageBitmap(myBitmap);
            }*/
        }
        //holder.imageView.setImageResource(rowItem.getImageid());
        return convertView;
    }

}