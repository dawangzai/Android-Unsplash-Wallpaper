package com.wangzai.lovesy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wangzai.lovesy.core.net.download.entities.FileEntity;
import com.wangzai.lovesy.core.net.download.services.DownloadService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangzai on 2017/9/7
 */

public class HttpTestActivity extends AppCompatActivity {


    @BindView(R.id.pb_progress)
    ProgressBar mPbProgress;
    @BindView(R.id.btn_download)
    Button mBtnDownload;
    @BindView(R.id.btn_pause)
    Button mBtnPause;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        mPbProgress.setMax(100);

        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadService.ACTION_UPDATE);
        intentFilter.addAction(DownloadService.ACTION_FINISH);
        registerReceiver(mBroadcastReceiver, intentFilter);

    }

    @OnClick({R.id.btn_download, R.id.btn_pause})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        FileEntity fileEntity = new FileEntity(0, "http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=16f6ff0030292df583cea456d4583615/e1fe9925bc315c60b6b051c087b1cb13495477f3.jpg",
                "picture.jpg", 0, 0);
        switch (view.getId()) {
            case R.id.btn_download:
                intent.setClass(this, DownloadService.class);
                intent.setAction(DownloadService.ACTION_START);
                intent.putExtra(DownloadService.EXTRA_FILE, fileEntity);
                startService(intent);
                break;
            case R.id.btn_pause:
                intent.setClass(this, DownloadService.class);
                intent.setAction(DownloadService.ACTION_STOP);
                intent.putExtra(DownloadService.EXTRA_FILE, fileEntity);
                startService(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownloadService.ACTION_UPDATE.equals(intent.getAction())) {
                //更新进度
                final int finished = intent.getIntExtra(DownloadService.EXTRA_UPDATE, 0);
                int id = intent.getIntExtra(DownloadService.EXTRA_ID, 0);
                mPbProgress.setProgress(finished);
            } else if (DownloadService.ACTION_FINISH.equals(intent.getAction())) {
                Toast.makeText(context, "下载完成", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
