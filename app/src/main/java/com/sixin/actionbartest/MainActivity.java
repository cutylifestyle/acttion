package com.sixin.actionbartest;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.sixin.actionbartest.permissionsutil.PermissionsDenied;
import com.sixin.actionbartest.permissionsutil.PermissionsGranted;
import com.sixin.actionbartest.permissionsutil.PermissionsNoNeeded;
import com.sixin.actionbartest.permissionsutil.PermissionsUtil;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // TODO: 2017/12/25    返回栈源码分析  commit 与commitAllow...()源码分析
    // TODO: 2017/12/29    activity.onPause()  onStop()源码分析
    // TODO: 2017/12/29  碎片中onCreateOptions() 源码分析

    private SimpleExoPlayer player;

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PermissionsUtil.requestPermissions(this,100,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);


    }

    private void init() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"m.mp3";
        File file = new File(path);

        RamberPlayerView playerView = (RamberPlayerView) findViewById(R.id.playerView);


        Handler handler = new Handler();
        BandwidthMeter meter = new DefaultBandwidthMeter();
        TrackSelection.Factory factory = new AdaptiveTrackSelection.Factory(meter);
        TrackSelector trackSelector = new DefaultTrackSelector(factory);
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
        playerView.setPlayer(player);

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this,"actionbartest"));
        player.setPlayWhenReady(true);
        MediaSource source = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.fromFile(file));
        player.prepare(source);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }



    @PermissionsGranted
    private void doSomething() {
        init();
    }

    @PermissionsDenied
    private void doSomething1(List<String> permissions){
        Toast.makeText(this, "222222...."+permissions.size(), Toast.LENGTH_SHORT).show();
    }

    @PermissionsNoNeeded
    private void doSomething2(){
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionsUtil.onRequestPermissionsResult(this,requestCode,
                permissions,grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
