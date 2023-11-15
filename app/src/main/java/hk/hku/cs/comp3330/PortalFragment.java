package hk.hku.cs.comp3330;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class PortalFragment extends Fragment {
    WebView webView;
    Boolean loaded = false;
    private String readFile(File input_file) {
        String output = new String("");
        try {
            FileInputStream inputStream = new FileInputStream(input_file);
            output = IOUtils.toString(inputStream, "UTF-8");
        } catch (Exception e) {}
        return output;
    }

    private void startWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.loadUrl("https://hkuportal.hku.hk/login.html");
        String username = readFile(new File(getContext().getCacheDir(), "username"));
        String password = readFile(new File(getContext().getCacheDir(), "password"));
        String javascript = String.format("javascript:(function(){document.getElementById('username').value='%s';document.getElementById('password').value='%s';document.getElementById('login_btn').click();})()", username, password);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!loaded) {
                    loaded = true;
                    webView.loadUrl(javascript);
                    //webView.loadUrl("https://sis-main.hku.hk/psc/sisprod/EMPLOYEE/PSFT_CS/c/SA_LEARNER_SERVICES.SSR_SSENRL_CART.GBL?pslnkid=Z_HC_SSR_SSENRL_CART_LNK&FolderPath=PORTAL_ROOT_OBJECT.Z_SIS_MENU.Z_ENROLLMENT.Z_HC_SSR_SSENRL_CART_LNK&IsFolder=false&IgnoreParamTempl=FolderPath%2cIsFolder&PortalActualURL=https%3a%2f%2fsis-main.hku.hk%2fpsc%2fsisprod%2fEMPLOYEE%2fPSFT_CS%2fc%2fSA_LEARNER_SERVICES.SSR_SSENRL_CART.GBL%3fpslnkid%3dZ_HC_SSR_SSENRL_CART_LNK&PortalContentURL=https%3a%2f%2fsis-main.hku.hk%2fpsc%2fsisprod%2fEMPLOYEE%2fPSFT_CS%2fc%2fSA_LEARNER_SERVICES.SSR_SSENRL_CART.GBL%3fpslnkid%3dZ_HC_SSR_SSENRL_CART_LNK&PortalContentProvider=PSFT_CS&PortalCRefLabel=Enrollment%20Add%20Classes&PortalRegistryName=EMPLOYEE&PortalServletURI=https%3a%2f%2fsis-eportal.hku.hk%2fpsp%2fptlprod%2f&PortalURI=https%3a%2f%2fsis-eportal.hku.hk%2fpsc%2fptlprod%2f&PortalHostNode=EMPL&NoCrumbs=yes&PortalKeyStruct=yes");
                }
                if (url.equals("https://sis-eportal.hku.hk/psc/ptlprod/EMPLOYEE/EMPL/c/NUI_FRAMEWORK.PT_LANDINGPAGE.GBL?")) {
                    webView.loadUrl("https://sis-main.hku.hk/psc/sisprod/EMPLOYEE/PSFT_CS/c/SA_LEARNER_SERVICES.SSR_SSENRL_CART.GBL?pslnkid=Z_HC_SSR_SSENRL_CART_LNK&FolderPath=PORTAL_ROOT_OBJECT.Z_SIS_MENU.Z_ENROLLMENT.Z_HC_SSR_SSENRL_CART_LNK&IsFolder=false&IgnoreParamTempl=FolderPath%2cIsFolder&PortalActualURL=https%3a%2f%2fsis-main.hku.hk%2fpsc%2fsisprod%2fEMPLOYEE%2fPSFT_CS%2fc%2fSA_LEARNER_SERVICES.SSR_SSENRL_CART.GBL%3fpslnkid%3dZ_HC_SSR_SSENRL_CART_LNK&PortalContentURL=https%3a%2f%2fsis-main.hku.hk%2fpsc%2fsisprod%2fEMPLOYEE%2fPSFT_CS%2fc%2fSA_LEARNER_SERVICES.SSR_SSENRL_CART.GBL%3fpslnkid%3dZ_HC_SSR_SSENRL_CART_LNK&PortalContentProvider=PSFT_CS&PortalCRefLabel=Enrollment%20Add%20Classes&PortalRegistryName=EMPLOYEE&PortalServletURI=https%3a%2f%2fsis-eportal.hku.hk%2fpsp%2fptlprod%2f&PortalURI=https%3a%2f%2fsis-eportal.hku.hk%2fpsc%2fptlprod%2f&PortalHostNode=EMPL&NoCrumbs=yes&PortalKeyStruct=yes");
                }
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_portal, container, false);
        webView = view.findViewById(R.id.portal_webview);
        startWebView();
        return view;
    }
}