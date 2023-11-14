package hk.hku.cs.comp3330;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;

public class ChatGptFragment extends Fragment {

    private String readFile(File input_file) {
        String output = new String("");
        try {
            FileInputStream inputStream = new FileInputStream(input_file);
            output = IOUtils.toString(inputStream, "UTF-8");
        } catch (Exception e) {}
        return output;
    }

    WebView webView;
    Boolean loaded = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_gpt, container, false);
        String username = readFile(new File(getContext().getCacheDir(), "username"));
        String password = readFile(new File(getContext().getCacheDir(), "password"));
        String url = "https://adfs.connect.hku.hk/adfs/ls/?username=" + username + "@connect.hku.hk&wa=wsignin1.0&wtrealm=urn%3afederation%3aMicrosoftOnline&wctx=estsredirect%3d2%26estsrequest%3drQQIARAAjZE_TBNhAMXv48oJiFiZiInRXJxIrv2u1_vTiw60V2gh9NpKyx9jmt7dd9y1vfvK_aGlhFHjSGJcGE00ymQcjCEODE5MnVl0MYKJiWHCzTYubvqS9_KmN7zfLMnGWPku_KMEM0wGmibL6GjY_pI3PRF9doNevHf-_JY_c8x9nfkWPQBTOnZdpAcxqxkOfAhuW0HQ9uV4HIdBC-NmDJumraOYjp047tTjHwDoA3AGwOGIKHASx_Miz0miKLGswEoxUYQ8NEyT4SCCTFLQJEaDbIoxeAklkKGxKVY4HbmuzoWBlRgG9uweuhgZN7Hn1NrYDw7I90DRw66C851sXrFwWLOrntMIPKh2s_ONhVw6ne1ArQx9u7mamVvPdUWV69VStpGxFxLQLi_ls8Y6rrTamqdb9VKjnEeJIL3JSMLWg8xcMb3p7KynVdSEVoYXy7ji2giKOY1fRrxQ3ShhQVwyG2vzBa9VDDcKlcq8tpbleF0IatVGwdVsnCukVpRQSG2Hob5cdXm96O7Yxc4h-V8g3pHU4EwHuyckhdvItY1-BHyJgO-RSUjKY2MTUWKGuENcRsCL0QGzJ1uf5M9Wb_ENOf0xe1MjTkbjPVxlF_Ibq5qgNFVfwSVuha1UjLbWK6hCWJACN6kGaAmuKqX7CZndp8A-RR1R42NklKDJTJE9o8BPCjy9QhyN_4t3_yo4nUxOUHqrbjv-9OwubRu1ADeRS8u7dNfxa7o-bNv1Voh8Wn5ID_bpR3t7e8fXiMupV49fX_Rf_vqRexslfgM1#";
        webView = view.findViewById(R.id.chatgpt_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.loadUrl(url);
        String javascript = String.format("javascript:(function(){document.getElementById('passwordInput').value='%s';document.getElementById('submitButton').click();})()", password);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.contains("outlook")) {
                    webView.loadUrl("https://chatgpt.hku.hk/");
                }
                if (!loaded) {
                    webView.loadUrl(javascript);
                    loaded = true;
                    webView.loadUrl("https://chatgpt.hku.hk/");
                }
            }

        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                WebView background_view = new WebView(view.getContext());
                background_view.getSettings().setJavaScriptEnabled(true);
                background_view.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                background_view.getSettings().setSupportMultipleWindows(true);
                ((WebView.WebViewTransport) resultMsg.obj).setWebView(background_view);;
                resultMsg.sendToTarget();
                background_view.setWebViewClient(new WebViewClient() {
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    };
                });

                return true;
            }
        });

        return view;
    }
}