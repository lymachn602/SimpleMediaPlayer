package com.example.linyuming.simplemediaplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer player;
    private String mPath;
    private VideoView videoView;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView= (TextView) findViewById(R.id.tv);
        Intent intent=getIntent();
        Uri uri=intent.getData();
        if(uri!=null){
            mPath=uri.getPath();
            setTitle(mPath);
            if(intent.getType().contains("video")){
                MediaController mc=new MediaController(this);
                videoView=new VideoView(this);
                videoView.setVideoPath(mPath);
                videoView.setMediaController(mc);
                mc.setMediaPlayer(videoView);
                videoView.start();
                setContentView(videoView);
            }else if (intent.getType().contains("audio")){

                player=new MediaPlayer();
                try {
                    player.setDataSource(mPath);
                    player.prepare();
                    player.start();
                    textView.setText(getString(R.string.playing));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,0,0,"播放");
        menu.add(0,1,0,"暂停");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 0:
                if(player==null){
                    Toast.makeText(this,"没有音乐资源，无法开始播放",Toast.LENGTH_SHORT).show();
                }else{
                    player.start();
                    textView.setText(getString(R.string.playing));
                }
                break;
            case 1:
                if(player==null){
                    Toast.makeText(this,"没有音乐资源，无须暂停播放",Toast.LENGTH_SHORT).show();

                }else{
                    player.pause();
                    textView.setText(getString(R.string.noplaying));
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        if(player!=null){
            player.stop();
        }
        if(videoView!=null){
            videoView.stopPlayback();
        }
        super.onDestroy();
    }
}
