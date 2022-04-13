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
                "1fbaa4959ceb4eb59018f5d686f9e8de",
                "6bOSTWALgUSfEMDRGKmXO8@WAWwbV0pW-XJCYn3t-05OF75mOwiBCKYpsEwR",
                "mobile_sdk_qa",
                "mobile_sdk_address_with_egain_bot_qa",
                "beta-qa@email.com",
                "beta-qa",
                "Hi"
        );
    }
}