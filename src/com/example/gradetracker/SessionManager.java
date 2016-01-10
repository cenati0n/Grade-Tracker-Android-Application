package com.example.gradetracker;

import java.util.HashMap;

import com.google.gson.Gson;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
 
public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;
     
    // Editor for Shared preferences
    Editor editor;
     
    // Context
    Context _context;
     
    // Shared pref mode
    int PRIVATE_MODE = 0;
     
    // Sharedpref file name
    private static final String PREF_NAME = "LoginPref";
     
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
     
    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";
     
    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";
     
    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        
    }
     
    /**
     * Create login session
     * */
/*//   public void createLoginSession(String name, String email){
//        // Storing login value as TRUE
//        editor.putBoolean(IS_LOGIN, true);
//         
//        // Storing name in pref
//        editor.putString(KEY_NAME, name);
//         
//        // Storing email in pref
//        editor.putString(KEY_EMAIL, email);
////        Gson gson = new Gson();
////        String json = gson.toJson(fac);
////        editor.putString("FacultyObject", json);
//        
//        // commit changes
//        editor.commit();
//    }   
*/    
    public void createLoginSession(String name, String email,Faculty fac ){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
         
        // Storing name in pref
        editor.putString(KEY_NAME, name);
         
        // Storing email in pref
       editor.putString(KEY_EMAIL, email);
       Gson gson = new Gson();
        String json = gson.toJson(fac);
        editor.putString("FacultyObject", json);
        
        // commit changes
        editor.commit();
    }  
    public void createLoginSessionStudent(String name, String email,Student stu){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
         
        // Storing name in pref
        editor.putString(KEY_NAME, name);
         
        // Storing email in pref
       editor.putString(KEY_EMAIL, email);
       Gson gson = new Gson();
        String json = gson.toJson(stu);
        editor.putString("StudentObject", json);
        
        // commit changes
        editor.commit();
    }  
    public void putSessionCourse(String cName)
    {
    	editor.putString("SelCourse", cName);
    	editor.commit();
    }
     
    public void putuid(String uid)
    {
    	editor.putString("uid", uid);
    	editor.commit();
    }
    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, Login.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
             
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             
            // Staring Login Activity
            _context.startActivity(i);
        }
         
    }
     
     
     
    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
         
        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
         
        // return user
        return user;
    }
     
    public Faculty getFaculty()
    {
    	Gson gson = new Gson();
    	String json = pref.getString("FacultyObject", "");
    	return gson.fromJson(json, Faculty.class);
    	
    }
    public Student getStudent()
    {
    	Gson gson = new Gson();
    	String json = pref.getString("StudentObject", "");
    	return gson.fromJson(json, Student.class);
    	
    }
    public String getSessionCourse()
    {
    	
    	return pref.getString("SelCourse",null);
    	
    }
    public String getuid()
    {
    	
    	return pref.getString("uid",null);
    	
    }
    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
         
        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, Login.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         
        // Staring Login Activity
        _context.startActivity(i);
    }
     
    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}