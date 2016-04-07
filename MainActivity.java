package com.example.lam.focastweathertravel;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
    ActionBar mAcBAr;
    TextView tvDesigner;
    Button btnThongTin,btnSetting,btnBaMien,btnWidget;
    Intent intentThongtin, intentLocation,intentWidget,
           intentMusicBg,intentSetting;
    RelativeLayout relativeBk;
    int images[] = {R.drawable.muathu, R.drawable.hoadao, R.drawable.muadong, R.drawable.muaha};

    @Override
    protected void onResume() {
        if(relativeBk != null)
            relativeBk.setBackgroundResource(images[getRandomNumber()]);
        super.onResume();
    }

    //    protected void onResume()
//    {
//        if(relativeBk != null)
//            relativeBk.setBackgroundResource(images[getRandomNumber()]);
//
//    }

    private int getRandomNumber() {
        return new Random().nextInt(4);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Method gọi đến id
        Searchid();

        relativeBk = (RelativeLayout) findViewById(R.id.relativeBk);

        mAcBAr = getActionBar();
        mAcBAr.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#CDAF95")));
        mAcBAr.setTitle(Html.fromHtml("<font color='#000000'> Dự báo thời tiết du lịch </font>"));
        tvDesigner.setText(Html.fromHtml("<font color='#000000'> by </font> <font color='#FF9900'> Lam Nguyen </font>"));

        // Gán file âm thanh vào Main
        intentMusicBg=new Intent(MainActivity.this, BackgroundSoundService.class);
        startService(intentMusicBg);
        System.out.println("Truyền file âm thanh");

        btnWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentWidget = new Intent(MainActivity.this,Widget.class);
                startActivity(intentWidget);
            }
        });
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentSetting = new Intent(MainActivity.this,Setting.class);
                startActivity(intentSetting);
            }
        });
        btnBaMien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3mien = new Intent(MainActivity.this, Information3Mien.class);
                startActivity(intent3mien);
            }
        });
        btnThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentThongtin = new Intent(MainActivity.this, InformationCompany.class);
                startActivity(intentThongtin);
            }
        });

    }

    private void Searchid() {
        btnWidget = (Button)findViewById(R.id.btnWidget);
        tvDesigner = (TextView)findViewById(R.id.tvDesigner);
        btnSetting = (Button)findViewById(R.id.btnSetting);
        btnBaMien = (Button)findViewById(R.id.btnBaMien);
        btnThongTin = (Button) findViewById(R.id.btnThongTin);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.music:
                Toast.makeText(getBaseContext(), "Tắt nhạc nền", Toast.LENGTH_SHORT).show();
                intentMusicBg=new Intent(MainActivity.this, BackgroundSoundService.class);
                stopService(intentMusicBg);
                break;

            case R.id.location:
                Toast.makeText(getBaseContext(), "Vị trí hiện tại", Toast.LENGTH_LONG).show();
                intentLocation = new Intent(MainActivity.this, Location.class);
                startActivity(intentLocation);
                break;
            case R.id.share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = " ";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Chia sẻ qua"));
                break;
            case android.R.id.home:
                this.finish();
                return true;
//            default:
//                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
