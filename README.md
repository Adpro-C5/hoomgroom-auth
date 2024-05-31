# HoomGroom Authentication and Profile

## Code Review
[![SonarCloud](https://sonarcloud.io/images/project_badges/sonarcloud-white.svg)](https://sonarcloud.io/summary/new_code?id=Adpro-C5_hoomgroom-auth)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Adpro-C5_hoomgroom-auth&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Adpro-C5_hoomgroom-auth)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Adpro-C5_hoomgroom-auth&metric=bugs)](https://sonarcloud.io/summary/new_code?id=Adpro-C5_hoomgroom-auth)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=Adpro-C5_hoomgroom-auth&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=Adpro-C5_hoomgroom-auth)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=Adpro-C5_hoomgroom-auth&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=Adpro-C5_hoomgroom-auth)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Adpro-C5_hoomgroom-auth&metric=coverage)](https://sonarcloud.io/summary/new_code?id=Adpro-C5_hoomgroom-auth)

## Monitoring
Monitoring dapat dilakukan dengan mengakses [Grafana](http://34.143.217.107:3000/d/adnahihjdxnuoc/dashboard-auth?orgId=1&refresh=30s).

## Authentication Feature

### Register
`POST /auth/register`
```json
{
    "fullName": "Muhammad Hilal Darul Fauzan",
    "birthDate": "2004-04-27",
    "gender": "Laki-laki",
    "username": "hiladfzn",
    "email": "hilalfauzan9@gmail.com",
    "address": "Menteng, Jakarta Pusat",
    "password": "hilal2704",
    "role": "BUYER"
}

```
`RESULT 200 /auth/register`
```json
{
    "token": null,
    "message": "User registered successfully"
}
```

`RESULT 400 /auth/register`
```json
{
    "token": null,
    "message": "Username is already taken"
}
```

```json
{
    "token": null,
    "message": "Password must be at least 8 characters"
}
```

### Login
`POST /auth/login`
```json
{
    "username": "hiladfzn",
    "password": "hilal2704"
}
```

`RESULT 200 /auth/login`
```json
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJoaWxhZGZ6biIsImlhdCI6MTcxNzE0MDE4NywiZXhwIjoxNzE3MjI2NTg3fQ.ArcPWWbAkKZ1NnM6Nd0kuxVTRc5cQt0DBlbP79Ryv39ERvXARjjiubnH4ngBUJzkg3CXSwSPvHBYz2o8b1iXPw",
    "message": "User authenticated successfully"
}
```

`RESULT 401 /auth/login`
```json
```

`RESULT 400 /auth/login`
```json
{
    "token": null,
    "message": "User not found"
}
```

### Logout (Bearer token in header)
`POST /auth/userLogout`
```json
```

`RESULT 200 /auth/userLogout`
```json
{
    "token": null,
    "message": "User logged out successfully"
}
```

`RESULT 400 /auth/userLogout`
```json
{
    "token": null,
    "message": "User not found"
}
```

`RESULT 401 /auth/userLogout`
```json
```

## Profile Feature (Bearer token in header)

### Get Profile
`GET /profile`
```json
```

`RESULT 200 /profile`
```json
{
    "message": "User profile retrieved successfully",
    "id": 11,
    "fullName": "Muhammad Hilal Darul Fauzan",
    "birthDate": [
        2004,
        4,
        27
    ],
    "gender": "Laki-laki",
    "username": "hiladfzn",
    "email": "hilalfauzan9@gmail.com",
    "address": "Menteng, Jakarta Pusat",
    "balance": 0
}
```

`RESULT 400 /profile`
```json
{
    "message": "Invalid token",
    "id": null,
    "fullName": null,
    "birthDate": null,
    "gender": null,
    "username": null,
    "email": null,
    "address": null,
    "balance": null
}
```

`RESULT 401 /profile`
```json
```

### Update Address
`PUT /profile/address`
```json
{
    "newAddress": "Menteng, Jakarta Pusat, DKI Jakarta"
}
```

`RESULT 200 /profile/address`
```json
{
    "message": "Address updated successfully",
    "id": null,
    "fullName": null,
    "birthDate": null,
    "gender": null,
    "username": null,
    "email": null,
    "address": null,
    "balance": null
}
```

`RESULT 400 /profile/address`
```json
{
    "message": "Invalid token",
    "fullName": null,
    "birthDate": null,
    "gender": null,
    "username": null,
    "email": null,
    "address": null,
    "balance": null
}
```

`RESULT 401 /profile/address`
```json
```

### Update Password
`PUT /profile/password`
```json
{
    "oldPassword": "hilal2704",
    "newPassword": "testupdate1"
}
```

`RESULT 200 /profile/password`
```json
{
    "message": "Password updated successfully",
    "id": null,
    "fullName": null,
    "birthDate": null,
    "gender": null,
    "username": null,
    "email": null,
    "address": null,
    "balance": null
}
```

`RESULT 400 /profile/password`
```json
{
    "message": "Invalid token",
    "id": null,
    "fullName": null,
    "birthDate": null,
    "gender": null,
    "username": null,
    "email": null,
    "address": null,
    "balance": null
}
```

```json
{
    "message": "Old password is incorrect",
    "id": null,
    "fullName": null,
    "birthDate": null,
    "gender": null,
    "username": null,
    "email": null,
    "address": null,
    "balance": null
}
```

`RESULT 401 /profile/password`
```json
```

### Delete Account
`DELETE /profile/delete`
```json
```

`RESULT 200 /profile/delete`
```json
{
    "message": "User profile deleted successfully",
    "id": null,
    "fullName": null,
    "birthDate": null,
    "gender": null,
    "username": null,
    "email": null,
    "address": null,
    "balance": null
}
```

`RESULT 400 /profile/delete`
```json
{
    "message": "Invalid token",
    "id": null,
    "fullName": null,
    "birthDate": null,
    "gender": null,
    "username": null,
    "email": null,
    "address": null,
    "balance": null
}
```

`RESULT 401 /profile/delete`
```json
```

## Code Diagram Authentication
![image](https://github.com/Adpro-C5/hoomgroom-auth/assets/121853767/c3f629aa-8704-41c4-8fd7-4a2ea6f34bdb)