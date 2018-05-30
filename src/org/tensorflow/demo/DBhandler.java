package org.tensorflow.demo;

/**
 * Created by decrypto on 21/4/18.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.tensorflow.demo.env.Logger;

public class DBhandler extends SQLiteOpenHelper {

    private final String myrecipe = "Step 1: In large bowl, dissolve yeast and sugar in water; let stand for 5 minutes. Add oil and salt. Stir in flour, a cup at a time, until a soft dough forms.\n\n"
            +"Step 2: Turn onto floured surface; knead until smooth and elastic, about 2-3 minutes. Place in a greased bowl, turning once to grease the top. Cover and let rise in a warm place until doubled, about 45 minutes. Meanwhile, cook beef and onion over medium heat until no longer pink; drain.\n\n"
            +"Step 3: Punch down dough; divide in half. Press each into a greased 12-in. pizza pan. Combine the tomato sauce, oregano and basil; spread over each crust. Top with beef mixture, green pepper and cheese.\n\n"
            +"Step 4: Bake at 400° for 25-30 minutes or until crust is lightly browned. Yield: 2 pizzas (3 servings each).";

    private final String myrecipe1 = "Step 1: Wash and soak the rice in one container and the dal and fenugreek seeds together in another container for 5-6 hours or over night, depending on the weather.\n\n"
            + "Step 2: Grind dal mixture together to a very smooth consistency. Next grind the rice smooth too and mix the two batters.\n\n"
            + "Step 3: Add salt and enough water to make into a dropping consistency. Leave to ferment over-night or more depending on the weather, till a little spongy.\n\n"
            + "Step 4: This will have to be very swift and will need a bit of practice.\n\n"
            + "Step 5: After spreading the batter, lower the heat and dribble a little oil around the edges so that it seeps under the dosa.\n\n";

    private final String myrecipe2 = "Step 1: Preheat an outdoor or indoor grill for low heat, and lightly oil the grate\n\n"
            + "Step 2: Sprinkle the chicken breasts generously on all sides with the Italian and grill seasonings. Slowly cook on the preheated grill, turning every 10 minutes; brush the chicken with the barbeque sauce each time you turn it.\n\n"
            + "Step 3: While the chicken is cooking, melt the butter in a skillet over medium-low heat. Cook the onions, bell peppers, and mushrooms in the butter, stirring frequently until the vegetables are tender.\n\n"
            + "Step 4: To make the sandwiches, place a chicken breast on each hamburger bun half. Spoon the pepper and onion mixture overtop, and top with a slice of Swiss cheese. Cover with the hamburger bun tops.\n\n";

    private final String myrecipe3 = "No Recipe Found\n\n";

    private static final Logger LOGGER = new Logger();
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FoodData.db";
    private static final String TABLE_NAME= "detectedfood";
    private static final String TABLE_NAME1= "mycollection";
    private static final String TABLE_NAME2= "Nutrient";
    private static final String TABLE_NAME3= "Recipe";

    // for detected food table
    private static final String COLUMN_FOODNAME = "foodname";
    private static final String COLUMN_IMAGEPATH= "imagepath";

    // for mycollection table
    private static final String COLUMN_FOODNAME1 = "foodname";
    private static final String COLUMN_IMAGEPATH1 = "imagepath";

    // for Nutrient table
    private static final String COLUMN_FOODNAME2 = "foodname";
    private static final String COLUMN_CALORIES = "calories";
    private static final String COLUMN_PROTEIN = "protein";
    private static final String COLUMN_CARBOHYDRATE = "carbohydrate";
    private static final String COLUMN_FAT = "fat";
    private static final String COLUMN_CHOLESTEROL = "cholesterol";
    private static final String COLUMN_SODIUM = "sodium";
    private static final String COLUMN_IRON = "iron";

    // for Recipe table
    private static final String COLUMN_FOODNAME3 = "foodname";
    private static final String COLUMN_RECIPE = "recipe";

    //Constructor for Database
    public DBhandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating detectedfood
        String query = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_FOODNAME + " TEXT, " +
                COLUMN_IMAGEPATH + " TEXT " + " DEFAULT " + "'img'," +
                " PRIMARY KEY (" + COLUMN_FOODNAME + ")" +
                ");";
        db.execSQL(query);

        // Creating mycollection
        String query1 = "CREATE TABLE " + TABLE_NAME1 + "(" +
                COLUMN_FOODNAME1 + " TEXT, " +
                COLUMN_IMAGEPATH1 + " TEXT " + " DEFAULT " + "'img'," +
                " PRIMARY KEY (" + COLUMN_FOODNAME1 + ")" +
                ");";
        db.execSQL(query1);

        // creating Nutrient
        String query3 = "CREATE TABLE " + TABLE_NAME2 + "(" +
                COLUMN_FOODNAME2 + " TEXT, " +
                COLUMN_CALORIES + " TEXT, " +
                COLUMN_PROTEIN + " TEXT, " +
                COLUMN_CARBOHYDRATE + " TEXT, " +
                COLUMN_FAT + " TEXT, " +
                COLUMN_CHOLESTEROL + " TEXT, " +
                COLUMN_SODIUM + " TEXT, " +
                COLUMN_IRON + " TEXT," +
                " PRIMARY KEY (" + COLUMN_FOODNAME2 + ")" +
                ");";
        db.execSQL(query3);

        // creating Recipe
        String query4 = "CREATE TABLE " + TABLE_NAME3 + "(" +
                COLUMN_FOODNAME3 + " TEXT, " +
                COLUMN_RECIPE + " TEXT," +
                " PRIMARY KEY (" + COLUMN_FOODNAME3 + ")" +
                ");";
        db.execSQL(query4);

        String query_nutreint_1 = "INSERT INTO " + TABLE_NAME2 + "(foodname,calories,protein,carbohydrate,fat,cholesterol,sodium,iron) " +
                " VALUES (" + "'pizza'" + " , " + " '266' " + " , " + " '11g' " + " , " + " '33g' " + " , " + " '10g' " +
                " , " + " '17mg' " + " , " + " '598mg' " + " , " + " '172mg' " + ");";
        db.execSQL(query_nutreint_1);

        String query_nutreint_2 = "INSERT INTO " + TABLE_NAME2 + "(foodname,calories,protein,carbohydrate,fat,cholesterol,sodium,iron) " +
                " VALUES (" + "'bhelpuri'" + " , " + " '185' " + " , " + " '10g' " + " , " + " '25g' " + " , " + " '2g' " +
                " , " + " '0mg' " + " , " + " '0mg' " + " , " + " '0mg' " + ");";
        db.execSQL(query_nutreint_2);

        String query_nutreint_3 = "INSERT INTO " + TABLE_NAME2 + "(foodname,calories,protein,carbohydrate,fat,cholesterol,sodium,iron) " +
                " VALUES (" + "'burger'" + " , " + " '250' " + " , " + " '32g' " + " , " + " '47g' " + " , " + " '7g' " +
                " , " + " '71mg' " + " , " + " '567mg' " + " , " + " '0mg' " + ");";
        db.execSQL(query_nutreint_3);

        String query_nutreint_4 = "INSERT INTO " + TABLE_NAME2 + "(foodname,calories,protein,carbohydrate,fat,cholesterol,sodium,iron) " +
                " VALUES (" + "'ladoo'" + " , " + " '180' " + " , " + " '4g' " + " , " + " '21g' " + " , " + " '14g' " +
                " , " + " '5mg' " + " , " + " '50mg' " + " , " + " '32mg' " + ");";
        db.execSQL(query_nutreint_4);

        String query_nutreint_5 = "INSERT INTO " + TABLE_NAME2 + "(foodname,calories,protein,carbohydrate,fat,cholesterol,sodium,iron) " +
                " VALUES (" + "'naan'" + " , " + " '336' " + " , " + " '14g' " + " , " + " '80g' " + " , " + " '18g' " +
                " , " + " '12mg' " + " , " + " '472mg' " + " , " + " '168mg' " + ");";
        db.execSQL(query_nutreint_5);

        String query_nutreint_6 = "INSERT INTO " + TABLE_NAME2 + "(foodname,calories,protein,carbohydrate,fat,cholesterol,sodium,iron) " +
                " VALUES (" + "'aaloo tikki'" + " , " + " '120' " + " , " + " '1g' " + " , " + " '12g' " + " , " + " '2g' " +
                " , " + " '24mg' " + " , " + " '0mg' " + " , " + " '0mg' " + ");";
        db.execSQL(query_nutreint_6);

        String query_nutreint_7 = "INSERT INTO " + TABLE_NAME2 + "(foodname,calories,protein,carbohydrate,fat,cholesterol,sodium,iron) " +
                " VALUES (" + "'biryani'" + " , " + " '390' " + " , " + " '16g' " + " , " + " '54g' " + " , " + " '12g' " +
                " , " + " '20mg' " + " , " + " '948mg' " + " , " + " '126mg' " + ");";
        db.execSQL(query_nutreint_7);

        String query_nutreint_8 = "INSERT INTO " + TABLE_NAME2 + "(foodname,calories,protein,carbohydrate,fat,cholesterol,sodium,iron) " +
                " VALUES (" + "'butter chicken'" + " , " + " '384' " + " , " + " '24g' " + " , " + " '8g' " + " , " + " '29g' " +
                " , " + " '102mg' " + " , " + " '860mg' " + " , " + " '106mg' " + ");";
        db.execSQL(query_nutreint_8);

        String query_nutreint_9 = "INSERT INTO " + TABLE_NAME2 + "(foodname,calories,protein,carbohydrate,fat,cholesterol,sodium,iron) " +
                " VALUES (" + "'cake'" + " , " + " '200' " + " , " + " '2g' " + " , " + " '20g' " + " , " + " '7g' " +
                " , " + " '10mg' " + " , " + " '0mg' " + " , " + " '0mg' " + ");";
        db.execSQL(query_nutreint_9);

        String query_nutreint_10 = "INSERT INTO " + TABLE_NAME2 + "(foodname,calories,protein,carbohydrate,fat,cholesterol,sodium,iron) " +
                " VALUES (" + "'daal makhni'" + " , " + " '330' " + " , " + " '13g' " + " , " + " '31g' " + " , " + " '19g' " +
                " , " + " '44mg' " + " , " + " '372mg' " + " , " + " '160mg' " + ");";
        db.execSQL(query_nutreint_10);

        String query_nutreint_11 = "INSERT INTO " + TABLE_NAME2 + "(foodname,calories,protein,carbohydrate,fat,cholesterol,sodium,iron) " +
                " VALUES (" + "'dosa'" + " , " + " '200' " + " , " + " '4g' " + " , " + " '28g' " + " , " + " '12g' " +
                " , " + " '4mg' " + " , " + " '460mg' " + " , " + " '110mg' " + ");";
        db.execSQL(query_nutreint_11);

        String query_nutreint_12 = "INSERT INTO " + TABLE_NAME2 + "(foodname,calories,protein,carbohydrate,fat,cholesterol,sodium,iron) " +
                " VALUES (" + "'gajar halwa'" + " , " + " '185' " + " , " + " '5g' " + " , " + " '32g' " + " , " + " '5g' " +
                " , " + " '2mg' " + " , " + " '158mg' " + " , " + " '26mg' " + ");";
        db.execSQL(query_nutreint_12);

        String query_nutreint_13 = "INSERT INTO " + TABLE_NAME2 + "(foodname,calories,protein,carbohydrate,fat,cholesterol,sodium,iron) " +
                " VALUES (" + "'hotdog'" + " , " + " '233' " + " , " + " '10g' " + " , " + " '31g' " + " , " + " '10g' " +
                " , " + " '20mg' " + " , " + " '0mg' " + " , " + " '0mg' " + ");";
        db.execSQL(query_nutreint_13);

        String query_nutreint_14 = "INSERT INTO " + TABLE_NAME2 + "(foodname,calories,protein,carbohydrate,fat,cholesterol,sodium,iron) " +
                " VALUES (" + "'idli'" + " , " + " '70' " + " , " + " '4g' " + " , " + " '15g' " + " , " + " '4g' " +
                " , " + " '4mg' " + " , " + " '132mg' " + " , " + " '10mg' " + ");";
        db.execSQL(query_nutreint_14);

        String query_nutreint_15 = "INSERT INTO " + TABLE_NAME2 + "(foodname,calories,protein,carbohydrate,fat,cholesterol,sodium,iron) " +
                " VALUES (" + "'kachori'" + " , " + " '201' " + " , " + " '5g' " + " , " + " '21g' " + " , " + " '11g' " +
                " , " + " '15mg' " + " , " + " '630mg' " + " , " + " '50mg' " + ");";
        db.execSQL(query_nutreint_15);

        String query_nutreint_16 = "INSERT INTO " + TABLE_NAME2 + "(foodname,calories,protein,carbohydrate,fat,cholesterol,sodium,iron) " +
                " VALUES (" + "'munchurian'" + " , " + " '183' " + " , " + " '3g' " + " , " + " '21g' " + " , " + " '11g' " +
                " , " + " '2mg' " + " , " + " '387mg' " + " , " + " '45mg' " + ");";
        db.execSQL(query_nutreint_16);

        String query_nutreint_17 = "INSERT INTO " + TABLE_NAME2 + "(foodname,calories,protein,carbohydrate,fat,cholesterol,sodium,iron) " +
                " VALUES (" + "'poha'" + " , " + " '54' " + " , " + " '7g' " + " , " + " '69g' " + " , " + " '6g' " +
                " , " + " '6mg' " + " , " + " '446mg' " + " , " + " '220mg' " + ");";
        db.execSQL(query_nutreint_17);

        String query_nutreint_18 = "INSERT INTO " + TABLE_NAME2 + "(foodname,calories,protein,carbohydrate,fat,cholesterol,sodium,iron) " +
                " VALUES (" + "'sandwich'" + " , " + " '352' " + " , " + " '20g' " + " , " + " '33g' " + " , " + " '16g' " +
                " , " + " '58mg' " + " , " + " '771mg' " + " , " + " '190mg' " + ");";
        db.execSQL(query_nutreint_18);

        String query_nutreint_19 = "INSERT INTO " + TABLE_NAME2 + "(foodname,calories,protein,carbohydrate,fat,cholesterol,sodium,iron) " +
                " VALUES (" + "'sarso ka saag'" + " , " + " '166' " + " , " + " '12g' " + " , " + " '5g' " + " , " + " '16g' " +
                " , " + " '3mg' " + " , " + " '328mg' " + " , " + " '25mg' " + ");";
        db.execSQL(query_nutreint_19);

        String query_nutreint_20 = "INSERT INTO " + TABLE_NAME2 + "(foodname,calories,protein,carbohydrate,fat,cholesterol,sodium,iron) " +
                " VALUES (" + "'softdrink'" + " , " + " '80' " + " , " + " '0g' " + " , " + " '39g' " + " , " + " '0g' " +
                " , " + " '0mg' " + " , " + " '45mg' " + " , " + " '10mg' " + ");";
        db.execSQL(query_nutreint_20);

        String query_nutreint_21 = "INSERT INTO " + TABLE_NAME2 + "(foodname,calories,protein,carbohydrate,fat,cholesterol,sodium,iron) " +
                " VALUES (" + "'sweets'" + " , " + " '145' " + " , " + " '2g' " + " , " + " '17g' " + " , " + " '8g' " +
                " , " + " '10mg' " + " , " + " '40mg' " + " , " + " '27mg' " + ");";
        db.execSQL(query_nutreint_21);

        String query_nutreint_22 = "INSERT INTO " + TABLE_NAME2 + "(foodname,calories,protein,carbohydrate,fat,cholesterol,sodium,iron) " +
                " VALUES (" + "'uttapam'" + " , " + " '170' " + " , " + " '4g' " + " , " + " '24g' " + " , " + " '9g' " +
                " , " + " '2mg' " + " , " + " '243mg' " + " , " + " '10mg' " + ");";
        db.execSQL(query_nutreint_22);




        String query_recipe_1 = "INSERT INTO " + TABLE_NAME3 + "(foodname,recipe) " +
                " VALUES (" + "'bhelpuri'" + " , " + " '"+ myrecipe1 + "' " + ");";
        db.execSQL(query_recipe_1);

        String query_recipe_2 = "INSERT INTO " + TABLE_NAME3 + "(foodname,recipe) " +
                " VALUES (" + "'pizza'" + " , " + " '"+ myrecipe + "' " + ");";
        db.execSQL(query_recipe_2);

        String query_recipe_3 = "INSERT INTO " + TABLE_NAME3 + "(foodname,recipe) " +
                " VALUES (" + "'ladoo'" + " , " + " '"+ myrecipe1 + "' " + ");";
        db.execSQL(query_recipe_3);

        String query_recipe_4 = "INSERT INTO " + TABLE_NAME3 + "(foodname,recipe) " +
                " VALUES (" + "'naan'" + " , " + " '"+ myrecipe3 + "' " + ");";
        db.execSQL(query_recipe_4);

        String query_recipe_5 = "INSERT INTO " + TABLE_NAME3 + "(foodname,recipe) " +
                " VALUES (" + "'burger'" + " , " + " '"+ myrecipe + "' " + ");";
        db.execSQL(query_recipe_5);

        String query_recipe_6 = "INSERT INTO " + TABLE_NAME3 + "(foodname,recipe) " +
                " VALUES (" + "'aaloo tikki'" + " , " + " '"+ myrecipe1 + "' " + ");";
        db.execSQL(query_recipe_6);

        String query_recipe_7 = "INSERT INTO " + TABLE_NAME3 + "(foodname,recipe) " +
                " VALUES (" + "'biryani'" + " , " + " '"+ myrecipe2 + "' " + ");";
        db.execSQL(query_recipe_7);

        String query_recipe_8 = "INSERT INTO " + TABLE_NAME3 + "(foodname,recipe) " +
                " VALUES (" + "'butter chicken'" + " , " + " '"+ myrecipe1 + "' " + ");";
        db.execSQL(query_recipe_8);

        String query_recipe_9 = "INSERT INTO " + TABLE_NAME3 + "(foodname,recipe) " +
                " VALUES (" + "'cake'" + " , " + " '"+ myrecipe1 + "' " + ");";
        db.execSQL(query_recipe_9);

        String query_recipe_10 = "INSERT INTO " + TABLE_NAME3 + "(foodname,recipe) " +
                " VALUES (" + "'daal makhni'" + " , " + " '"+ myrecipe + "' " + ");";
        db.execSQL(query_recipe_10);

        String query_recipe_11 = "INSERT INTO " + TABLE_NAME3 + "(foodname,recipe) " +
                " VALUES (" + "'dosa'" + " , " + " '"+ myrecipe1 + "' " + ");";
        db.execSQL(query_recipe_11);

        String query_recipe_12 = "INSERT INTO " + TABLE_NAME3 + "(foodname,recipe) " +
                " VALUES (" + "'gajar halwa'" + " , " + " '"+ myrecipe + "' " + ");";
        db.execSQL(query_recipe_12);

        String query_recipe_13 = "INSERT INTO " + TABLE_NAME3 + "(foodname,recipe) " +
                " VALUES (" + "'hotdog'" + " , " + " '"+ myrecipe2 + "' " + ");";
        db.execSQL(query_recipe_13);

        String query_recipe_14 = "INSERT INTO " + TABLE_NAME3 + "(foodname,recipe) " +
                " VALUES (" + "'idli'" + " , " + " '"+ myrecipe1 + "' " + ");";
        db.execSQL(query_recipe_14);

        String query_recipe_15 = "INSERT INTO " + TABLE_NAME3 + "(foodname,recipe) " +
                " VALUES (" + "'kachori'" + " , " + " '"+ myrecipe1 + "' " + ");";
        db.execSQL(query_recipe_15);

        String query_recipe_16 = "INSERT INTO " + TABLE_NAME3 + "(foodname,recipe) " +
                " VALUES (" + "'munchurian'" + " , " + " '"+ myrecipe2 + "' " + ");";
        db.execSQL(query_recipe_16);

        String query_recipe_17 = "INSERT INTO " + TABLE_NAME3 + "(foodname,recipe) " +
                " VALUES (" + "'poha'" + " , " + " '"+ myrecipe1 + "' " + ");";
        db.execSQL(query_recipe_17);

        String query_recipe_18 = "INSERT INTO " + TABLE_NAME3 + "(foodname,recipe) " +
                " VALUES (" + "'sandwich'" + " , " + " '"+ myrecipe2 + "' " + ");";
        db.execSQL(query_recipe_18);

        String query_recipe_19 = "INSERT INTO " + TABLE_NAME3 + "(foodname,recipe) " +
                " VALUES (" + "'sarso ka saag'" + " , " + " '"+ myrecipe1 + "' " + ");";
        db.execSQL(query_recipe_19);

        String query_recipe_20 = "INSERT INTO " + TABLE_NAME3 + "(foodname,recipe) " +
                " VALUES (" + "'softdrink'" + " , " + " '"+ myrecipe3 + "' " + ");";
        db.execSQL(query_recipe_20);

        String query_recipe_21 = "INSERT INTO " + TABLE_NAME3 + "(foodname,recipe) " +
                " VALUES (" + "'sweets'" + " , " + " '"+ myrecipe3 + "' " + ");";
        db.execSQL(query_recipe_21);

        String query_recipe_22 = "INSERT INTO " + TABLE_NAME3 + "(foodname,recipe) " +
                " VALUES (" + "'uttapam'" + " , " + " '"+ myrecipe1 + "' " + ");";
        db.execSQL(query_recipe_22);

        LOGGER.i("Created and Added Data to nutrient and recipe table");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        onCreate(db);
    }



    //Get info regarding foodname
    public String[] get_foodinfo(String foodname) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "Select * from " + TABLE_NAME2 + " where " + COLUMN_FOODNAME2 + " = '" + foodname + "';";
        Cursor c = db.rawQuery(query, null);
        String[] str = new String[7];
        boolean chk = c.moveToFirst();
        if (!chk) {
            c.close();
            return null;
        } else {
            if (c.getString(c.getColumnIndex("foodname")) == null) {
                c.close();
                return null;
            } else {
                String mystr;
                mystr = c.getString(c.getColumnIndex("calories"));
                str[0] = mystr;
                mystr = c.getString(c.getColumnIndex("protein"));
                str[1] = mystr;
                mystr = c.getString(c.getColumnIndex("carbohydrate"));
                str[2] = mystr;
                mystr = c.getString(c.getColumnIndex("fat"));
                str[3] = mystr;
                mystr = c.getString(c.getColumnIndex("cholesterol"));
                str[4] = mystr;
                mystr = c.getString(c.getColumnIndex("sodium"));
                str[5] = mystr;
                mystr = c.getString(c.getColumnIndex("iron"));
                str[6] = mystr;
            }
            db.close();
            return str;
        }
    }


    public String get_recipeinfo(String foodname) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "Select * from " + TABLE_NAME3 + " where " + COLUMN_FOODNAME3 + " = '" + foodname + "';";
        Cursor c = db.rawQuery(query, null);
        String str;
        boolean chk = c.moveToFirst();
        if (!chk) {
            c.close();
            return null;
        } else {
            if (c.getString(c.getColumnIndex("foodname")) == null) {
                c.close();
                return null;
            } else {
                str = c.getString(c.getColumnIndex("recipe"));
            }
            db.close();
            return str;
        }
    }


    // Use this function to delete the table when app starts
    public void Delete_detectedfoodtable(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        LOGGER.i("Deleted detectedfood table");
    }

    // Use this function to create the table when app starts
    public void Create_detectedfoodtable(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_FOODNAME + " TEXT, " +
                COLUMN_IMAGEPATH + " TEXT " + " DEFAULT " + "'img'," +
                " PRIMARY KEY (" + COLUMN_FOODNAME + ")" +
                ");";
        LOGGER.i("Created detectedfood table");
        db.execSQL(query);
    }

    //Adding a new row to the Database
    public void addDetectedFooditem(String foodname){
        String query = "INSERT INTO " + TABLE_NAME + "(foodname,imagepath) " +
                " VALUES (" + "'" + foodname + "'" + " , " + " 'acb' " + ");";
       /* String query1 = "INSERT INTO " + TABLE_NAME1 + "(username,phonenum) " +
                " VALUES (" + "'" + username + "'" + " , " + "'" + number + "'" + ");";*/
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
        LOGGER.i("Added " + foodname + "to the detectedfood table");
        //db.execSQL(query1);
        db.close();
    }

    //Adding image to the existing user
    public void addimage(String foodname, String imagepath ){
        String query = "UPDATE " + TABLE_NAME +
                " SET " + COLUMN_IMAGEPATH + " = '" + imagepath +
                "' WHERE " + COLUMN_FOODNAME + " = '" + foodname + "';";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
        String query1 = "UPDATE " + TABLE_NAME1 +
                " SET " + COLUMN_IMAGEPATH1 + " = '" + imagepath +
                "' WHERE " + COLUMN_FOODNAME1 + " = '" + foodname + "';";
        db.execSQL(query1);
        db.close();
    }

    //Getting all the username from the Database
    public String[] get_DetectedFoodname(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor crs = db.rawQuery("SELECT * FROM detectedfood", null);
        String[] array = new String[crs.getCount()];
        int i = 0;
        while(crs.moveToNext()){
            String uname = crs.getString(crs.getColumnIndex("foodname"));
            array[i] = uname;
            i++;
        }
        LOGGER.i("extracting string array from detectedfood table");
        crs.close();
        db.close();
        return array;
    }


    public  boolean CheckIsDataAlreadyInDBorNot(String foodname){
        SQLiteDatabase db = getWritableDatabase();
        String Query = "Select * from " + TABLE_NAME + " where " + COLUMN_FOODNAME + " = '" + foodname + "';";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        LOGGER.i("Checking if " + foodname + "already exist in detectedfood table or not");
        cursor.close();
        db.close();
        return true;
    }

    public String[] get_imagepath(String[] users){
        SQLiteDatabase db = getReadableDatabase();
        Cursor crs = db.rawQuery("SELECT * FROM detectedfood", null);
        String[] array = new String[crs.getCount()];
        int i = 0;
        for(int j=0;j<users.length;j++) {
            String str =  check_image(users[j]);
            array[i] = str;
            i++;
        }
        crs.close();
        db.close();
        return array;
    }

    public String check_image(String foodname) {
        String image = "acb",path;
        SQLiteDatabase db = getWritableDatabase();
        String query = "Select * from " + TABLE_NAME + " where " + COLUMN_FOODNAME + " = '" + foodname + "';";
        Cursor c = db.rawQuery(query, null);
        boolean chk = c.moveToFirst();
        if (!chk) {
            return null;
        }
        else {
            if (c.getString(c.getColumnIndex("foodname")) == null) {
                c.close();
                return null;
            } else {
                if (c.getString(c.getColumnIndex("imagepath")).compareTo(image) ==0 ) {
                    c.close();
                    return null;
                }
                else
                {
                    path= c.getString(c.getColumnIndex("imagepath"));
                }
            }
            db.close();
            return path;
        }
    }

    //Delete a row from the database
   /* public void deleteuser(String username){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + " = '" + username + "';");
        db.close();
    }*/

    //Adding a new row to the mycollection table
    public void addFoodto_mycollection(String foodname){
        String query = "INSERT INTO " + TABLE_NAME1 + "(foodname,imagepath) " +
                " VALUES (" + "'" + foodname + "'" + " , " + " 'acb' " + ");";
       /* String query1 = "INSERT INTO " + TABLE_NAME1 + "(username,phonenum) " +
                " VALUES (" + "'" + username + "'" + " , " + "'" + number + "'" + ");";*/
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
        LOGGER.i("Added " + foodname + "to the mycollection table");
        //db.execSQL(query1);
        db.close();
    }

    //Adding image to the existing user
    public void addimageto_mycollection(String foodname, String imagepath ){
        String query = "UPDATE " + TABLE_NAME1 +
                " SET " + COLUMN_IMAGEPATH1 + " = '" + imagepath +
                "' WHERE " + COLUMN_FOODNAME1 + " = '" + foodname + "';";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    //Getting all the foodname from the mycollection
    public String[] get_Foodfrom_mycollection(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor crs = db.rawQuery("SELECT * FROM mycollection", null);
        String[] array = new String[crs.getCount()];
        int i = 0;
        while(crs.moveToNext()){
            String uname = crs.getString(crs.getColumnIndex("foodname"));
            array[i] = uname;
            i++;
        }
        LOGGER.i("extracting string array from mycollection table");
        crs.close();
        db.close();
        return array;
    }


    public  boolean CheckIsDataAlreadyIn_mycollection(String foodname){
        SQLiteDatabase db = getWritableDatabase();
        String Query = "Select * from " + TABLE_NAME1 + " where " + COLUMN_FOODNAME1 + " = '" + foodname + "';";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        LOGGER.i("Checking if " + foodname + "already exist in mycollection table or not");
        cursor.close();
        db.close();
        return true;
    }

    public String[] get_imagepath_mycollection(String[] users){
        SQLiteDatabase db = getReadableDatabase();
        Cursor crs = db.rawQuery("SELECT * FROM mycollection", null);
        String[] array = new String[crs.getCount()];
        int i = 0;
        for(int j=0;j<users.length;j++) {
            String str =  check_image_mycollection(users[j]);
            array[i] = str;
            i++;
        }
        crs.close();
        db.close();
        return array;
    }

    public String check_image_mycollection(String foodname) {
        String image = "acb",path;
        SQLiteDatabase db = getWritableDatabase();
        String query = "Select * from " + TABLE_NAME1 + " where " + COLUMN_FOODNAME1 + " = '" + foodname + "';";
        Cursor c = db.rawQuery(query, null);
        boolean chk = c.moveToFirst();
        if (!chk) {
            return null;
        }
        else {
            if (c.getString(c.getColumnIndex("foodname")) == null) {
                c.close();
                return null;
            } else {
                if (c.getString(c.getColumnIndex("imagepath")).compareTo(image) ==0 ) {
                    c.close();
                    return null;
                }
                else
                {
                    path= c.getString(c.getColumnIndex("imagepath"));
                }
            }
            db.close();
            return path;
        }
    }

    public String databasetostring(){
        String dbstring="";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE 1";

        //cursor point to a location result
        Cursor c = db.rawQuery(query, null);
        //move to first row
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("username"))!=null){
                dbstring+=c.getString(c.getColumnIndex("username"));
                dbstring+="\n";
            }
        }
        db.close();
        return dbstring;
    }

    //To check the database for the user credentials
    /*public boolean check_database(String username, String password) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "Select * from " + TABLE_NAME + " where " + COLUMN_USERNAME + " = '" + username + "';";
        Cursor c = db.rawQuery(query, null);
        boolean chk = c.moveToFirst();
        if (!chk) {
            c.close();
            return false;
        }
        else {
            if (c.getString(c.getColumnIndex("username")) == null) {
                c.close();
                return false;
            } else {
                if (c.getString(c.getColumnIndex("password")).compareTo(password) != 0) {
                    c.close();
                    return false;
                }
            }
            return true;
        }
    }*/

    /*public String get_phonenum(String username) {
        String path,DEFAULT="1234567890";
        SQLiteDatabase db = getWritableDatabase();
        String query = "Select * from " + TABLE_NAME1 + " where " + COLUMN_USERNAME1 + " = '" + username + "';";
        Cursor c = db.rawQuery(query, null);
        boolean chk = c.moveToFirst();
        if (!chk) {
            return null;
        }
        else {
            if (c.getString(c.getColumnIndex("username")) == null) {
                c.close();
                return DEFAULT;
            } else {
                path= c.getString(c.getColumnIndex("phonenum"));
            }
            db.close();
            return path;
        }
    }*/

}

