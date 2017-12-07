package com.wangzai.lovesy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wangzai.lovesy.core.download.MultiThreadDownload;

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
        intentFilter.addAction(MultiThreadDownload.ACTION_UPDATE);
        intentFilter.addAction(MultiThreadDownload.ACTION_FINISH);
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver, intentFilter);

    }

    @OnClick({R.id.btn_download, R.id.btn_pause})
    public void onViewClicked(View view) {
//        String url = "https://images.unsplash.com/photo-1511898290398-cee3038fa7a7";
        String url = "https://images.unsplash.com/photo-1512508561942-18fbe6d5d0cf?ixlib=rb-0.3.5&q=85&fm=jpg&crop=entropy&cs=srgb&s=b5f72cbead22d905154fcd500cf558bb";
        final MultiThreadDownload md = MultiThreadDownload.builder()
                .context(this)
                .url(url)
                .extension("jpg")
                .build();
        switch (view.getId()) {
            case R.id.btn_download:
                md.download();
                break;
            case R.id.btn_pause:
                md.pause();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
    }

    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (MultiThreadDownload.ACTION_UPDATE.equals(intent.getAction())) {
                //更新进度
                final int finished = intent.getIntExtra(MultiThreadDownload.EXTRA_UPDATE, 0);
                int id = intent.getIntExtra(MultiThreadDownload.EXTRA_FILE_ID, 0);
                mPbProgress.setProgress(finished);
            } else if (MultiThreadDownload.ACTION_FINISH.equals(intent.getAction())) {
                Toast.makeText(context, "下载完成", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
