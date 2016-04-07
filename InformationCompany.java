package com.example.lam.focastweathertravel;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Lam on 3/4/2016.
 */
public class InformationCompany extends Activity {
    ActionBar mAcBAr;
    ImageView imgBook;
    TextView tvinfo1;
    String info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_information_company);
        imgBook =(ImageView)findViewById(R.id.imgBook);
        tvinfo1 =(TextView)findViewById(R.id.tvinfo1);

        info =  "- Tên người hướng dẫn: "+ "\n"
                + "       Ths Ninh Thị Thanh Tâm"+ "\n" +
                "- Tên các thành viên trong nhóm: "+"\n"
                + "       Nguyễn Tuấn Lâm"+"\n"
                + "       Nguyễn Đăng Đức"+"\n"
                + "       Trần Trung Đức "+"\n"
                + "       Trịnh Thị Thoa"+"\n"
                + "       Bùi Vũ Hưng"+"\n\n"+
                  "Source code by LamNguyen";
        tvinfo1.setText(info);


        mAcBAr = getActionBar();
        mAcBAr.setHomeButtonEnabled(true);
        mAcBAr.setDisplayHomeAsUpEnabled(true);
        mAcBAr.setIcon(R.drawable.information);
        mAcBAr.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#CDAF95")));
        mAcBAr.setTitle(Html.fromHtml("<font color='#000000'> Thông tin nhóm </font>"));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
