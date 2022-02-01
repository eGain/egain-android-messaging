package com.example.eg_sample_messaging;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.egain.ps.sdk.internal.data.models.eGainButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        eGainButton launchGuest = new eGainButton(this,
                "e9d5d640ce074dd1b79dff4d88a89a83",
                "hIz55YTxfXLzE6LicxRa0ba56PIaMiPQfE0hhaxdlkh7@qtirCV7-WwAK9fb",
                true);
    }
}