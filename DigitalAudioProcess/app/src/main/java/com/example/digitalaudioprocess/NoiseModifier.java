package com.example.digitalaudioprocess;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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
public class NoiseModifier extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener  {


    private Button btnbackmain,btnADSRDefault,btnLFODefault;

    private ToggleButton tbADSR,tbLFO;

    private SeekBar sbLowPass,sbHighPass,sbBandPassFreq,sbBandPassBandWidth,sbLowPassRange,sbLowPassDepth,sbVolumn;

    private EditText etPeak,etAttack,etDecrease,etSustain,etRelease;

    private TextView tvLowPass,tvHighPass,tvBandPassFreq,tvBandPassBandWidth,tvLowPassRange,tvLowPassDepth,tvVolumn;

    private CheckBox cbLowPass,cbHighPass,cbBandPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noisemodifier);

        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btnbackmain = (Button) findViewById(R.id.btnback);
        btnADSRDefault = (Button) findViewById(R.id.btnADSRDefault);
        btnLFODefault = (Button) findViewById(R.id.btnLFODefault);

        tvLowPass = (TextView)findViewById(R.id.tvLowPass);
        tvHighPass = (TextView)findViewById(R.id.tvHighPass);
        tvBandPassFreq = (TextView)findViewById(R.id.tvBandPassFreq);
        tvBandPassBandWidth = (TextView)findViewById(R.id.tvBandPassBandWidth);
        tvLowPassRange = (TextView)findViewById(R.id.tvLowPassRange);
        tvLowPassDepth = (TextView)findViewById(R.id.tvLowPassDepth);
        tvVolumn = (TextView)findViewById(R.id.tvVolumn);

        etPeak = (EditText)findViewById(R.id.etPeak);
        etAttack = (EditText)findViewById(R.id.etAttack);
        etDecrease = (EditText)findViewById(R.id.etDecrease);
        etSustain = (EditText)findViewById(R.id.etSustain);
        etRelease = (EditText)findViewById(R.id.etRelease);

        sbLowPass = (SeekBar)findViewById(R.id.sbLowPass) ;
        sbHighPass = (SeekBar)findViewById(R.id.sbHighPass) ;
        sbBandPassFreq = (SeekBar)findViewById(R.id.sbBandPassFreq) ;
        sbBandPassBandWidth = (SeekBar)findViewById(R.id.sbBandPassBandWidth ) ;
        sbLowPassRange = (SeekBar)findViewById(R.id.sbLowPassRange) ;
        sbLowPassDepth = (SeekBar)findViewById(R.id.sbLowPassDepth) ;
        sbVolumn = (SeekBar)findViewById(R.id.sbVolumn) ;

        tbADSR = (ToggleButton)findViewById(R.id.tbADSR);
        tbLFO = (ToggleButton)findViewById(R.id.tbLFO);

        cbLowPass = (CheckBox)findViewById(R.id.cbLowPass);
        cbHighPass = (CheckBox)findViewById(R.id.cbHighPass);
        cbBandPass = (CheckBox)findViewById(R.id.cbBandPass);

        try {
            initPd();
        } catch (IOException e) {
            e.printStackTrace();
        }

        cbLowPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
                if(isChecked){
                    PdBase.sendBang("openlop");
                    Log.e("onCheckedChanged: ","打开低通滤波器" );
                }
                else{
                    PdBase.sendBang("openlop");
                    Log.e("onCheckedChanged: ","关闭低通滤波器" );
                }
            }
        });
        cbHighPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
                if(isChecked){
                    PdBase.sendBang("openhip");
                    Log.e("onCheckedChanged: ","打开高通滤波器" );
                }
                else{
                    PdBase.sendBang("openhip");
                    Log.e("onCheckedChanged: ","关闭高通滤波器" );
                }
            }
        });
        cbBandPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
                if(isChecked){
                    PdBase.sendBang("openbp");
                    Log.e("onCheckedChanged: ","打开带通滤波器" );
                }
                else{
                    PdBase.sendBang("openbp");
                    Log.e("onCheckedChanged: ","关闭带通滤波器" );
                }
            }
        });

        tbADSR.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sendsignaladsr();
                    PdBase.sendBang("adsr");
                    Log.e("onCheckedChanged: ","打开ADSR" );
                }
                else{
                    PdBase.sendBang("adsr");
                    Log.e("onCheckedChanged: ","关闭ADSR" );
                }
            }
        });
        tbLFO.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    PdBase.sendBang("lfo");
                    Log.e("onCheckedChanged: ","打开LFO" );
                }
                else{
                    PdBase.sendBang("lfo");
                    Log.e("onCheckedChanged: ","关闭LFO" );
                }
            }
        });


        sbLowPass.setOnSeekBarChangeListener(this);
        sbHighPass.setOnSeekBarChangeListener(this);
        sbBandPassFreq.setOnSeekBarChangeListener(this);
        sbBandPassBandWidth.setOnSeekBarChangeListener(this);
        sbLowPassRange.setOnSeekBarChangeListener(this);
        sbLowPassDepth.setOnSeekBarChangeListener(this);
        sbVolumn.setOnSeekBarChangeListener(this);





        btnbackmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoiseModifier.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btnADSRDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPeak.setText(String.valueOf(1));
                etAttack.setText(String.valueOf(3000));
                etDecrease.setText(String.valueOf(10000));
                etSustain.setText(String.valueOf(30));
                etRelease.setText(String.valueOf(5000));
                sbLowPass.setProgress(300);
                sbVolumn.setProgress(50);
                //--------------开启ADSR，调整成海浪声的预设值，为避免其他参数干扰，设为0，并且关闭---------
                sbHighPass.setProgress(0);
                sbBandPassFreq.setProgress(0);
                sbBandPassBandWidth.setProgress(0);
                sbLowPassRange.setProgress(0);
                sbLowPassDepth.setProgress(0);
                //--------------开启ADSR，调整成海浪声的预设值，为避免其他参数干扰，设为0，并且关闭---------
                Toast.makeText(NoiseModifier.this, "请打开低通滤波器，并且关闭高通、带通滤波器", Toast.LENGTH_LONG).show();
                Log.e("默认设置: ","低通滤波器300 海浪声" );
            }
        });
        btnLFODefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sbLowPassRange.setProgress(1);
                sbLowPassDepth.setProgress(60);
                sbBandPassFreq.setProgress(400);
                sbBandPassBandWidth.setProgress(1);
                sbVolumn.setProgress(50);
                //--------------开启LFO低频振荡器，调整成风声的预设值，为避免其他参数干扰，设为0，并且关闭---------
                sbLowPass.setProgress(0);
                sbHighPass.setProgress(0);
                etPeak.setText(String.valueOf(0));
                etAttack.setText(String.valueOf(0));
                etDecrease.setText(String.valueOf(0));
                etSustain.setText(String.valueOf(0));
                etRelease.setText(String.valueOf(0));
                //--------------开启LFO低频振荡器，调整成风声的预设值，为避免其他参数干扰，设为0，并且关闭---------
                Toast.makeText(NoiseModifier.this, "请打开带通滤波器，并且关闭低通、高通滤波器", Toast.LENGTH_LONG).show();
                Log.e("默认设置: ","低频振荡器400 Q=1 风声" );
            }
        });

    }


    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
        switch (seekBar.getId()){
            case R.id.sbLowPass:
                tvLowPass.setText("频率 "+progress+" 以下将被保留");
                PdBase.sendFloat("lop",progress);
                break;
            case R.id.sbHighPass:
                tvHighPass.setText("频率 "+progress+" 以上将被保留");
                PdBase.sendFloat("hip",progress);
                break;
            case R.id.sbBandPassFreq:
                tvBandPassFreq.setText(String.valueOf(progress));
                PdBase.sendFloat("bpcenterfreq",progress);
                break;
            case R.id.sbBandPassBandWidth:
                tvBandPassBandWidth.setText(String.valueOf(progress));
                PdBase.sendFloat("bpq",progress);
                break;
            case R.id.sbLowPassRange:
                float tr = progress * 0.1f;
                tvLowPassRange.setText(String.valueOf(tr));
                PdBase.sendFloat("lfofreq",tr);
                break;

            case R.id.sbLowPassDepth:
                tvLowPassDepth.setText(String.valueOf(progress));
                PdBase.sendFloat("lfodepth",progress);
                break;

            case R.id.sbVolumn:
                float vol = progress * 0.01f;
                vol += 0.5f;
                PdBase.sendFloat("volumn",vol);
                tvVolumn.setText(String.valueOf(vol*100)+" %");
                break;

        }
    }
    public void onStartTrackingTouch(SeekBar seekBar){

    }
    public void onStopTrackingTouch(SeekBar seekBar){

    }



    private void initPd() throws IOException {
        int sampleRate = 44100;
        int outChans = 2;
        int ticks = 16;
        PdBase.openAudio(0, outChans, (int) sampleRate);
        PdBase.computeAudio(true);
        File dir = getFilesDir();
        //提取压缩包注释
        File patchFile = new File(dir, "noise.pd");
        PdBase.openPatch(patchFile);
        PdAudio.initAudio(sampleRate, 0, outChans, ticks, true);
        PdDispatcher dispatcher = new PdUiDispatcher();

        tvLowPassRange.setText("0 ");
        tvLowPassDepth.setText("0 ");

        PdBase.setReceiver(dispatcher);
        PdAudio.startAudio(NoiseModifier.this);

        //Toast.makeText(this, "触发器开启", Toast.LENGTH_SHORT).show();
        //Log.e("onCheckedChanged: ","触发器开启" );
    }

    private void sendsignaladsr(){
        PdBase.sendFloat("Peak",Float.valueOf(etPeak.getText().toString()));
        PdBase.sendFloat("Attack",Float.valueOf(etAttack.getText().toString()));
        PdBase.sendFloat("Decrease",Float.valueOf(etDecrease.getText().toString()));
        PdBase.sendFloat("Sustain",Float.valueOf(etSustain.getText().toString()));
        PdBase.sendFloat("Release",Float.valueOf(etRelease.getText().toString()));
        Log.e("onCheckedChanged: ","发送ADSR信号完毕" );

    }


}
