package com.example.fyp.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.google.gson.Gson;

@SuppressLint("InflateParams")
public class BaseFragment extends Fragment {
    private ProgressDialog progressDialog;
    private SharedPreferences preferences; //instance of SharedPreferences
    private FirebaseAuth auth; //instance of FirebaseAuth
    private CollectionReference usersReference; // instance of Firebase Firestore CollectionReference;
    private DatabaseReference databaseReference; //instance of Firebase database reference
    private StorageReference storageReference; // instance of Firebase Storage reference

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(requireContext());
        usersReference = FirebaseFirestore.getInstance().collection(Constants.users);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        preferences = requireActivity().getSharedPreferences(Constants.prefName, Context.MODE_PRIVATE);
    }


    /**
     * A function for showing progress dialog
     *
     * @param message: String value
     */
    public void showProgressDialog(String message) {
        progressDialog.show(message);
    }

    /**
     * A function for changing message on an already shown Progress dialog
     *
     * @param message: String value
     */
    public void setDialogMessage(String message) {
        progressDialog.setMessage(message);
    }

    /**
     * For hiding progress dialog
     */
    public void hideProgressDialog() {
        progressDialog.dismiss();
    }

    public void alert(String title, String positiveText, DialogInterface.OnClickListener positiveListener, String negativeText, DialogInterface.OnClickListener negativeListener) {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(title)
                .setPositiveButton(positiveText, positiveListener)
                .setNegativeButton(negativeText, negativeListener)
                .show();
    }

    public void alert(String title, String message, String positiveText, DialogInterface.OnClickListener positiveButton, String negativeText, DialogInterface.OnClickListener negativeListener) {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveText, positiveButton)
                .setNegativeButton(negativeText, negativeListener)
                .show();
    }
    public <T> void goTo(Class<T> intent) {
        startActivity(new Intent(requireActivity().getApplicationContext(), intent));
    }

    public <T> void goTo(Class<T> intent, Boolean finish) {
        if (finish) {
            startActivity(new Intent(requireActivity().getApplicationContext(), intent));
            requireActivity().finish();
        } else {
            startActivity(new Intent(requireActivity().getApplicationContext(), intent));
        }
    }

    public <T> void goToWithFinishAffinity(Class<T> intent) {
        startActivity(new Intent(requireActivity().getApplicationContext(), intent));
        requireActivity().finishAffinity();
    }


    public void goBack() {
        requireActivity().onBackPressed();
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
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * For showing with long message
     *
     * @param message: String value
     */
    public void longToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
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
    public CollectionReference getUsersReference() {
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
     * @return an instance of { storageReference}
     * @getter for {storageReference}
     */
    public StorageReference getStorageReference() {
        return storageReference;
    }
}
