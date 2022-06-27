package com.example.fyp.api;


import com.example.fyp.data.notification.PushNotification;
import com.example.fyp.utils.Constants;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FirebaseNotificationClient {

    @Headers({"Authorization: key=" + Constants.serverKey, "Content-Type:" + Constants.contentType})
    @POST("fcm/send")
    Call<ResponseBody> sendNotification(
            @Body() PushNotification notification
    );
}
