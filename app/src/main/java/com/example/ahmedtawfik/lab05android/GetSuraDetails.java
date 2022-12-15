package com.example.ahmedtawfik.lab05android;

import android.content.Context;
import android.support.annotation.NonNull;
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



public class GetSuraDetails extends AsyncTaskLoader<ArrayList<SuraInfo>> {

    String url = null;
    ArrayList<SuraInfo> suraInfoArrayList = new ArrayList<>();

    public GetSuraDetails(@NonNull Context context, String url) {
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
            urlConnection.setReadTimeout(100000);
            urlConnection.setConnectTimeout(20000000);
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);


        } catch (Exception e) {
            Log.d("Error", String.valueOf(e));

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

    @Override
    public ArrayList<SuraInfo> loadInBackground() {


        JSONObject jsonRoot = null;

        try {
            jsonRoot = new JSONObject(getHttpRequest(new URL(url)));

            JSONArray jsonArray = jsonRoot.getJSONArray("data");



            for (int i = 0; i < jsonArray.length(); i++) {
                SuraInfo suraInfoobj = new SuraInfo();
                JSONObject Sura = jsonArray.getJSONObject(i);

                suraInfoobj.set_id(Sura.getInt("number"));
                suraInfoobj.setName(Sura.getString("name"));
                suraInfoobj.setNum(Sura.getInt("numberOfAyahs"));
                suraInfoobj.setType(Sura.getString("revelationType"));


                suraInfoArrayList.add(suraInfoobj);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return suraInfoArrayList;

}}
