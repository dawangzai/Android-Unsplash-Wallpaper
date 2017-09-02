package com.wangzai.lovesy.ui.login;

import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.wangzai.lovesy.R;
import com.wangzai.lovesy.base.BaseActivity;
import com.wangzai.lovesy.bean.AccessToken;
import com.wangzai.lovesy.http.HttpClient;
import com.wangzai.lovesy.http.callback.OnResultCallback;
import com.wangzai.lovesy.http.observer.HttpObserver;
import com.wangzai.lovesy.utils.LogUtil;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * Created by wangzai on 2017/7/11.
 */

public class LoginActivity extends BaseActivity {
    private WebView webView;
    String code;
    private Button btnToken;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        webView = (WebView) findViewById(R.id.webView);
        btnToken = (Button) findViewById(R.id.btnToken);
        btnToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.i("token", "店家了---------");
//                requestData(code);
                requestCode();
            }
        });

        webView.addJavascriptInterface(new InJavaScriptLocalObj(), "java_obj");
//声明WebSettings子类
        WebSettings webSettings = webView.getSettings();

//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
// 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
// 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可

//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口

        webSettings.setDomStorageEnabled(true);


//步骤3. 复写shouldOverrideUrlLoading()方法，使得打开网页时不调用系统浏览器， 而是在本WebView中显示
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed(); // 接受所有网站的证书
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtil.i("webview", url.toString());
                if (url.contains("http://dawangzai.com")) {
                    String[] split = url.split("code=");
                    code = split[1];
                    webView.postUrl(
                            "https://unsplash.com/oauth/token?client_id=b05bfc46a0de4842346cb5ce7c766b3a8c9da071ec77f3b5f719406829c2fb31&client_secret=af3b7125ce78c9a05bac4f9b9f216260919c3646eaf02ea9f36f0f10b014a965&redirect_uri=http://dawangzai.com&code=" + code + "&grant_type=authorization_code",
                            null);
                    return true;
                }
                webView.loadUrl(url);
                return true;
            }


//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//                    boolean redirect = request.isRedirect();
//                    Uri url = request.getUrl();
//                    LogUtil.i("webview", url.toString() + "------" + redirect);
//                }
//                return super.shouldOverrideUrlLoading(view, request);
//            }


            @Override
            public void onPageFinished(WebView view, String url) {
                // 获取页面内容
                view.loadUrl("javascript:window.java_obj.showSource("
//                        + "document.getElementsByTagName('html')[0].innerHTML);");
                + "document.getElementsByTagName('pre')[0].innerHTML);");

                // 获取解析<meta name="share-description" content="获取到的值">
                view.loadUrl("javascript:window.java_obj.showDescription("
//                        + "document.querySelector('body')"
                        + "document.querySelector('meta[name=\"share-description\"]').getAttribute('content')"
                        + ");");

                super.onPageFinished(view, url);
            }
        });

        webView.loadUrl("https://unsplash.com/oauth/authorize?client_id=b05bfc46a0de4842346cb5ce7c766b3a8c9da071ec77f3b5f719406829c2fb31&redirect_uri=http://dawangzai.com&response_type=code&scope=public+read_user+write_user+read_collections+write_collections");
//        webView.loadUrl("https://unsplash.com/oauth/login");
    }

    public final class InJavaScriptLocalObj {
        @JavascriptInterface
        public void showSource(String html) {
            LogUtil.i("html",html);
//            System.out.println("====>html=" + html);
        }

        @JavascriptInterface
        public void showDescription(String str) {
            LogUtil.i("string",str);
//            System.out.println("====>String=" + str);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.clearCache(true);

//清除当前webview访问的历史记录
//只会webview访问历史记录里的所有记录除了当前访问记录
        webView.clearHistory();

//这个api仅仅清除自动完成填充的表单数据，并不会清除WebView存储到本地的数据
        webView.clearFormData();
    }

    private void requestData(String code) {
        HttpObserver<AccessToken> observer = new HttpObserver<AccessToken>(new OnResultCallback<AccessToken>() {
            @Override
            public void onSuccess(AccessToken accessToken) {
                LogUtil.i("token", accessToken.getAccess_token());
            }

            @Override
            public void onFailed(int code, String message) {
                LogUtil.i("token", "onFailed");
            }
        });
        HttpClient.getInstance().login(observer, code);
    }

    private void requestCode() {
        HttpObserver<AccessToken> observer = new HttpObserver<AccessToken>(new OnResultCallback<AccessToken>() {
            @Override
            public void onSuccess(AccessToken accessToken) {
                LogUtil.i("token", accessToken.getAuthorization_code());
            }

            @Override
            public void onFailed(int code, String message) {
                LogUtil.i("token", "onFailed");
            }
        });
        HttpClient.getInstance().getAccessCode(observer);
    }
}
