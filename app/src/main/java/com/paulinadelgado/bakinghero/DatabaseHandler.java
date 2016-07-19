package com.paulinadelgado.bakinghero;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by pauparapapau on 29/06/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "recipesManager";

    // Recipes table name
    private static final String TABLE_RECIPES = "recipes";

    // Recipes Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_CATEGORY ="category";
    private static final String KEY_NAME = "name";
    private static final String KEY_ING = "ingredients";
    private static final String KEY_INST = "instructions";
    private static final String KEY_NOTES = "notes";
    private final ArrayList<Recipe> recipe_list = new ArrayList<Recipe>();

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RECIPES_TABLE = "CREATE TABLE " + TABLE_RECIPES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CATEGORY + " TEXT,"
                + KEY_NAME + " TEXT," + KEY_ING + " TEXT," + KEY_INST + " TEXT," + KEY_NOTES + " TEXT" + ")";
        db.execSQL(CREATE_RECIPES_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);

        // Create tables again
        onCreate(db);
    }


    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new Recipe
    public void Add_Recipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORY,recipe.getCategory()); //Recipe
        values.put(KEY_NAME, recipe.getName()); // Recipe Nama
        values.put(KEY_ING, recipe.getIngredients()); //
        values.put(KEY_INST, recipe.getInstructions()); //
        values.put(KEY_NOTES, recipe.getNotes());
        // Inserting Row
        db.insert(TABLE_RECIPES, null, values);
        db.close(); // Closing database connection
    }

    // Getting single Recipe
    Recipe Get_Recipe(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_RECIPES, new String[] { KEY_ID, KEY_CATEGORY,
                        KEY_NAME, KEY_ING, KEY_INST,KEY_NOTES }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Recipe recipe = new Recipe(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
        // return Recipe
        cursor.close();
        db.close();

        return recipe;
    }

    // Getting All Recipes
    public ArrayList<Recipe> Get_Recipes() {
        try {
            recipe_list.clear();

            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_RECIPES ;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Recipe recipe = new Recipe();
                    recipe.setID(Integer.parseInt(cursor.getString(0)));
                    recipe.setCategory(cursor.getString(1));
                    recipe.setName(cursor.getString(2));
                    recipe.setIngredients(cursor.getString(3));
                    recipe.setInstructions(cursor.getString(4));
                    recipe.setNotes(cursor.getString(5));
                    // Adding Recipe to list
                    recipe_list.add(recipe);
                } while (cursor.moveToNext());
            }

            // return Recipe list
            cursor.close();
            db.close();
            return recipe_list;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("all_recipe", "" + e);
        }

        return recipe_list;
    }







    // Updating single Recipe
    public int Update_Recipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORY, recipe.getCategory());
        values.put(KEY_NAME, recipe.getName());
        values.put(KEY_ING, recipe.getIngredients());
        values.put(KEY_INST, recipe.getInstructions());
        values.put(KEY_NOTES,recipe.getNotes());


        // updating row
        return db.update(TABLE_RECIPES, values, KEY_ID + " = ?",
                new String[] { String.valueOf(recipe.getID()) });
    }

    // Deleting single Recipe
    public void Delete_Recipe(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RECIPES, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    // Getting Recipes Count
    public int Get_Total_Recipes() {
        String countQuery = "SELECT  * FROM " + TABLE_RECIPES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}