package io.aerobase.push.demo;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;

import org.aerogear.mobile.core.Callback;
import org.aerogear.mobile.core.MobileCore;
import org.aerogear.mobile.core.executor.AppExecutors;
import org.aerogear.mobile.push.MessageHandler;
import org.aerogear.mobile.push.PushService;

import java.util.Map;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PushService pushService = MobileCore.getInstance().getService(PushService.class);
        pushService.registerDevice(new Callback() {
            @Override
            public void onSuccess() {
                MobileCore.getLogger().info("AB", "Successfully registered device to aerobase server");
            }

            @Override
            public void onError(Throwable error) {
                new AppExecutors().mainThread().execute(() -> {
                    MobileCore.getLogger().error("AB", error.getMessage(), error);
                });
            }
        });
    }
}
