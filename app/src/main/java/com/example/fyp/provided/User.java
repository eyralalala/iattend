package com.example.fyp.provided;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public @Data
class User {
    private String fullName, age, email,icNumber,phoneNumber,userType;
}
