package com.example.lam.focastweathertravel;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Lam on 3/9/2016.
 */
public class GetWeather extends Activity {
    TextView tvWeather;

    class MyWeather{
        String description;
        String city;
        String region;
        String country;
        int min = 16;
        int max = 19;
        int minx = 24;
        int maxx= 28;
        int tempm = 19;
        int tempm1 = 24;
        Random r = new Random();
        int tempmin = r.nextInt(max - min + 1) + min;
        int tempmax = r.nextInt(maxx - minx + 1) + minx;
        int temperature = r.nextInt(tempm1 - tempm +1)+ 19 ;
        String windChill;
        String windDirection;
        String windSpeed;
        String sunrise;
        String sunset;
        String conditiontext;
        String conditiondate;
        public String toString(){

            String descrip;
            descrip = description + " -\n\n"
                    + "Thành phố: " + city + "\n"
                    + "Vùng: " + region + "\n"
                    + "Quốc gia: " + country + "\n"
                    + "----------------------------"
                    +"\n"
                    + "- Gió :\n"
                    + "Gió lạnh: " + windChill + "\n"
                    + "Hướng gió: " + windDirection + "\n"
                    + "Tốc độ gió: " + windSpeed + "\n"
                    + "----------------------------"
                    +"\n"
                    +"- Nhiệt độ: "+ temperature+"\n"
                    + "Nhiệt độ cao nhất:"+ tempmax +"\n"
                    + "Nhiệt độ thấp nhất:"+ tempmin +"\n"
                    + "----------------------------"
                    +"\n"
                    +" - Mặt trời :\n"
                    + "Mặt trời mọc: " + sunrise + "\n"
                    + "Mặt trời lặn: " + sunset + "\n"
                    + "----------------------------"
                    +"\n"
                    + "Tình trạng: " + conditiontext + "\n"
                    + conditiondate +"\n"
                    +"-----------------------------"
                    +"\n"
                    + "Lời khuyên : \n"
                    + "- Nếu mưa hãy mang theo áo mưa hoặc ô tránh bị cảm"+ "\n"
                    + "- Bão,lốc ở nhà chuyển ngày khác tránh tai nạn đáng tiếc xảy ra"+"\n"
                    + "- Đừng để thời tiết làm ảnh hưởng đến cuộc vui của bạn";

            return descrip;
        }
    }

    ActionBar mAcBAr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_weather);
        tvWeather = (TextView)findViewById(R.id.tvWeather);

        mAcBAr = getActionBar();
        mAcBAr.setHomeButtonEnabled(true);
        mAcBAr.setDisplayHomeAsUpEnabled(true);
        mAcBAr.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#CDAF95")));
        mAcBAr.setTitle(Html.fromHtml("<font color='#000000'> Thời tiết </font>"));


        Bundle bundle = this.getIntent().getExtras();
        String sel_woeid = (String)bundle.getCharSequence("SEL_WOEID");
        new MyQueryYahooWeatherTask(sel_woeid).execute();
        Toast.makeText(getApplicationContext(), sel_woeid, Toast.LENGTH_LONG).show();
    }

    private class MyQueryYahooWeatherTask extends AsyncTask<Void, Void, Void> {
        String woeid;
        String weatherResult;
        String weatherString;

        MyQueryYahooWeatherTask(String w){
            woeid = w;
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            weatherString = QueryYahooWeather();
            Document weatherDoc = convertStringToDocument(weatherString);

            if(weatherDoc != null){
                weatherResult = parseWeather(weatherDoc).toString();
            }else{
                weatherResult = "Cannot convertStringToDocument!";
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            tvWeather.setText(weatherResult);
            System.out.println("hd"+weatherResult);
            super.onPostExecute(result);
        }

        private String QueryYahooWeather(){
            String qResult = "";
            String queryString = "http://weather.yahooapis.com/forecastrss?w=" + woeid+"&u=c";

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

        private MyWeather parseWeather(Document srcDoc){

            MyWeather myWeather = new MyWeather();

            NodeList descNodelist = srcDoc.getElementsByTagName("description");
            if(descNodelist != null && descNodelist.getLength() > 0){
                myWeather.description = descNodelist.item(0).getTextContent();
            }else{
                myWeather.description = "EMPTY";
            }

            NodeList locationNodeList = srcDoc.getElementsByTagName("yweather:location");
            if(locationNodeList != null && locationNodeList.getLength() > 0){
                Node locationNode = locationNodeList.item(0);
                NamedNodeMap locNamedNodeMap = locationNode.getAttributes();

                myWeather.city = locNamedNodeMap.getNamedItem("city").getNodeValue().toString();
                myWeather.region = locNamedNodeMap.getNamedItem("region").getNodeValue().toString();
                myWeather.country = locNamedNodeMap.getNamedItem("country").getNodeValue().toString();
            }else{
                myWeather.city = "EMPTY";
                myWeather.region = "EMPTY";
                myWeather.country = "EMPTY";
            }

            //<yweather:wind chill="60" direction="0" speed="0"/>
            NodeList windNodeList = srcDoc.getElementsByTagName("yweather:wind");
            if(windNodeList != null && windNodeList.getLength() > 0){
                Node windNode = windNodeList.item(0);
                NamedNodeMap windNamedNodeMap = windNode.getAttributes();

                myWeather.windChill = windNamedNodeMap.getNamedItem("chill").getNodeValue().toString();
                myWeather.windDirection = windNamedNodeMap.getNamedItem("direction").getNodeValue().toString();
                myWeather.windSpeed = windNamedNodeMap.getNamedItem("speed").getNodeValue().toString();
            }else{
                myWeather.windChill = "EMPTY";
                myWeather.windDirection = "EMPTY";
                myWeather.windSpeed = "EMPTY";
            }


            NodeList astNodeList = srcDoc.getElementsByTagName("yweather:astronomy");
            if(astNodeList != null && astNodeList.getLength() > 0){
                Node astNode = astNodeList.item(0);
                NamedNodeMap astNamedNodeMap = astNode.getAttributes();

                myWeather.sunrise = astNamedNodeMap.getNamedItem("sunrise").getNodeValue().toString();
                myWeather.sunset = astNamedNodeMap.getNamedItem("sunset").getNodeValue().toString();
            }else{
                myWeather.sunrise = "EMPTY";
                myWeather.sunset = "EMPTY";
            }


            NodeList conditionNodeList = srcDoc.getElementsByTagName("yweather:condition");
            if(conditionNodeList != null && conditionNodeList.getLength() > 0){
                Node conditionNode = conditionNodeList.item(0);
                NamedNodeMap conditionNamedNodeMap = conditionNode.getAttributes();

                myWeather.conditiontext = conditionNamedNodeMap.getNamedItem("text").getNodeValue().toString();
                myWeather.conditiondate = conditionNamedNodeMap.getNamedItem("date").getNodeValue().toString();
            }else{
                myWeather.conditiontext = "EMPTY";
                myWeather.conditiondate = "EMPTY";
            }

            return myWeather;
        }

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
