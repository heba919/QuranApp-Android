package com.example.ahmedtawfik.lab05android;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;





//http://api.alquran.cloud/v1/surah/114/ar.alafasy



public class GetAyahDetalis extends AsyncTaskLoader<ArrayList<AyaInfo>> {

    String url = null;

    ArrayList<AyaInfo> ayah = new ArrayList<>();

    public GetAyahDetalis(Context context, String url ) {
        super(context);
        this.url = url;


    }


    private String getHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        HttpURLConnection urlConnection = null;

        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(20000);
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);


        } catch (Exception e) {
            Log.d("Errror", String.valueOf(e));

            return "No Internet Connection";
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();


            if (inputStream != null)
                inputStream.close();
        }


        return jsonResponse;
    }

    private String readFromStream(InputStream inputStream)throws IOException {

        StringBuilder result=new StringBuilder();

        if(inputStream!=null){
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream,Charset.forName("UTF-8"));

            BufferedReader reader=new BufferedReader(inputStreamReader);

            String line=reader.readLine();

            while (line!=null){
                result.append(line);
                line=reader.readLine();
            }

        }
        return result.toString();
    }



    public ArrayList<AyaInfo> loadInBackground()
    {

        JSONObject jsonRoot = null;

        try {
            jsonRoot = new JSONObject(getHttpRequest(new URL(url)));

            JSONObject jsonDataArray = jsonRoot.getJSONObject("data");

            JSONArray ayahs = jsonDataArray.getJSONArray("ayahs");




            for (int i = 0; i < ayahs.length(); i++) {

                AyaInfo ayaInfoObj = new AyaInfo();
                JSONObject ayahsinfo = ayahs.getJSONObject(i);

                ayaInfoObj.set_id(ayahsinfo.getInt("numberInSurah"));
                ayaInfoObj.setText(ayahsinfo.getString("text"));
                ayaInfoObj.setAudio(ayahsinfo.getString("audio"));

                ayah.add( ayaInfoObj );

            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ayah;

    }
}
