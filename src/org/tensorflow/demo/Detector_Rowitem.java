package org.tensorflow.demo;

/**
 * Created by decrypto on 22/4/18.
 */

public class Detector_Rowitem {

    private String Foodname;
    private String Imageid;

    public Detector_Rowitem(String foodname,String imageid){
        this.Foodname = foodname;
        this.Imageid = imageid;
    }
    public String getFoodname(){
        return this.Foodname;
    }
    public String getImageid(){
        return Imageid;
    }
    public void setImageid(String imageid){
        this.Imageid = imageid;
    }
    public void setFoodname(String foodname){
        this.Foodname = foodname;
    }
    @Override
    public String toString() {
        return Foodname + "\n";
    }
}
