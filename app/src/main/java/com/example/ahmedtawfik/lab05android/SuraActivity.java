package com.example.ahmedtawfik.lab05android;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class SuraActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<SuraInfo>> {


    ListView lv_showEmployees;
    SuraAdapter suraAdapter ;

    ProgressBar progressBar;

    private static String SURA_URL = "http://api.alquran.cloud/v1/surah";


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sura);

        lv_showEmployees = findViewById(R.id.lv_showEmployees);

        progressBar = findViewById(R.id.pb_progress);

        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(0, null, SuraActivity.this).forceLoad();





        lv_showEmployees.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                Intent i = new Intent(SuraActivity.this , AyaActivity.class);

                i.putExtra("id", String.valueOf(position+1));
                startActivity(i);

            }
        });

         }





    @Override
    public Loader<ArrayList<SuraInfo>> onCreateLoader(int i, @Nullable Bundle bundle) {
        progressBar.setVisibility(View.VISIBLE);
        return new GetSuraDetails(this, SURA_URL);
    }



    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<SuraInfo>> loader, ArrayList<SuraInfo> sura) {

        progressBar.setVisibility(View.GONE);

        if (sura.size() == 0)
            Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_SHORT).show();

       suraAdapter = new SuraAdapter(getApplicationContext(), sura);


        lv_showEmployees.setAdapter(suraAdapter);



    }








    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<SuraInfo>> loader) {

    }




}
