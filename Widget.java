package com.example.lam.focastweathertravel;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Lam on 3/17/2016.
 */
public class Widget extends Activity {
    Intent intentHumVsCom;
    ActionBar mAcBAr;
    Button btnCaro,btnHoangDao,btnTruyenCuoi,btnSinhHoc,btnDovui,btnLaBan;
    Intent intentMusicBg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget);
        Searchid();

        mAcBAr = getActionBar();
        mAcBAr.setHomeButtonEnabled(true);
        mAcBAr.setDisplayHomeAsUpEnabled(true);
        mAcBAr.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#CDAF95")));
        mAcBAr.setTitle(Html.fromHtml("<font color='#000000'> Tiện ích </font>"));
        btnCaro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentHumVsCom = new Intent(Widget.this, Obj_HumVsCom.class);
                System.out.println("Truyền tab từ Widget sang Obj_PlayComputer");
                intentHumVsCom.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentHumVsCom);
            }
        });
        btnHoangDao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // custom dialog
                final Dialog dialog = new Dialog(getApplicationContext());
                dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                dialog.setContentView(R.layout.custom_hoang_dao_widget);
                dialog.setTitle(Html.fromHtml("<font color='#FFE4B5'> Cung hoàng đạo </font>"));
                ImageView image = (ImageView) dialog.findViewById(R.id.imgHoangDao);
                image.setImageResource(R.drawable.connect);

                final Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

                //////////////////////////////////////
            }
        });
        btnDovui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // custom dialog
                final Dialog dialog = new Dialog(getApplicationContext());
                dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                dialog.setContentView(R.layout.custom_hoang_dao_widget);
                dialog.setTitle(Html.fromHtml("<font color='#FFE4B5'> Đố vui </font>"));
                ImageView image = (ImageView) dialog.findViewById(R.id.imgHoangDao);
                image.setImageResource(R.drawable.connect);

                final Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });
        btnSinhHoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // custom dialog
                final Dialog dialog = new Dialog(getApplicationContext());
                dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                dialog.setContentView(R.layout.custom_hoang_dao_widget);
                dialog.setTitle(Html.fromHtml("<font color='#FFE4B5'> Nhịp tim </font>"));
                ImageView image = (ImageView) dialog.findViewById(R.id.imgHoangDao);
                image.setImageResource(R.drawable.connect);

                final Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });
        btnTruyenCuoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // custom dialog
                final Dialog dialog = new Dialog(getApplicationContext());
                dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                dialog.setContentView(R.layout.custom_hoang_dao_widget);
                dialog.setTitle(Html.fromHtml("<font color='#FFE4B5'> Truyện cười </font>"));
                ImageView image = (ImageView) dialog.findViewById(R.id.imgHoangDao);
                image.setImageResource(R.drawable.connect);

                final Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        btnLaBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // custom dialog
                final Dialog dialog = new Dialog(getApplicationContext());
                dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                dialog.setContentView(R.layout.custom_hoang_dao_widget);
                dialog.setTitle(Html.fromHtml("<font color='#FFE4B5'> La bàn </font>"));
                ImageView image = (ImageView) dialog.findViewById(R.id.imgHoangDao);
                image.setImageResource(R.drawable.connect);

                final Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });


    }
    private void Searchid() {
        btnCaro = (Button)findViewById(R.id.btnCaro);
        btnHoangDao=(Button)findViewById(R.id.btnHoangDao);
        btnDovui=(Button)findViewById(R.id.btnDoVui);
        btnSinhHoc =(Button)findViewById(R.id.btnSinhHoc);
        btnTruyenCuoi =(Button)findViewById(R.id.btnTruyenCuoi);
        btnLaBan =(Button)findViewById(R.id.btnLaBan);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mute, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.music:
                Toast.makeText(getBaseContext(), "Tắt nhạc nền", Toast.LENGTH_SHORT).show();
                intentMusicBg=new Intent(Widget.this, BackgroundSoundService.class);
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
}
