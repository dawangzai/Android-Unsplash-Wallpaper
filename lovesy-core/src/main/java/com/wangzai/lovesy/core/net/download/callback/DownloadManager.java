package com.wangzai.lovesy.core.net.download.callback;

import com.wangzai.lovesy.core.net.download.db.DownloadDBController;
import com.wangzai.lovesy.core.net.download.domain.DownloadInfo;

import java.util.List;

/**
 * Created by renpingqing on 15/01/2017
 */

public interface DownloadManager {

  void download(DownloadInfo downloadInfo);

  void pause(DownloadInfo downloadInfo);

  void resume(DownloadInfo downloadInfo);

  void remove(DownloadInfo downloadInfo);

  void onDestroy();

  DownloadInfo getDownloadById(int id);

  List<DownloadInfo> findAllDownloading();

  List<DownloadInfo> findAllDownloaded();

  DownloadDBController getDownloadDBController();

}
