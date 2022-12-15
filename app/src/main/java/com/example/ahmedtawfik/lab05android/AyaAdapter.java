package com.example.ahmedtawfik.lab05android;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;


import java.io.IOException;
import java.util.ArrayList;

public class AyaAdapter extends ArrayAdapter<AyaInfo>
{
    public AyaAdapter(Context context, ArrayList<AyaInfo> userArrayList){

        super(context,R.layout.aya_custom_item,userArrayList);

    }


    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {

        final AyaInfo ayaInfo = getItem(position);

        if (convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.aya_custom_item,parent,false);

        }

        TextView aya_number = convertView.findViewById(R.id.aya_number); //int
        TextView aya_text = convertView.findViewById(R.id.aya_text);
        ImageButton btn_audio = convertView.findViewById(R.id.btn_play);




        aya_number.setText( String.valueOf(ayaInfo.get_id()));
        aya_text.setText(ayaInfo.getText());



        btn_audio.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                class CustomMediaPlayer extends MediaPlayer
                {
                    String dataSource;

                    @Override
                    public void setDataSource(String path) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException
                    {
                        // TODO Auto-generated method stub
                        super.setDataSource(path);
                        dataSource = path;
                    }

                }

                CustomMediaPlayer customMediaPlayer = new CustomMediaPlayer ();
                customMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    customMediaPlayer.setDataSource(ayaInfo.getAudio());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    customMediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                customMediaPlayer.start();


            }});


        return convertView;
    }
}
