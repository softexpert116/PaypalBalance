package com.clevery.android.paypalbalance;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.clevery.android.paypalbalance.Service.PingService;
import com.clevery.android.paypalbalance.httpModule.RequestBuilder;
import com.clevery.android.paypalbalance.httpModule.ResponseElement;
import com.clevery.android.paypalbalance.httpModule.RunanbleCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.clevery.android.paypalbalance.App.arrayList;

public class MainActivity extends AppCompatActivity {
//    public static String serverUrl = "http://192.168.0.107/paypal/index.php";
    AccountListAdapter accountListAdapter;
//    ArrayList<AccountModel> arrayList = new ArrayList<>();
    ListView listView;
    int sel_index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView);

        final Button btn_start = (Button)findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.isRunningMyService(PingService.class)) {
                    App.stopMyService(PingService.class);
                    btn_start.setText("Start Widget");
                    btn_start.setBackgroundColor(getColor(R.color.colorPrimaryDark));
                } else {
                    App.startMyService(PingService.class);
                    btn_start.setText("Stop Widget");
                    btn_start.setBackgroundColor(getColor(R.color.colorAccent));
                }
            }
        });
        if (!App.isRunningMyService(PingService.class)) {
            btn_start.setText("Start Widget");
            btn_start.setBackgroundColor(getColor(R.color.colorPrimaryDark));
        } else {
            btn_start.setText("Stop Widget");
            btn_start.setBackgroundColor(getColor(R.color.colorAccent));
        }
        Button btn_add = (Button)findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddDialog();
            }
        });
        accountListAdapter = new AccountListAdapter(this, arrayList);
        listView.setAdapter(accountListAdapter);
    }
    public void refreshAccounts() {
//        arrayList = App.readPreference_account_array();
//        if (arrayList == null) {
//            arrayList = new ArrayList<>();
//            App.setPreference_account_array(arrayList);
//        }
//        accountListAdapter = new AccountListAdapter(this, arrayList);

//        listView.setAdapter(accountListAdapter);
        accountListAdapter.notifyDataSetChanged();
    }
    private void openAddDialog() {
        final Dialog dialog = new Dialog(this);
        Window window = dialog.getWindow();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        final View view = getLayoutInflater().inflate(R.layout.dialog_add_account, null);
        dialog.setContentView(view);
        window.setGravity(Gravity.CENTER);
        final EditText edit_username = (EditText) view.findViewById(R.id.edit_user_name);
        final EditText edit_password = (EditText) view.findViewById(R.id.edit_password);
        final EditText edit_signature = (EditText) view.findViewById(R.id.edit_signature);
        Button btn_add = (Button)view.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edit_username.getText().toString().trim();
                String password = edit_password.getText().toString().trim();
                String signature = edit_signature.getText().toString().trim();
                if (username.length() * password.length() * signature.length() == 0) {
                    Utils.showAlert(MainActivity.this, "Warning", "Please fill in all fields.");
                    return;
                }
                for(AccountModel model: arrayList) {
                    if (model.username.equals(username)) {
                        Utils.showAlert(MainActivity.this, "Warning", "Username already exists.");
                        return;
                    }
                }
                AccountModel model = new AccountModel(username, password, signature, "", "", "");
                arrayList.add(model);
                App.setPreference_account_array(arrayList);
                accountListAdapter = new AccountListAdapter(MainActivity.this, arrayList);
                listView.setAdapter(accountListAdapter);
                dialog.dismiss();
                App.notifyWidgetUpdate();
            }
        });
        dialog.show();
        dialog.getWindow().setLayout((int)(width*0.9f), ViewGroup.LayoutParams.WRAP_CONTENT);
    }
    protected void displayResultText(String result, boolean isSuccess) {
        TextView txt_result = (TextView)findViewById(R.id.txtResult);
        txt_result.setText("Result : " + result);
        if (isSuccess) {
            txt_result.setTextColor(getColor(R.color.colorPrimary));
        } else {
            txt_result.setTextColor(getColor(R.color.colorAccent));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
