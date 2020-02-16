package com.example.khoslalabs;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androdocs.httprequest.HttpRequest;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ScrollingActivity extends AppCompatActivity {

    String ZIP = "560043,in";
    String API = "542f9208ad25dfe2a92b501b74631b58";
    TextView[] dayText, mTemp, eTemp, nTemp;
    TextView todayText, todaydate, todaytempTextn, todaytempTextm, todaytempTexte;
    CollapsingToolbarLayout toolbar;

    void IntFilds() {

        todayText = findViewById(R.id.today_text_day);
        todaydate = findViewById(R.id.today_text_date);
        // todaytempTexte=(TextView)findViewById(R.id.today_text_e);
        todaytempTextm = findViewById(R.id.today_text_m);
        //  todaytempTextn=(TextView)findViewById(R.id.today_text_n);

        dayText = new TextView[]{
                findViewById(R.id.day_1_text_day),
                findViewById(R.id.day_2_text_day),
                findViewById(R.id.day_3_text_day),
                findViewById(R.id.day_4_text_day),
                findViewById(R.id.day_5_text_day)
        };
        mTemp = new TextView[]{
                findViewById(R.id.day_1_text_m),
                findViewById(R.id.day_2_text_m),
                findViewById(R.id.day_3_text_m),
                findViewById(R.id.day_4_text_m),
                findViewById(R.id.day_5_text_m)};

        nTemp = new TextView[]{
                findViewById(R.id.day_1_text_n),
                findViewById(R.id.day_2_text_n),
                findViewById(R.id.day_3_text_n),
                findViewById(R.id.day_4_text_n),
                findViewById(R.id.day_5_text_n)};

        eTemp = new TextView[]{findViewById(R.id.day_1_text_e),
                findViewById(R.id.day_2_text_e),
                findViewById(R.id.day_3_text_e),
                findViewById(R.id.day_4_text_e),
                findViewById(R.id.day_5_text_e)};


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        IntFilds();

        toolbar = findViewById(R.id.toolbar_layout);

        new currentWeatherTask().execute();
        new weatherTask().execute();

    }

    class currentWeatherTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?zip=560043,in&units=metric&appid=542f9208ad25dfe2a92b501b74631b58");
            return response;
        }

        @Override
        protected void onPostExecute(String result) {


            try {
                Long timeStamp = 88888L;
                JSONObject jsonObject = new JSONObject(result);
                JSONObject main = jsonObject.getJSONObject("main");
                String currentTemp = main.getString("temp");
                timeStamp = jsonObject.getLong("dt");


                toolbar.setTitle(getTitle());
                toolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
                toolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
                toolbar.setTitle("Current Temp : " + currentTemp + " °C");

                todaytempTextm.setText(currentTemp + " °C");

                SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                sdf.setTimeZone(TimeZone.getTimeZone("ISD"));
                Date dateFormat = new java.util.Date(timeStamp * 1000);
                String weekday = sdf.format(dateFormat);
                todayText.setText(weekday);

                SimpleDateFormat sdfdate = new java.text.SimpleDateFormat("yyyy-MM-dd");
                sdfdate.setTimeZone(TimeZone.getTimeZone("ISD"));
                Date date = new java.util.Date(timeStamp * 1000);
                String formattedDate = sdfdate.format(date);
                todaydate.setText(formattedDate);


            } catch (JSONException e) {

                Toast.makeText(ScrollingActivity.this, "Error in Json Current" + e.getMessage(), Toast.LENGTH_LONG).show();
            }

        }

    }

    class weatherTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/forecast?zip=560043,in&units=metric&appid=542f9208ad25dfe2a92b501b74631b58");
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            String hour = "";


            try {
                Long timeStamp = 11111111L;
                for (int j = 0, p = 0; j < 40; j = j + 8, p++) {
                    for (int k = 0, l = j; k < 3; k++, l = l + 2) {
                        JSONObject jsonObjTop = new JSONObject(result);
                        JSONArray jsonArray = jsonObjTop.getJSONArray("list");
                        JSONObject jsonObj = jsonArray.getJSONObject(l);
                        JSONObject main = jsonObj.getJSONObject("main");
                        timeStamp = jsonObj.getLong("dt");

                        String temp = main.getString("temp") + "°C";
                        if (k == 0) mTemp[p].setText("M : " + temp);
                        if (k == 1) nTemp[p].setText("N : " + temp);
                        if (k == 2) eTemp[p].setText("E : " + temp);
                    }

                    SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                    sdf.setTimeZone(TimeZone.getTimeZone("ISD"));

                    Date dateFormat = new java.util.Date(timeStamp * 1000);

                    String weekday = sdf.format(dateFormat);
                    dayText[p].setText(weekday);

                }

            } catch (JSONException e) {

                Toast.makeText(ScrollingActivity.this, "Error in Json" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            Log.e("peturn", hour);

        }


    }


}
