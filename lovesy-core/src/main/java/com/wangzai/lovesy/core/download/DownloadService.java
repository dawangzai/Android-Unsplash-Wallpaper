package com.wangzai.lovesy.core.download;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.wangzai.lovesy.core.app.LoveSy;
import com.wangzai.lovesy.core.download.entities.FileInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wangzai on 2017/11/30
 */

public class DownloadService extends Service {

    private static final class FileHolder {
        private static final ArrayList<FileInfo> FILE_INFOS = new ArrayList<>();
    }

    private static final class TaskHolder {
        private static final ConcurrentHashMap<Integer, DownloadTask> DOWNLOAD_TASKS = new ConcurrentHashMap<>();
    }

    public static ArrayList<FileInfo> getFiles() {
        return FileHolder.FILE_INFOS;
    }

    public static ConcurrentHashMap<Integer, DownloadTask> getTasks() {
        return TaskHolder.DOWNLOAD_TASKS;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public static DownloadManager sDownloadManager;

    public static DownloadManager getDownloadManager() {
        if (!isServiceRunning(LoveSy.getApplicationContext())) {
            Intent downloadSvr = new Intent(LoveSy.getApplicationContext(), DownloadService.class);
            LoveSy.getApplicationContext().startService(downloadSvr);
        }
        if (DownloadService.sDownloadManager == null) {
            DownloadService.sDownloadManager = DownloadManager.getInstance();
        }
        return sDownloadManager;
    }

    private static boolean isServiceRunning(Context context) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(Integer.MAX_VALUE);

        if (serviceList == null || serviceList.size() == 0) {
            return false;
        }

        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(
                    DownloadService.class.getName())) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

//    public static final int MSG_FILE = 0x1;
//
//    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
//    private Map<Integer, DownloadTask> mDownloadList = new LinkedHashMap<>();
//    @SuppressLint("HandlerLeak")
//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case MSG_FILE:
//                    FileInfo fileEntity = (FileInfo) msg.obj;
//                    LogUtil.i("FileInfo=" + fileEntity.toString());
//                    final DownloadTask downloadHandler = new DownloadTask(DownloadService.this, fileEntity);
//                    mDownloadList.put(fileEntity.getId(), downloadHandler);
//                    downloadHandler.download();
//                    break;
//                default:
//                    break;
//            }
//        }
//    };
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        if (intent != null) {
//            final FileInfo fileEntity = (FileInfo) intent.getSerializableExtra(MultiThreadDownload.EXTRA_FILE);
//            final DownloadTask downloadHandler = mDownloadList.get(fileEntity.getId());
//            if (MultiThreadDownload.ACTION_START.equals(intent.getAction())) {
//                if (downloadHandler == null) {
////                    setFileLength(fileEntity);
//                    new InitThread(fileEntity).start();
//                } else {
//                    downloadHandler.download();
//                }
//            } else if (MultiThreadDownload.ACTION_STOP.equals(intent.getAction())) {
//                if (downloadHandler != null) {
//                    LogUtil.i("暂停DownloadHandler=" + mDownloadList.toString());
//                    downloadHandler.pause();
//                }
//            }
//        }
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//    //    public DownloadService() {
////        super("DownloadService");
////    }
////
////    @Override
////    protected void onHandleIntent(@Nullable Intent intent) {
////        if (intent != null) {
////            final FileInfo fileEntity = (FileInfo) intent.getSerializableExtra(MultiThreadDownload.EXTRA_FILE);
////            final DownloadTask handler = mDownloadList.get(fileEntity.getId());
////            if (MultiThreadDownload.ACTION_START.equals(intent.getAction())) {
////                if (handler == null) {
////                    setFileLength(fileEntity);
////                } else {
////                    handler.download();
////                }
////            } else if (MultiThreadDownload.ACTION_STOP.equals(intent.getAction())) {
////                if (handler != null) {
////                    LogUtil.i("暂停DownloadHandler=" + mDownloadList.toString());
////                    handler.pause();
////                }
////            }
////        }
////    }
//
//    private class InitThread extends Thread {
//        private FileInfo mFileEntity;
//
//        public InitThread(FileInfo fileEntity) {
//            this.mFileEntity = fileEntity;
//        }
//
//        @Override
//        public void run() {
//            super.run();
//            setFileLength(mFileEntity);
//        }
//    }
//
//    @SuppressWarnings("ResultOfMethodCallIgnored")
//    private void setFileLength(FileInfo fileEntity) {
//        HttpURLConnection conn = null;
//        RandomAccessFile raf = null;
//        try {
//            URL url = new URL(fileEntity.getUrl());
//            conn = (HttpURLConnection) url.openConnection();
//            conn.setConnectTimeout(3000);
//            conn.setRequestMethod("GET");
//            int length = -1;
//            if (conn.getResponseCode() == 200) {
//                length = conn.getContentLength();
//            }
//            if (length <= 0) {
//                return;
//            }
//            LogUtil.i("FileLength=" + length);
//            File dir = new File(fileEntity.getDir());
//            if (!dir.exists()) {
//                dir.mkdir();
//            }
//            File file = new File(dir, fileEntity.getFileName());
//            LogUtil.i(file.getAbsolutePath());
//
//            raf = new RandomAccessFile(file, "rwd");
//            raf.setLength(length);
//            fileEntity.setLength(length);
//
//            LogUtil.i(fileEntity.toString());
//
//            mHandler.obtainMessage(MSG_FILE, fileEntity).sendToTarget();
////            final DownloadTask downloadHandler = new DownloadTask(this, fileEntity);
////            mDownloadList.put(fileEntity.getId(), downloadHandler);
////            LogUtil.i(mDownloadList.get(fileEntity.getId()).toString());
////            downloadHandler.download();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (raf != null) {
//                    raf.close();
//                }
//                if (conn != null) {
//                    conn.disconnect();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
