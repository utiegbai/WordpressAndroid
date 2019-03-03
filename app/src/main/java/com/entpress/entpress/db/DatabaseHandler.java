package com.entpress.entpress.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.entpress.entpress.models.UserDetails;

import org.json.JSONException;
import org.json.JSONObject;

public class DatabaseHandler extends SQLiteOpenHelper {

	//user details columns and table
	public String USEDETAILS_TABLE = "user_table";
	public static String USERID = "userid";
	public static String NAME = "name";
	public static String USERNAME = "username";
	public static String EMAIL = "email";
	public static String BIO = "bio";
	public static String PASSWORD = "password";
	public static String VERIFIED = "verified";
	public static String CREATED_AT = "created_at";
	public static String UPDATED_AT = "updated_at";
	public static String TOKEN = "token";

	private static final String DATABASE_NAME = "yanpals_mobile.db";
	private static final int DATABASE_VERSION = 1;
	Context context;

	private final String CREATE_USER_TABLE = "create table "
			+ USEDETAILS_TABLE + "("
			+ USERID + " text not null PRIMARY KEY, "
			+ NAME + " text, "
			+ USERNAME + " text, "
			+ EMAIL + " text, "
			+ BIO + " text, "
			+ PASSWORD + " text, "
			+ VERIFIED + " text, "
			+ CREATED_AT + " text, "
			+ UPDATED_AT + " text, "
			+ TOKEN + " text);";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USEDETAILS_TABLE);
	}

	//user data methods

    /**
     * add, fetch and delete table
     */

    // Adding new user
    public void addUserData(UserDetails userDetails) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USERID, userDetails.getUserId());
        values.put(NAME, userDetails.getName());
        values.put(USERNAME, userDetails.getUsername());
        values.put(EMAIL, userDetails.getEmail());
        values.put(BIO, userDetails.getBio());
        values.put(PASSWORD, userDetails.getPassword());
        values.put(VERIFIED, userDetails.getVerified());
        values.put(CREATED_AT, userDetails.getCreated_at());
        values.put(UPDATED_AT, userDetails.getUpdated_at());
        values.put(TOKEN, userDetails.getToken());

        db.insertWithOnConflict(USEDETAILS_TABLE, null, values, SQLiteDatabase.CONFLICT_ABORT);
        db.close();
    }

    public UserDetails getUserData() {
        UserDetails userDetails = new UserDetails();
        String selectQuery = "SELECT * FROM " + USEDETAILS_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        //
        if (cursor.moveToFirst()) {
            userDetails.setUserId(cursor.getString(0));
            userDetails.setName(cursor.getString(1));
            userDetails.setUsername(cursor.getString(2));
            userDetails.setEmail(cursor.getString(3));
            userDetails.setBio(cursor.getString(4));
            userDetails.setPassword(cursor.getString(5));
            userDetails.setVerified(cursor.getString(6));
            userDetails.setCreated_at(cursor.getString(7));
            userDetails.setUpdated_at(cursor.getString(8));
            userDetails.setToken(cursor.getString(9));

        }
        cursor.close();
        db.close();
        return userDetails;
    }

    public int updateUserData(String field, String userid, String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(field, data);
        // updating row
        return db.update(USEDETAILS_TABLE, values, USERID + " = ?",
                new String[]{userid});
    }


    public void clearUserDataTable(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(USEDETAILS_TABLE, null, null);
		db.close();
	}

    public String getLoggedInUser(){
        String requestBody = "";
        UserDetails userDetails = getUserData();
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("controller", "moment");
            jsonBody.put("action", "user_join");
            jsonBody.put("userId", userDetails.getUserId());
            requestBody = jsonBody.toString();
        } catch (JSONException e) {
            Log.e("parse error",e.getMessage());
            e.printStackTrace();
        }
        return requestBody;
    }
}
