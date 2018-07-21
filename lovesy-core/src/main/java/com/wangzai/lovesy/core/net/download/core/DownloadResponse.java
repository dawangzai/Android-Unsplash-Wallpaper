package com.wangzai.lovesy.core.net.download.core;

import com.wangzai.lovesy.core.net.download.domain.DownloadInfo;
import com.wangzai.lovesy.core.net.download.exception.DownloadException;

/**
 * Created by renpingqing on 17/1/22
 */

public interface DownloadResponse {

  void onStatusChanged(DownloadInfo downloadInfo);

  void handleException(DownloadException exception);
}
