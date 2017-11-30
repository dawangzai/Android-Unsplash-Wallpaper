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
        FileEntity fileEntity = new FileEntity(0, "https://images.unsplash.com/photo-1511898290398-cee3038fa7a7",
                "picture.jpg", 0, 0);
        switch (view.getId()) {
            case R.id.btn_download:
                Intent intent = new Intent(this, DownloadService.class);
                intent.setAction(DownloadService.ACTION_START);
                intent.putExtra(DownloadService.EXTRA_FILE, fileEntity);
                startService(intent);
                break;
            case R.id.btn_pause:
                Intent intent1 = new Intent(this, DownloadService.class);
                intent1.setAction(DownloadService.ACTION_STOP);
                intent1.putExtra(DownloadService.EXTRA_FILE, fileEntity);
                startService(intent1);
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
