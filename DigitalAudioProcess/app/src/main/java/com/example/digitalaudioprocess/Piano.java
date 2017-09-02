package com.example.digitalaudioprocess;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import org.puredata.android.io.PdAudio;
import org.puredata.android.utils.PdUiDispatcher;
import org.puredata.core.PdBase;
import org.puredata.core.PdListener;
import org.puredata.core.utils.PdDispatcher;

import java.io.File;
import java.io.IOException;

/**
 * Created by 惠中 on 2017/6/19.
 */
public class Piano extends AppCompatActivity implements Button.OnTouchListener {


    private ImageButton btnupC,btnupD,btnupF,btnupG,btnupA,
                         btnC,btnD,btnE,btnF,btnG,btnA,btnB;

    //private ImageButton btntest;
    private Button btnbackmain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piano);

        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        btnupC = (ImageButton)findViewById(R.id.btnupC) ;
        btnupD = (ImageButton)findViewById(R.id.btnupD) ;
        btnupF = (ImageButton)findViewById(R.id.btnupF) ;
        btnupG = (ImageButton)findViewById(R.id.btnupG) ;
        btnupA = (ImageButton)findViewById(R.id.btnupA) ;
        btnC = (ImageButton)findViewById(R.id.btnC) ;
        btnD = (ImageButton)findViewById(R.id.btnD) ;
        btnE = (ImageButton)findViewById(R.id.btnE) ;
        btnF = (ImageButton)findViewById(R.id.btnF) ;
        btnG = (ImageButton)findViewById(R.id.btnG) ;
        btnA = (ImageButton)findViewById(R.id.btnA) ;
        btnB = (ImageButton)findViewById(R.id.btnB) ;
        //btntest = (ImageButton)findViewById(R.id.imageButton) ;

        try {
            initPd();
        } catch (IOException e) {
            e.printStackTrace();
        }

        btnupC.setOnTouchListener(this);
        btnupD.setOnTouchListener(this);
        btnupF.setOnTouchListener(this);
        btnupG.setOnTouchListener(this);
        btnupA.setOnTouchListener(this);
        btnC.setOnTouchListener(this);
        btnD.setOnTouchListener(this);
        btnE.setOnTouchListener(this);
        btnF.setOnTouchListener(this);
        btnG.setOnTouchListener(this);
        btnA.setOnTouchListener(this);
        btnB.setOnTouchListener(this);

        btnbackmain = (Button) findViewById(R.id.btnback);
    }

    public void btnback(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onTouch(View v, MotionEvent event){
        switch (v.getId()){
            case R.id.btnupC:
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    PdBase.sendBang("btnupC");
                }
                if (event.getAction() == MotionEvent.ACTION_UP){
                    PdBase.sendBang("btnupendC");
                }
            case R.id.btnupD:
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    PdBase.sendBang("btnupD");
                }
                if (event.getAction() == MotionEvent.ACTION_UP){
                    PdBase.sendBang("btnupendD");
                }
            case R.id.btnupF:
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    PdBase.sendBang("btnupF");
                }
                if (event.getAction() == MotionEvent.ACTION_UP){
                    PdBase.sendBang("btnupendF");
                }
            case R.id.btnupG:
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    PdBase.sendBang("btnupG");
                }
                if (event.getAction() == MotionEvent.ACTION_UP){
                    PdBase.sendBang("btnupendG");
                }
            case R.id.btnupA:
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    PdBase.sendBang("btnupA");
                }
                if (event.getAction() == MotionEvent.ACTION_UP){
                    PdBase.sendBang("btnupendA");
                }
            //------------------------上排按键-----------------------------------
            //------------------------下排按键-----------------------------------
            case R.id.btnC:
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    PdBase.sendBang("btnC");
                }
                if (event.getAction() == MotionEvent.ACTION_UP){
                    PdBase.sendBang("btnendC");
                }
            case R.id.btnD:
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    PdBase.sendBang("btnD");
                }
                if (event.getAction() == MotionEvent.ACTION_UP){
                    PdBase.sendBang("btnendD");
                }
            case R.id.btnE:
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    PdBase.sendBang("btnE");
                }
                if (event.getAction() == MotionEvent.ACTION_UP){
                    PdBase.sendBang("btnendE");
                }
            case R.id.btnF:
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    PdBase.sendBang("btnF");
                }
                if (event.getAction() == MotionEvent.ACTION_UP){
                    PdBase.sendBang("btnendF");
                }
            case R.id.btnG:
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    PdBase.sendBang("btnG");
                }
                if (event.getAction() == MotionEvent.ACTION_UP){
                    PdBase.sendBang("btnendG");
                }
            case R.id.btnA:
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    PdBase.sendBang("btnA");
                }
                if (event.getAction() == MotionEvent.ACTION_UP){
                    PdBase.sendBang("btnendA");
                }
            case R.id.btnB:
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    PdBase.sendBang("btnB");
                }
                if (event.getAction() == MotionEvent.ACTION_UP){
                    PdBase.sendBang("btnendB");
                }
        }
        return true;
    }

    private void initPd() throws IOException {
        int sampleRate = 44100;
        int outChans = 2;
        int ticks = 16;
        PdBase.openAudio(0, outChans, (int) sampleRate);
        PdBase.computeAudio(true);
        File dir = getFilesDir();
        //提取压缩包注释
        File patchFile = new File(dir, "pianoplay.pd");
        PdBase.openPatch(patchFile);
        PdAudio.initAudio(sampleRate, 0, outChans, ticks, true);
        PdDispatcher dispatcher = new PdUiDispatcher();

        PdBase.setReceiver(dispatcher);
        PdAudio.startAudio(Piano.this);
        Log.e("onCheckedChanged: ","DSP开启" );

        //Toast.makeText(this, "触发器开启", Toast.LENGTH_SHORT).show();
        //Log.e("onCheckedChanged: ","触发器开启" );
    }
}

