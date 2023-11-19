package hk.hku.cs.comp3330;

import static android.content.Context.DOWNLOAD_SERVICE;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.shapesecurity.salvation2.URLs.URI;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;

public class MoodleFragment extends Fragment {

    private String readFile(File input_file) {
        String output = new String("");
        try {
            FileInputStream inputStream = new FileInputStream(input_file);
            output = IOUtils.toString(inputStream, "UTF-8");
        } catch (Exception e) {}
        return output;
    }

    WebView webView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_moodle, container, false);
        webView = view.findViewById(R.id.moodle_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.loadUrl("https://hkuportal.hku.hk/cas/login?service=https%3A%2F%2Fmoodle.hku.hk%2Flogin%2Findex.php%3FauthCAS%3DCAS");
        String username = readFile(new File(getContext().getCacheDir(), "username"));
        String password = readFile(new File(getContext().getCacheDir(), "password"));
        String javascript = String.format("javascript:(function(){document.getElementById('username').value='%s';document.getElementById('password').value='%s';document.getElementById('login_btn').click();})()", username, password);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webView.loadUrl(javascript);

            }
        });
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                DownloadManager.Request temp = new DownloadManager.Request(Uri.parse(url));
                String filename = URLUtil.guessFileName(url, null, MimeTypeMap.getFileExtensionFromUrl(url));
                temp.addRequestHeader("Cookie", CookieManager.getInstance().getCookie(url));
                temp.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                temp.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
                ((DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE)).enqueue(temp);
            }
        });
        return view;
    }
}