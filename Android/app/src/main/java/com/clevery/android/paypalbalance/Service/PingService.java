package com.clevery.android.paypalbalance.Service;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

import com.clevery.android.paypalbalance.AccountModel;
import com.clevery.android.paypalbalance.App;
import com.clevery.android.paypalbalance.httpModule.RequestBuilder;
import com.clevery.android.paypalbalance.httpModule.ResponseElement;
import com.clevery.android.paypalbalance.httpModule.RunanbleCallback;

import java.util.Timer;
import java.util.TimerTask;

public class PingService extends Service {
    private static Timer timer = new Timer();
    private Context ctx;
    final Handler handler = new Handler();
    final int ping_time = 10000;
    int sel_index = 0;

    public IBinder onBind(Intent arg0)
    {
        return null;
    }

    public void onCreate()
    {
        super.onCreate();
        ctx = this;
        startService();
    }

    private void startService()
    {
        timer = new Timer();
        timer.scheduleAtFixedRate(new mainTask(), 0, ping_time);
    }

    private class mainTask extends TimerTask
    {
        public void run()
        {
            if (App.arrayList.size() > 0) {
                AccountModel model = App.arrayList.get(sel_index);
                balanceRequest(model);
                sel_index ++;
                if (sel_index >= App.arrayList.size()) {
                    sel_index = 0;
                }
            }
        }
    }

    public void onDestroy()
    {
        timer.cancel();
        super.onDestroy();
    }

    private void balanceRequest(AccountModel model) {
        RequestBuilder requestBuilder = new RequestBuilder(App.serverUrl);
        requestBuilder
                .addParam("type", "GetBalance")
                .addParam("username", model.username)
                .addParam("signature", model.signature)
                .addParam("password", model.password)
                .sendRequest(callback);
    }

    RunanbleCallback callback = new RunanbleCallback() {
        @Override
        public void finish(ResponseElement element) {
            int code = element.getStatusCode();
            switch (code) {
                case 200:
                    String balance = element.getData("balance");
                    String username = element.getData("username");
                    String currency = element.getData("currency");
                    String date = element.getData("date");
                    for (AccountModel model:App.arrayList) {
                        if (model.username.equals(username)) {
                            model.balance = balance; model.currency = currency; model.date = date;
                            break;
                        }
                    }
//                    App.setPreference_account_array(App.arrayList);
                    break;
                case 400:
                case 500:
                    if (sel_index == 0) {
                        AccountModel model = App.arrayList.get(App.arrayList.size()-1);
                        model.balance = "Error!";
                    } else {
                        for (int i = 0; i < App.arrayList.size(); i++) {
                            AccountModel model = App.arrayList.get(i);
                            if (i == sel_index-1) {
                                model.balance = "Error!";
                                break;
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
            App.notifyWidgetUpdate();
        }

    };
}
