package com.example.khoslalabs;

import android.os.AsyncTask;
import android.os.Bundle;

import com.androdocs.httprequest.HttpRequest;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.Delayed;

import static android.os.Build.ID;

public class ScrollingActivity extends AppCompatActivity {

    String ZIP = "560043,in";
    String API = "542f9208ad25dfe2a92b501b74631b58";
    TextView[] dayText,mTemp,eTemp,nTemp;
TextView todayText,todaydate,todaytempTextn,todaytempTextm,todaytempTexte;
    void IntFilds () {

        todayText = (TextView)findViewById(R.id.today_text_day);
        todaydate =(TextView)findViewById(R.id.today_text_date);
       // todaytempTexte=(TextView)findViewById(R.id.today_text_e);
        todaytempTextm=(TextView)findViewById(R.id.today_text_m);
      //  todaytempTextn=(TextView)findViewById(R.id.today_text_n);

        dayText  = new TextView[]{
               (TextView) findViewById(R.id.day_1_text_day),
               (TextView) findViewById(R.id.day_2_text_day),
               (TextView) findViewById(R.id.day_3_text_day),
               (TextView) findViewById(R.id.day_4_text_day),
               (TextView) findViewById(R.id.day_5_text_day)
       };
        mTemp = new TextView[]{
                (TextView) findViewById(R.id.day_1_text_m),
                (TextView) findViewById(R.id.day_2_text_m),
                (TextView) findViewById(R.id.day_3_text_m),
                (TextView) findViewById(R.id.day_4_text_m),
                (TextView) findViewById(R.id.day_5_text_m)};

         nTemp = new TextView[]{
                 (TextView) findViewById(R.id.day_1_text_n),
                 (TextView) findViewById(R.id.day_2_text_n),
                 (TextView) findViewById(R.id.day_3_text_n),
                 (TextView) findViewById(R.id.day_4_text_n),
                 (TextView) findViewById(R.id.day_5_text_n)};

     eTemp = new TextView[]{(TextView) findViewById(R.id.day_1_text_e),
        (TextView) findViewById(R.id.day_2_text_e),
        (TextView) findViewById(R.id.day_3_text_e),
        (TextView) findViewById(R.id.day_4_text_e),
        (TextView) findViewById(R.id.day_5_text_e)};


    }
    CollapsingToolbarLayout toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        IntFilds();

    toolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        new currentWeatherTask().execute();
        new weatherTask().execute();

    }

    class  currentWeatherTask extends  AsyncTask<String ,Void, String>{
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
                Long timeStamp =88888L;
                JSONObject jsonObject = new JSONObject(result);
                JSONObject main = jsonObject.getJSONObject("main");
                String currentTemp = main.getString("temp");



                toolbar.setTitle(getTitle());
                toolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
                toolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
                toolbar.setTitle("Current Temp : "+currentTemp+ " °C");


                SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                sdf.setTimeZone(TimeZone.getTimeZone("ISD"));
                Date dateFormat = new java.util.Date(timeStamp * 1000);
                String weekday = sdf.format(dateFormat );
                todayText.setText(weekday);
                todaytempTextm.setText( currentTemp+ " °C");

               SimpleDateFormat simpledate = new SimpleDateFormat("yyyy-MM-dd");
                simpledate.setTimeZone(TimeZone.getTimeZone("ISD"));
                Date dateFormatDate = new java.util.Date(timeStamp * 1000);
                String tdate = simpledate.format(dateFormatDate);
                todaydate.setText(tdate);





            } catch (JSONException e) {

                Toast.makeText(ScrollingActivity.this,"Error in Json Current"+e.getMessage(),Toast.LENGTH_LONG).show();
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
            String hour="";


            try {
                Long timeStamp=11111111L;
               for(int j=0, p =0 ; j<40; j= j+8, p++ ) {
                    for (int k= 0,  l=j ; k<3; k++, l=l+2) {
                        JSONObject jsonObjTop = new JSONObject(result);
                        JSONArray jsonArray = jsonObjTop.getJSONArray("list");
                        JSONObject jsonObj = jsonArray.getJSONObject(l);
                        JSONObject main = jsonObj.getJSONObject("main");
                        timeStamp = jsonObj.getLong("dt");

                        String temp = main.getString("temp") + "°C";
                        if(k==0) mTemp[p].setText("M : "+temp);
                        if(k==1) nTemp[p].setText("N : "+temp);
                        if(k==2) eTemp[p].setText("E : "+temp);
                    }

                   SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                   sdf.setTimeZone(TimeZone.getTimeZone("ISD"));

                   Date dateFormat = new java.util.Date(timeStamp * 1000);

                   String weekday = sdf.format(dateFormat );
                    dayText[p].setText(weekday);

                }

            } catch (JSONException e) {

                Toast.makeText(ScrollingActivity.this,"Error in Json"+e.getMessage(),Toast.LENGTH_LONG).show();
            }
            Log.e("peturn",hour);

        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
