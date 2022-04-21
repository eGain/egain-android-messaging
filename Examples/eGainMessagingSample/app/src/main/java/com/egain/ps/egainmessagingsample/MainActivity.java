package com.egain.ps.egainmessagingsample;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        brandingUtil.setTheme(1);

        eGainMessaging launchCustomer_qa = new eGainMessaging(
                this,
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        );
    }
}