package com.clevery.android.paypalbalance;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

class ListProvider implements RemoteViewsService.RemoteViewsFactory {
//    private ArrayList<AccountModel> listItemList = new ArrayList();
    private Context context = null;
    private int appWidgetId;

    public ListProvider(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

    }

    @Override
    public void onCreate() {

    }
    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {
//        end_timer();
    }

    @Override
    public int getCount() {
        return App.arrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    /*
     *Similar to getView of Adapter where instead of View
     *we return RemoteViews
     *
     */
    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.cell_widget);
        AccountModel account = App.arrayList.get(position);
        remoteView.setTextViewText(R.id.txt_username, account.username);
        remoteView.setTextViewText(R.id.txt_balance, account.balance);

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }
}
