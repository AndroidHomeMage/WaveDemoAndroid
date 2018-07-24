package com.hivescm.tms.consignor.waveview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private WaveView wave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wave = findViewById(R.id.wave);
        wave.setOnClickListener(v->{
            wave.setWaveHeight((int) (wave.getWaveHeight()*1.1f));
        });
    }
}
