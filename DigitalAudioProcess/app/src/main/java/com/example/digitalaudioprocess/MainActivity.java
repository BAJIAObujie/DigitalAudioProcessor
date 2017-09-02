package com.example.digitalaudioprocess;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import org.puredata.core.utils.IoUtils;
import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    private Button ActivityPiano,ActivitySingleAudio,ActivityNoiseModifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(this.getResources().getConfiguration().orientation==
                Configuration. ORIENTATION_PORTRAIT ) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }


        ActivityPiano = (Button) findViewById(R.id.PianoPlay);
        ActivitySingleAudio = (Button) findViewById(R.id.SingleAudio);
        ActivityNoiseModifier = (Button) findViewById(R.id.NoiseModifier);

        ActivityPiano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PianoToMain = new Intent(MainActivity.this,Piano.class);
                startActivity(PianoToMain);
            }
        });
        ActivitySingleAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SingleAudioToMain = new Intent(MainActivity.this,SingleAudio.class);
                startActivity(SingleAudioToMain);
            }
        });
        ActivityNoiseModifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NoiseModifierToMain = new Intent(MainActivity.this,NoiseModifier.class);
                startActivity(NoiseModifierToMain);
            }
        });

        try {
            initPd();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initPd() throws IOException {
        File dir = getFilesDir();
        IoUtils.extractZipResource(getResources().openRawResource(R.raw.digitalaudioprocessor), dir, true);
    }
}
