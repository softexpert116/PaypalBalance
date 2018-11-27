package com.clevery.android.paypalbalance;

import java.io.Serializable;

/**
 * Created by Acer on 01/12/2016.
 */

public class AccountModel implements Serializable {
    public String username;
    public String password;
    public String signature;
    public String date;
    public String balance;
    public String currency;

    public AccountModel(String username, String password, String signature, String date, String balance, String currency)
    {
        this.username = username;
        this.password = password;
        this.signature = signature;
        this.balance = balance;
        this.currency = currency;
        this.date = date;
    }
    public AccountModel()
    {
        this.username = "";
        this.password = "";
        this.signature = "";
        this.balance = "";
        this.currency = "";
        this.date = "";
    }
}
