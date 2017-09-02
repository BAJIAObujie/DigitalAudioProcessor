package com.example.digitalaudioprocess;

import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.puredata.android.io.PdAudio;
import org.puredata.android.utils.PdUiDispatcher;
import org.puredata.core.PdBase;
import org.puredata.core.PdListener;
import org.puredata.core.utils.IoUtils;
import org.puredata.core.utils.PdDispatcher;

import java.io.File;
import java.io.IOException;

/**
 * Created by 惠中 on 2017/6/19.
 */
public class SingleAudio extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private Button btnbackmain,btnplay,btnsetdefault;

    private EditText etFirstPeak,etFirstAttack,etFirstDecrease,etFirstSustain,etFirstRelease,
                      etSecondPeak,etSecondAttack,etSecondDecrease,etSecondSustain,etSecondRelease,
                      etThirdPeak,etThirdAttack,etThirdDecrease,etThirdSustain,etThirdRelease;

    private SeekBar sbFreq,sbVolumn,sbFirstMultiplier,sbSecondMultiplier,sbThirdMultiplier;

    private TextView tvFreq,tvVolumn,tvPitch,tvFirstMultiplier,tvSecondMultiplier,tvThirdMultiplier;
    private Button btntest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleaudio);

        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        etFirstPeak = (EditText) findViewById(R.id.etFirstPeak) ;
        etFirstAttack = (EditText) findViewById(R.id.etFirstAttack) ;
        etFirstDecrease = (EditText) findViewById(R.id.etFirstDecrease);
        etFirstSustain = (EditText) findViewById(R.id.etFirstSustain);
        etFirstRelease = (EditText) findViewById(R.id.etFirstRelease);
        etSecondPeak = (EditText) findViewById(R.id.etSecondPeak);
        etSecondAttack = (EditText) findViewById(R.id.etSecondAttack);
        etSecondDecrease = (EditText) findViewById(R.id.etSecondDecrease);
        etSecondSustain = (EditText) findViewById(R.id.etSecondSustain);
        etSecondRelease = (EditText) findViewById(R.id.etSecondRelease);
        etThirdPeak = (EditText) findViewById(R.id.etThirdPeak);
        etThirdAttack = (EditText) findViewById(R.id.etThirdAttack);
        etThirdDecrease = (EditText) findViewById(R.id.etThirdDecrease);
        etThirdSustain = (EditText) findViewById(R.id.etThirdSustain);
        etThirdRelease = (EditText) findViewById(R.id.etThirdRelease);

        tvFreq = (TextView) findViewById(R.id.tvFreq) ;
        tvVolumn = (TextView) findViewById(R.id.tvVolumn) ;
        tvPitch = (TextView) findViewById(R.id.tvPitch) ;
        tvFirstMultiplier = (TextView) findViewById(R.id.tvFirstMultiplier) ;
        tvSecondMultiplier = (TextView) findViewById(R.id.tvSecondMultiplier) ;
        tvThirdMultiplier = (TextView) findViewById(R.id.tvThirdMultiplier) ;

        sbFreq = (SeekBar) findViewById(R.id.sbFreq);
        sbVolumn = (SeekBar) findViewById(R.id.sbVolumn);
        sbFirstMultiplier = (SeekBar) findViewById(R.id.sbFirstMultiplier);
        sbSecondMultiplier = (SeekBar) findViewById(R.id.sbSecondMultiplier);
        sbThirdMultiplier = (SeekBar) findViewById(R.id.sbThirdMultiplier);

        btnplay = (Button) findViewById(R.id.btnPlay);
        btnsetdefault = (Button) findViewById(R.id.btnSetDefault);
        btntest = (Button) findViewById(R.id.test);
        btnbackmain = (Button) findViewById(R.id.btnback);

        try {
            initPd();
        } catch (IOException e) {
            e.printStackTrace();
        }

        sbFreq.setOnSeekBarChangeListener(this);
        sbVolumn.setOnSeekBarChangeListener(this);
        sbFirstMultiplier.setOnSeekBarChangeListener(this);
        sbSecondMultiplier.setOnSeekBarChangeListener(this);
        sbThirdMultiplier.setOnSeekBarChangeListener(this);


        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.e("onCheckedChanged: ","play开始吧") ;
                //Toast.makeText(SingleAudio.this, etThirdRelease.getText().toString(), Toast.LENGTH_SHORT).show();
                sendsignal();
                PdBase.sendBang("bang");
            }
        });

        btnsetdefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etFirstPeak.setText("0.4");
                etFirstAttack.setText("0");
                etFirstDecrease.setText("1500");
                etFirstSustain.setText("0");
                etFirstRelease.setText("0");
                sbFirstMultiplier.setProgress(4);

                etSecondPeak.setText("0.8");
                etSecondAttack.setText("0");
                etSecondDecrease.setText("600");
                etSecondSustain.setText("0");
                etSecondRelease.setText("0");
                sbSecondMultiplier.setProgress(9);

                etThirdPeak.setText("0.5");
                etThirdAttack.setText("0");
                etThirdDecrease.setText("600");
                etThirdSustain.setText("0");
                etThirdRelease.setText("0");
                sbThirdMultiplier.setProgress(10);

                sbFreq.setProgress(1378);
                sbVolumn.setProgress(50);
            }
        });

        btnbackmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingleAudio.this,MainActivity.class);
                startActivity(intent);
            }
        });

        btntest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PdBase.sendBang("test");
                Toast.makeText(SingleAudio.this, "开启测试正弦波函数", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
        switch (seekBar.getId()){
            case R.id.sbFreq:
                PdBase.sendFloat("primaryfrequence",progress);
                tvFreq.setText(Integer.toString(progress));
                break;
            case R.id.sbVolumn:
                float vol = progress * 0.01f;
                vol += 0.5f;
                PdBase.sendFloat("CurrentVolumn",vol);
                tvVolumn.setText(String.valueOf(vol*100)+" %");
                break;
            case R.id.sbFirstMultiplier:
                float fir =progress * 0.25f;
                PdBase.sendFloat("Multiplier1",fir);
                tvFirstMultiplier.setText(String.valueOf(fir*100)+" %");
                break;
            case R.id.sbSecondMultiplier:
                float sec =progress * 0.25f;
                PdBase.sendFloat("Multiplier2",sec);
                tvSecondMultiplier.setText(String.valueOf(sec*100)+" %");
                break;
            case R.id.sbThirdMultiplier:
                float thi =progress * 0.25f;
                PdBase.sendFloat("Multiplier3",thi);
                tvThirdMultiplier.setText(String.valueOf(thi*100+" %"));
                break;
        }
    }
    public void onStartTrackingTouch(SeekBar seekBar){

    }
    public void onStopTrackingTouch(SeekBar seekBar){

    }


    private void sendsignal(){
        PdBase.sendFloat("Peak1",Float.valueOf(etFirstPeak.getText().toString()));
        PdBase.sendFloat("Attack1",Float.valueOf(etFirstAttack.getText().toString()));
        PdBase.sendFloat("Decrease1",Float.valueOf(etFirstDecrease.getText().toString()));
        PdBase.sendFloat("Sustain1",Float.valueOf(etFirstSustain.getText().toString()));
        PdBase.sendFloat("Release1",Float.valueOf(etFirstRelease.getText().toString()));

        PdBase.sendFloat("Peak2",Float.valueOf(etSecondPeak.getText().toString()));
        PdBase.sendFloat("Attack2",Float.valueOf(etSecondAttack.getText().toString()));
        PdBase.sendFloat("Decrease2",Float.valueOf(etSecondDecrease.getText().toString()));
        PdBase.sendFloat("Sustain2",Float.valueOf(etSecondSustain.getText().toString()));
        PdBase.sendFloat("Release2",Float.valueOf(etSecondRelease.getText().toString()));

        PdBase.sendFloat("Peak3",Float.valueOf(etThirdPeak.getText().toString()));
        PdBase.sendFloat("Attack3",Float.valueOf(etThirdAttack.getText().toString()));
        PdBase.sendFloat("Decrease3",Float.valueOf(etThirdDecrease.getText().toString()));
        PdBase.sendFloat("Sustain3",Float.valueOf(etThirdSustain.getText().toString()));
        PdBase.sendFloat("Release3",Float.valueOf(etThirdRelease.getText().toString()));
        Log.e("onCheckedChanged: ",etThirdRelease.getText().toString()) ;
        Toast.makeText(this, etThirdRelease.getText().toString(), Toast.LENGTH_SHORT).show();
    }


    private void initPd() throws IOException {
        int sampleRate = 44100;
        int outChans = 2;
        int ticks = 16;
        PdBase.openAudio(0, outChans, (int) sampleRate);
        PdBase.computeAudio(true);
        File dir = getFilesDir();
        //提取压缩包注释
        File patchFile = new File(dir, "yueyin.pd");
        PdBase.openPatch(patchFile);
        PdAudio.initAudio(sampleRate, 0, outChans, ticks, true);
        PdDispatcher dispatcher = new PdUiDispatcher();

        dispatcher.addListener("apppitch", new PdListener.Adapter(){
            @Override
            public void receiveFloat(String source, float x) {
                super.receiveFloat(source, x);
                tvPitch.setText(Float.toString(x));  //返回频率
            }
        });
        tvPitch.setText("0 %");
        tvFreq.setText("0 ");

        PdBase.setReceiver(dispatcher);
        PdAudio.startAudio(SingleAudio.this);
        Log.e("onCheckedChanged: ","DSP开启" );
        PdBase.sendBang("trigger1");
        PdBase.sendBang("trigger2");
        PdBase.sendBang("trigger3");
        //Toast.makeText(this, "触发器开启", Toast.LENGTH_SHORT).show();
        //Log.e("onCheckedChanged: ","触发器开启" );
    }















}
