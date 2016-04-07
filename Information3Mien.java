package com.example.lam.focastweathertravel;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Lam on 3/15/2016.
 */
public class Information3Mien extends Activity {
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    ArrayList<String> selectedItems = new ArrayList<>();

    final String yahooPlaceApisBase = "http://query.yahooapis.com/v1/public/yql?q=select*from%20geo.places%20where%20text=";
    final String yahooapisFormat = "&format=xml";
    String yahooPlaceAPIsQuery;
    final String[] mBac_list= {"Bình Phước","Bình Dương","Đồng Nai","Tây Ninh","Bà Rịa Vũng Tàu","TpHCM","Long An","Đồng Tháp"
        ,"Tiền Giang","An Giang","Bến Tre","Vĩng Long","Trà Vinh","Hậu Giang","Kiên Giang","Sóc Trăng","Bạc Liêu"
        ,"Cà Mau","Tp Cần Thơ","Lào Cai","Yên Bái","Điện Biên","Hoà Bình","Lai Châu","Sơn La",
        "Hà Giang","Cao Bằng","Bắc Kạn","Lạng Sơn","Tuyên Quang","Thái Nguyên","Phú Thọ","Bắc Giang","Quảng Ninh",
        "Bắc Ninh","Hà Nam","Hà Nội","Hải Dương","Hải Phòng","Hưng Yên","Nam Định","Ninh Bình","Thái Bình","Vĩnh Phúc",
        "Thanh Hoá","Nghệ An","Hà Tĩnh","Quảng Bình","Quảng Trị","Thừa Thiên-Huế",
        "Đà Nẵng","Quảng Nam","Quảng Ngãi","Bình Định","Phú Yên","Khánh Hoà","Ninh Thuận","Bình Thuận",
        "Kon Tum","Gia Lai","Đắc Lắc","Đắc Nông","Lâm Đồng"};
    final String[] mList_Vn={"Bình Phước","Bình Dương","Đồng Nai","Tây Ninh","Bà Rịa Vũng Tàu","TpHCM","Long An","Đồng Tháp"
            ,"Tiền Giang","An Giang","Bến Tre","Vĩng Long","Trà Vinh","Hậu Giang","Kiên Giang","Sóc Trăng","Bạc Liêu"
            ,"Cà Mau","Tp Cần Thơ","Lào Cai","Yên Bái","Điện Biên","Hoà Bình","Lai Châu","Sơn La",
            "Hà Giang","Cao Bằng","Bắc Kạn","Lạng Sơn","Tuyên Quang","Thái Nguyên","Phú Thọ","Bắc Giang","Quảng Ninh",
            "Bắc Ninh","Hà Nam","Hà Nội","Hải Dương","Hải Phòng", "Hưng Yên", "Nam Định","Ninh Bình","Thái Bình","Vĩnh Phúc",
        "Thanh Hoá","Nghệ An","Hà Tĩnh","Quảng Bình","Quảng Trị","Thừa Thiên-Huế",
            "Đà Nẵng","Quảng Nam", "Quảng Ngãi","Bình Định","Phú Yên","Khánh Hoà", "Ninh Thuận", "Bình Thuận",
        "Kon Tum","Gia Lai","Đắc Lắc", "Đắc Nông", "Lâm Đồng"};

    ListView lvMienBac_addfavourite,lvWoeidListSearch;
    AutoCompleteTextView autotvPlace;
    Button btnSearch,btnAddMbac,btnRemoveMbac;
    Intent intentMusicBg;
    ActionBar mAcBAr;
    ArrayAdapter<String> adapter,adapter3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_tab_3_mien);

        loadTab();
        Searchid();
        mAcBAr = getActionBar();
        mAcBAr.setHomeButtonEnabled(true);
        mAcBAr.setDisplayHomeAsUpEnabled(true);
        mAcBAr.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#CDAF95")));
        mAcBAr.setTitle(Html.fromHtml("<font color='#000000'> Thông tin 3 miền </font>"));

        adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_multiple_choice, mBac_list);
        adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mList_Vn);

        lvMienBac_addfavourite.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lvMienBac_addfavourite.setAdapter(adapter);
        Arrays.sort(mBac_list);

        // Bước 1 : Khởi tạo SharePreference
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if(sharedpreferences.contains(MyPREFERENCES)) {
            // Thực thi việc Load lựa chọn multicheck
            LoadSelections();
            SaveSelections();
        }


        autotvPlace.setAdapter(adapter3);
        autotvPlace.setThreshold(1);
        autotvPlace.setTextColor(Color.RED);

        ///////////////////////////////////////////////////////////////////////
        /////////////////////////Danh sách 3 miền//////////////////////////////
        btnAddMbac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selected = "";
                ArrayList<String> selectedItems = new ArrayList<>();
                int selectChoice = lvMienBac_addfavourite.getCount();
                SparseBooleanArray checked = lvMienBac_addfavourite.getCheckedItemPositions();
                for (int i = 0; i < selectChoice; i++) {
                    // Trả về giá trị các key được check --> SaveFavourite
                    int position = checked.keyAt(i);
                    if (checked.valueAt(i)) {
                        selectedItems.add(adapter.getItem(position));
                    }
                    if (checked.get(i)) {
                        selected += lvMienBac_addfavourite.getItemAtPosition(i).toString() + "\n";
                    }
                }
//                 Xuất ra xâu giá trị được chọn chuyển đến SaveFourite
                String[] outputStrArr = new String[selectedItems.size()];
                for (int i = 0; i < selectedItems.size(); i++) {
                    outputStrArr[i] = selectedItems.get(i);
                    SaveSelections();
                }
                Toast.makeText(Information3Mien.this, selected, Toast.LENGTH_LONG).show();
            }

        });
        btnRemoveMbac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearSelections();
            }
        });

        ///////////////////////////////////////////////////////////////////////
        ///////////////////////////Tìm kiếm////////////////////////////////////

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(autotvPlace.getText().toString().equals(" ")){
                    Toast.makeText(getBaseContext(),
                            "Bạn hãy nhập địa chỉ!",
                            Toast.LENGTH_LONG).show();
                    System.out.println("Import me plz");
                }else{
                    new MyQueryYahooPlaceTask().execute();
                    System.out.println("Touch me plz");
                    //SaveSelections();
                }
            }
        });
    }
    private void ClearSelections() {
        int count = this.lvMienBac_addfavourite.getAdapter().getCount();
        for (int i = 0; i < count; i++) {
            this.lvMienBac_addfavourite.setItemChecked(i, false);
        }
        SaveSelections();
    }

    // Việc này thực hiện sau khi check các item cà ấn Button
    private void SaveSelections() {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        String savedItems = getSavedItems();
        editor.putString(MyPREFERENCES.toString(), savedItems);
        System.out.println("4");
        // Chấp nhận lưu file xuống
        editor.commit();
    }

    /////////////////////SaveItems////////////////////////
    //////////////////////////////////////////////////////
    public String getSavedItems() {
        String savedItems = " ";
        int count = this.lvMienBac_addfavourite.getAdapter().getCount();
        for (int i = 0; i < count; i++) {
            if (this.lvMienBac_addfavourite.isItemChecked(i)) {
                if (savedItems.length() > 0) {
                    savedItems += "," + this.lvMienBac_addfavourite.getItemAtPosition(i);
                }else {
                    savedItems += this.lvMienBac_addfavourite.getItemAtPosition(i);
                }
            }
        }
        return savedItems;
    }
    /////////////////////LoadSelection////////////////////////
    /////////////////////////////////////////////////////////
    private void LoadSelections() {
        if (sharedpreferences.contains(MyPREFERENCES.toString())) {
            String savedItems = sharedpreferences.getString(MyPREFERENCES.toString(), "");
            selectedItems.addAll(Arrays.asList(savedItems.split(",")));
            int count = this.lvMienBac_addfavourite.getAdapter().getCount();
            System.out.println("6");
            for (int i = 0; i < count; i++) {
                String currentItem = (String) lvMienBac_addfavourite.getAdapter().getItem(i);
                if (selectedItems.contains(currentItem)) {
                    lvMienBac_addfavourite.setItemChecked(i, true);
                    SaveSelections();
                } else {
                    lvMienBac_addfavourite.setItemChecked(i, false);
                    SaveSelections();
                }
            }
        }
    }
//////////////////////////////////////
    private class MyQueryYahooPlaceTask extends AsyncTask<Void, Void, Void> {
        ArrayList<String> listquery;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Mảng danh sách chính là địa chỉ truy vấn từ Yahoo
            listquery = QueryYahooPlaceAPIs();
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
                Intent intent = new Intent(Information3Mien.this, GetWeather.class);
                Bundle bundle = new Bundle();
                bundle.putCharSequence("SEL_WOEID", listquery.get(0));
                intent.putExtras(bundle);
                startActivity(intent);
            super.onPostExecute(result);
        }
    }

    private ArrayList<String> QueryYahooPlaceAPIs(){
        // Nhận địa chỉ và mã hóa
        String uriPlace = Uri.encode(autotvPlace.getText().toString());
        System.out.println("Địa chỉ đã nhập là = "+ uriPlace);

        yahooPlaceAPIsQuery = yahooPlaceApisBase
                + "%22" + uriPlace + "%22"
                + yahooapisFormat;
        String woeidString = QueryYahooWeather(yahooPlaceAPIsQuery);
        System.out.println("Query yahoo place api = woiedString "+ woeidString);
        Document woeidDoc = convertStringToDocument(woeidString);
        System.out.println("Query yahoo place api = woiedDoc "+ woeidDoc);
        return  parseWOEID(woeidDoc);
    }

    private ArrayList<String> parseWOEID(Document srcDoc){
        ArrayList<String> listWOEID = new ArrayList<>();
        // Lấy tag woeid bên trong srcDoc
        NodeList nodeListDescription = srcDoc.getElementsByTagName("woeid");
        if(nodeListDescription.getLength()>=0){
            for(int i=0; i<nodeListDescription.getLength(); i++){
                listWOEID.add(nodeListDescription.item(i).getTextContent());
            }
        }else{
            listWOEID.clear();
        }

        return listWOEID;
    }

    private Document convertStringToDocument(String src){
        Document dest = null;

        DocumentBuilderFactory dbFactory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder parser;

        try {
            parser = dbFactory.newDocumentBuilder();
            dest = parser.parse(new ByteArrayInputStream(src.getBytes()));
        } catch (ParserConfigurationException e1) {
            e1.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dest;
    }

    private String QueryYahooWeather(String queryString){
        String qResult = "";

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(queryString);

        try {
            HttpEntity httpEntity = httpClient.execute(httpGet).getEntity();

            if (httpEntity != null){
                InputStream inputStream = httpEntity.getContent();
                Reader in = new InputStreamReader(inputStream);
                BufferedReader bufferedreader = new BufferedReader(in);
                StringBuilder stringBuilder = new StringBuilder();

                String stringReadLine = null;

                while ((stringReadLine = bufferedreader.readLine()) != null) {
                    stringBuilder.append(stringReadLine + "\n");
                }

                qResult = stringBuilder.toString();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return qResult;
    }




    private void Searchid() {
        btnRemoveMbac=(Button)findViewById(R.id.btnRemoveMbac);
        lvMienBac_addfavourite =(ListView)findViewById(R.id.lvMienBac_addfavourite);
        lvWoeidListSearch=(ListView)findViewById(R.id.lvWoeidListSearch);
        autotvPlace =(AutoCompleteTextView)findViewById(R.id.autotvPlace);
        btnSearch =(Button)findViewById(R.id.btnSearch);
        btnAddMbac =(Button)findViewById(R.id.btnAddMbac);
    }

    private void loadTab() {
        final TabHost tab=(TabHost) findViewById(android.R.id.tabhost);
        //gọi lệnh setup
        tab.setup();
        TabHost.TabSpec spec;

        //Tạo tab1
        spec=tab.newTabSpec("t1").setContent(R.id.tab1).setIndicator("Danh sách 3 miền");
        tab.addTab(spec);
        //Tạo tab2
        spec=tab.newTabSpec("t2").setContent(R.id.tab2).setIndicator("Tìm kiếm");
        tab.addTab(spec);
        //Thiết lập tab mặc định được chọn ban đầu là tab 0
        tab.setCurrentTab(0);
        tab.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String arg0) {
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.items, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.music:
                Toast.makeText(getBaseContext(), "Tắt nhạc nền", Toast.LENGTH_SHORT).show();
                intentMusicBg=new Intent(Information3Mien.this, BackgroundSoundService.class);
                stopService(intentMusicBg);
                break;
            case R.id.favourite:
                Toast.makeText(getBaseContext(), "Sở thích 3 miền", Toast.LENGTH_SHORT).show();
                String[] outputStrArr = new String[selectedItems.size()];
                for (int i = 0; i < selectedItems.size(); i++) {
                    outputStrArr[i] = selectedItems.get(i);
                    SaveSelections();
                }
                Intent intentFavourite = new Intent(getApplicationContext(), SaveFavourite.class);
                Bundle b = new Bundle();
                b.putStringArray("selectedItems", outputStrArr);
                intentFavourite.putExtras(b);
                startActivity(intentFavourite);
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
