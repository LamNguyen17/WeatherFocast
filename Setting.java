package com.example.lam.focastweathertravel;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Lam on 3/14/2016.
 */
public class Setting extends Activity {
    ActionBar mAcBAr;
    public static final String MyPREFERENCES = "MyPrefs" ;
    Button btnOk;
    public Boolean EnSound= true;
    public Boolean firstPlayerX = true;
    RadioButton radEnable,radDisable,radX,radO ;
    TextView tvSound;
    Intent intentMusicBg;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    RadioGroup radGroupSym,radGroupSou;

    //Xử lý Sharepreference truyền static method (truền trong Touch Event)
    public static Boolean getFirstPlayerX(Context context) {
        return context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).getBoolean("Select X", true);
    }

    // Lưu trạng thái của activity
    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Lưu giá trị lựa chọn người chơi 1 chọn X hay O
        outState.putBoolean("Select X", firstPlayerX);
        System.out.println("onSaveState = " + firstPlayerX);

        outState.putBoolean("Select Sound", EnSound);
        System.out.println("onSaveState = " + EnSound);

        super.onSaveInstanceState(outState);
    }

    // Được gọi khi tái hoạt động Activity sau khi kill OS
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        firstPlayerX=savedInstanceState.getBoolean("Select X");
        System.out.println("onRestoreInstanceState ="+ firstPlayerX);

        EnSound = savedInstanceState.getBoolean("Select Sound");
        System.out.println("onRestoreInstanceState =" + EnSound);

        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        // Trạng thái trống để giá trị mặc định
        if(savedInstanceState == null){
            firstPlayerX = true;
        } else{
            firstPlayerX = savedInstanceState.getBoolean("Select X", true);
        }
        // file SOund
        if (savedInstanceState == null) {
            EnSound = true;
        } else {
            EnSound = savedInstanceState.getBoolean("Select Disable Sound", true);
        }

        intentMusicBg = new Intent(Setting.this, BackgroundSoundService.class);

        mAcBAr = getActionBar();
        mAcBAr.setIcon(R.drawable.settingss);
        mAcBAr.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#CDAF95")));
        mAcBAr.setTitle(Html.fromHtml("<font color='#000000'> Cài đặt </font>"));
        mAcBAr.setHomeButtonEnabled(true);
        mAcBAr.setDisplayHomeAsUpEnabled(true);
        SearchId();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(), "Đã lưu cài đặt", Toast.LENGTH_SHORT).show();
            }
        });
        radGroupSym.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radX) {
                    firstPlayerX = true;
                } else if (checkedId == R.id.radO) {
                    firstPlayerX = false;
                }
                System.out.println("X= " + firstPlayerX);
            }
        });
        radGroupSou.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radEnable) {
                    EnSound = true;
                    startService(intentMusicBg);
                }
                if (checkedId == R.id.radDisable) {
                    EnSound = false;
                    stopService(intentMusicBg);
                }
            }
        });
    }

    // Trạng thái đã lưu được lấy lên khi activity bắt đầu hoạt động
    // Gọi đến hàm đọc trạng thái
    @Override
    protected void onResume() {
        super.onResume();
        firstPlayerX= getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).getBoolean("Select X", true);
        EnSound = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).getBoolean("Select Sound",true);

        // chọn radioGroup theo lựa chọn đã lưu
        if(firstPlayerX){
            radGroupSym.check(R.id.radX);
        } else {
            radGroupSym.check(R.id.radO);
        }
        // chọn radioGroup theo lựa chọn đã lưu
        if(EnSound){
            radGroupSou.check(R.id.radEnable);
        } else {
            radGroupSou.check(R.id.radDisable);
        }
    }

    ///////*/////////
    // Gọi đến hàm lưu trạng thái
    @Override
    protected void onPause() {
        super.onPause();
        getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE ).edit().putBoolean("Select X", firstPlayerX).commit();
        getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE).edit().putBoolean("Select Sound",EnSound ).commit();// chấp nhận lưu

    }
    ////////*//////////

    public void Save(){
        // Write share preference
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        firstPlayerX = radX.isChecked();
        editor.putBoolean("Select X", firstPlayerX);
        editor.putBoolean("Enable Sound", EnSound);
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mute, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId()){
            case R.id.music:
                Toast.makeText(getBaseContext(), "Tắt nhạc nền", Toast.LENGTH_SHORT).show();
                intentMusicBg=new Intent(Setting.this, BackgroundSoundService.class);
                stopService(intentMusicBg);
                break;
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void SearchId() {
        radGroupSym = (RadioGroup)findViewById(R.id.radGroupSym);
        radGroupSou = (RadioGroup)findViewById(R.id.radGroupSou);
        radEnable = (RadioButton)findViewById(R.id.radEnable);
        radDisable = (RadioButton)findViewById(R.id.radDisable);
        radX =(RadioButton)findViewById(R.id.radX);
        radO = (RadioButton)findViewById(R.id.radO);
        btnOk = (Button)findViewById(R.id.btnOk);
        tvSound = (TextView)findViewById(R.id.tvSound);
    }
    // Source code by LamNguyen
}
