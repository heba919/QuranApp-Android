package com.example.ahmedtawfik.lab05android;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.ArrayList;

public class AyaActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<ArrayList<AyaInfo>> {


   private static String AYAHS_URL1 = "http://api.alquran.cloud/v1/surah/";
   private static String AYAHS_URL2 = "/ar.alafasy";





    String id;
    ListView lv_showAyahs;
    ProgressBar pb2_progress;






    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayahs);

        lv_showAyahs=findViewById(R.id.lv_showAyahs);
        pb2_progress=findViewById( R.id.pb2_progress);

        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(0, null, AyaActivity.this).forceLoad();
        id = getIntent().getStringExtra("id");








    }

    @Override
    public Loader<ArrayList<AyaInfo>> onCreateLoader(int i, @Nullable Bundle bundle) {
        pb2_progress.setVisibility(View.VISIBLE);
        id = getIntent().getStringExtra("id");
        return new GetAyahDetalis(this, AYAHS_URL1+id+AYAHS_URL2);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<AyaInfo>> loader, ArrayList<AyaInfo> DataAya) {
        pb2_progress.setVisibility(View.GONE);


        if (DataAya.size() == 0)
            Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_SHORT).show();

        AyaAdapter ayaAdapter =  new AyaAdapter(getApplicationContext(), DataAya);


        lv_showAyahs.setAdapter(ayaAdapter);



    }









    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<AyaInfo>> loader) {

    }
}