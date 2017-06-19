package com.MinimalSoft.JOIIN.Responses;

public class UserData {
    private int idUser;
    private String name;
    private String lastName;
    private String phone;
    private String birthday;
    // Todo: Verify this API field.
    private String idFacebook;

    public int getIdUser() {
        return idUser;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getBirthday() {
        return birthday;
    }
}