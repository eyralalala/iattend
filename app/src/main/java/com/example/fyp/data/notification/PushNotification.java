package com.example.fyp.data.notification;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public @Data
class PushNotification {
    NotificationData data;
    String to;
}
