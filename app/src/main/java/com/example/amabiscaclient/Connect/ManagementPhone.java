package com.example.amabiscaclient.Connect;


import android.content.Context;

import java.util.ArrayList;

public class ManagementPhone {

    private Context context;

    public ManagementPhone(Context context) {
        this.context = context;
    }

    public void clickNumber(ClickButtonListener clickButtonListener) {
        clickButtonListener.Click();
    }


}
