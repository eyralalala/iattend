package com.example.fyp.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

/**
 * A utility activity from which other activities are expending
 */
public class BaseActivity extends AppCompatActivity {
    private ProgressDialog dialog;
    private SharedPreferences preferences; //instance of SharedPreferences
    private FirebaseAuth auth; //instance of FirebaseAuth
    private DatabaseReference usersReference; // instance of Firebase Firestore CollectionReference;
    private DatabaseReference databaseReference; //instance of Firebase database reference
    private StorageReference storageReference; // instance of Firebase Storage reference
    private ImageHelper imageHelper;

    /**
     * instance of {@link ImageHelper} class
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new ProgressDialog(this);
        preferences = getSharedPreferences(Constants.prefName, Context.MODE_PRIVATE);
        auth = FirebaseAuth.getInstance();
        usersReference = FirebaseDatabase.getInstance().getReference(Constants.users);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        imageHelper = new ImageHelper(this);
    }

    /**
     * A function for showing progress dialog
     *
     * @param message: String value
     */
    public void showProgressDialog(String message) {
        dialog.show(message);
    }

    /**
     * A function for changing message on an already shown Progress dialog
     *
     * @param message: String value
     */
    public void setDialogMessage(String message) {
        dialog.setMessage(message);
    }

    public void loadCloudImage(String url, ImageView view) {
        imageHelper.loadCloudImage(url, view);
    }

    public <T> void goTo(Class<T> intent) {
        startActivity(new Intent(getApplicationContext(), intent));
    }

    public <T> void goTo(Class<T> intent, Boolean finishThis) {
        if (finishThis) {
            startActivity(new Intent(getApplicationContext(), intent));
            finish();
        } else {
            startActivity(new Intent(getApplicationContext(), intent));
        }
    }

    public <T> void goToWithFinishAffinity(Class<T> intent) {
        startActivity(new Intent(getApplicationContext(), intent));
        finishAffinity();
    }


    public void goBack() {
        onBackPressed();
    }

    /**
     * For hiding progress dialog
     */
    public void hideProgressDialog() {
        dialog.dismiss();
    }

    public void alert(String title, String positiveText, DialogInterface.OnClickListener positiveButton, String negativeText, DialogInterface.OnClickListener negativeListener) {
        new MaterialAlertDialogBuilder(this)
                .setTitle(title)
                .setPositiveButton(positiveText, positiveButton)
                .setNegativeButton(negativeText, negativeListener)
                .show();
    }
    public void alert(String title, String positiveText, DialogInterface.OnClickListener positiveButton) {
        new MaterialAlertDialogBuilder(this)
                .setTitle(title)
                .setPositiveButton(positiveText, positiveButton)
                .show();
    }

    public void alert(String title, String message, String positiveText, DialogInterface.OnClickListener positiveButton, String negativeText, DialogInterface.OnClickListener negativeListener) {
        new MaterialAlertDialogBuilder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveText, positiveButton)
                .setNegativeButton(negativeText, negativeListener)
                .show();
    }

    /**
     * For saving String value to {preferences}
     *
     * @param key    : a constant used for saving
     * @param value: value to be saved
     */
    public void savePreferenceString(String key, String value) {
        preferences.edit().putString(key, value).apply();
    }

    public String getPreferenceString(String key) {
        return preferences.getString(key, "nullString");
    }

    /**
     * For saving Float value to {preferences}
     *
     * @param key    : a constant used for saving
     * @param value: value to be saved
     */
    public void savePreferenceFloat(String key, Float value) {
        preferences.edit().putFloat(key, value).apply();
    }

    public Float getPreferenceFloat(String key) {
        return preferences.getFloat(key, 0f);
    }

    /**
     * For saving Integer value to {preferences}
     *
     * @param key    : a constant used for saving
     * @param value: value to be saved
     */
    public void savePreferenceInt(String key, Integer value) {
        preferences.edit().putInt(key, value).apply();
    }

    public Integer getPreferenceInt(String key) {
        return preferences.getInt(key, 0);
    }

    /**
     * For saving Long value to {preferences}
     *
     * @param key    : a constant used for saving
     * @param value: value to be saved
     */
    public void savePreferenceLong(String key, Long value) {
        preferences.edit().putLong(key, value).apply();
    }

    public Long getPreferenceLong(String key) {
        return preferences.getLong(key, 0L);
    }

    /**
     * For saving Boolean value to {preferences}
     *
     * @param key    : a constant used for saving
     * @param value: value to be saved
     */
    public void savePreferenceBoolean(String key, Boolean value) {
        preferences.edit().putBoolean(key, value).apply();
    }

    public Boolean getPreferenceBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    public void saveObject(String key, Object object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        savePreferenceString(key, json);
    }

    public <T> T getObject(String key, @NonNull Class<T> object) {
        Gson gson = new Gson();
        String json = getPreferenceString(key);
        return gson.fromJson(json, object);
    }

    /**
     * For showing with short message
     *
     * @param message: String value
     */
    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * For showing with long message
     *
     * @param message: String value
     */
    public void longToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    /**
     * @return an instance of {auth}
     * @getter for {auth}
     */
    public FirebaseAuth getAuth() {
        return auth;
    }

    /**
     * @return an instance of {usersReference}
     * @getter for {usersReference}
     */
    public DatabaseReference getUsersReference() {
        return usersReference;
    }

    /**
     * @return an instance of {databaseReference}
     * @getter for {databaseReference}
     */
    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    /**
     * @return an instance of {storageReference}
     * @getter for {storageReference}
     */
    public StorageReference getStorageReference() {
        return storageReference;
    }
}