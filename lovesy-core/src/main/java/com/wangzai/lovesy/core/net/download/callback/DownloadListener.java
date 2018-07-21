package com.wangzai.lovesy.core.net.download.callback;


import com.wangzai.lovesy.core.net.download.exception.DownloadException;

/**
 * Created by renpingqing on 17/2/23
 */

public interface DownloadListener {

  void onStart();

  void onWaited();

  void onPaused();

  void onDownloading(long progress, long size);

  void onRemoved();

  void onDownloadSuccess();

  void onDownloadFailed(DownloadException e);
}
