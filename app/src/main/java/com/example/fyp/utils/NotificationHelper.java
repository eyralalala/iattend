package com.example.fyp.utils;

import android.util.Log;

import com.example.fyp.api.FirebaseNotificationClient;
import com.example.fyp.api.FirebaseNotificationHelper;
import com.example.fyp.data.notification.PushNotification;

import org.jetbrains.annotations.NotNull;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationHelper {
    FirebaseNotificationClient notificationClient;
    private static final String TAG = "NotificationHelper";

    public NotificationHelper() {
        notificationClient = new FirebaseNotificationHelper().retrofit().create(FirebaseNotificationClient.class);
    }

    public void sendNotification(PushNotification notification) {
        try {
            Call<ResponseBody> responseCall = notificationClient.sendNotification(notification);
            responseCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Log.d(TAG, "onResponse: Notification sent");
                        assert response.body() != null;
                        Log.d(TAG, "onResponse: "+response.body());
                    }else{
                        Log.d(TAG, "onResponse: Failure sent");
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                    Log.d(TAG, "sendNotification: Unable to send notification" + t.getLocalizedMessage());
                }
            });
        } catch (Exception exception) {
            Log.d(TAG, "sendNotification: " + exception);
        }
    }
}
