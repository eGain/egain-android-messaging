package com.egain.ps.egainmessagingsample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.egain.ps.sdk.internal.config.AppConstants;
import com.egain.ps.sdk.internal.data.models.eGainButton;
import com.egain.ps.sdk.internal.data.ui.brandingUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        brandingUtil.setTheme(1);

        eGainButton launchCustomer = new eGainButton(
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