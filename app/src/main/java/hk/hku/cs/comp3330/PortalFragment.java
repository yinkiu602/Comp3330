package hk.hku.cs.comp3330;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;

public class PortalFragment extends Fragment {
    private Button button_test;
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

    private void setTopbar(Boolean flag) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
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
        Toolbar toolbar = view.findViewById(R.id.portal_toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            activity.getSupportActionBar().setDisplayShowHomeEnabled(false);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    activity.getSupportActionBar().setDisplayShowHomeEnabled(false);
                    webView.setVisibility(View.GONE);

                }
            });
        }


        ((Button) view.findViewById(R.id.add_class_button)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                webView.loadUrl("https://sis-main.hku.hk/psc/sisprod/EMPLOYEE/PSFT_CS/c/SA_LEARNER_SERVICES.SSR_SSENRL_CART.GBL?pslnkid=Z_HC_SSR_SSENRL_CART_LNK&FolderPath=PORTAL_ROOT_OBJECT.Z_SIS_MENU.Z_ENROLLMENT.Z_HC_SSR_SSENRL_CART_LNK&IsFolder=false&IgnoreParamTempl=FolderPath%2cIsFolder&PortalActualURL=https%3a%2f%2fsis-main.hku.hk%2fpsc%2fsisprod%2fEMPLOYEE%2fPSFT_CS%2fc%2fSA_LEARNER_SERVICES.SSR_SSENRL_CART.GBL%3fpslnkid%3dZ_HC_SSR_SSENRL_CART_LNK&PortalContentURL=https%3a%2f%2fsis-main.hku.hk%2fpsc%2fsisprod%2fEMPLOYEE%2fPSFT_CS%2fc%2fSA_LEARNER_SERVICES.SSR_SSENRL_CART.GBL%3fpslnkid%3dZ_HC_SSR_SSENRL_CART_LNK&PortalContentProvider=PSFT_CS&PortalCRefLabel=Enrollment%20Add%20Classes&PortalRegistryName=EMPLOYEE&PortalServletURI=https%3a%2f%2fsis-eportal.hku.hk%2fpsp%2fptlprod%2f&PortalURI=https%3a%2f%2fsis-eportal.hku.hk%2fpsc%2fptlprod%2f&PortalHostNode=EMPL&NoCrumbs=yes&PortalKeyStruct=yes");
                webView.setVisibility(View.VISIBLE);
                setTopbar(true);
            }
        });
        ((Button) view.findViewById(R.id.status_button)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                webView.loadUrl("https://sis-main.hku.hk/psc/sisprod/EMPLOYEE/PSFT_CS/c/SA_LEARNER_SERVICES.SSS_STUDENT_CENTER.GBL?pslnkid=Z_ENROLLMENT_STATUS_LNK&FolderPath=PORTAL_ROOT_OBJECT.Z_SIS_MENU.Z_ENROLLMENT.Z_ENROLLMENT_STATUS_LNK&IsFolder=false&IgnoreParamTempl=FolderPath%2cIsFolder&PortalActualURL=https%3a%2f%2fsis-main.hku.hk%2fpsc%2fsisprod%2fEMPLOYEE%2fPSFT_CS%2fc%2fSA_LEARNER_SERVICES.SSS_STUDENT_CENTER.GBL%3fpslnkid%3dZ_ENROLLMENT_STATUS_LNK&PortalContentURL=https%3a%2f%2fsis-main.hku.hk%2fpsc%2fsisprod%2fEMPLOYEE%2fPSFT_CS%2fc%2fSA_LEARNER_SERVICES.SSS_STUDENT_CENTER.GBL%3fpslnkid%3dZ_ENROLLMENT_STATUS_LNK&PortalContentProvider=PSFT_CS&PortalCRefLabel=Enrollment%20Status&PortalRegistryName=EMPLOYEE&PortalServletURI=https%3a%2f%2fsis-eportal.hku.hk%2fpsp%2fptlprod%2f&PortalURI=https%3a%2f%2fsis-eportal.hku.hk%2fpsc%2fptlprod%2f&PortalHostNode=EMPL&NoCrumbs=yes&PortalKeyStruct=yes");
                webView.setVisibility(View.VISIBLE);
                setTopbar(true);
            }
        });
        ((Button) view.findViewById(R.id.transcript_button)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                webView.loadUrl("https://sis-main.hku.hk/psc/sisprod/EMPLOYEE/PSFT_CS/c/Z_SS_MENU.Z_TSRPT_WEB_STDT.GBL?FolderPath=PORTAL_ROOT_OBJECT.Z_SIS_MENU.Z_ACADEMIC_RECORDS.Z_TSRPT_WEB_STDT_GBL&IsFolder=false&IgnoreParamTempl=FolderPath%2cIsFolder&PortalActualURL=https%3a%2f%2fsis-main.hku.hk%2fpsc%2fsisprod%2fEMPLOYEE%2fPSFT_CS%2fc%2fZ_SS_MENU.Z_TSRPT_WEB_STDT.GBL&PortalContentURL=https%3a%2f%2fsis-main.hku.hk%2fpsc%2fsisprod%2fEMPLOYEE%2fPSFT_CS%2fc%2fZ_SS_MENU.Z_TSRPT_WEB_STDT.GBL&PortalContentProvider=PSFT_CS&PortalCRefLabel=Transcript%20(Student%20Copy)&PortalRegistryName=EMPLOYEE&PortalServletURI=https%3a%2f%2fsis-eportal.hku.hk%2fpsp%2fptlprod%2f&PortalURI=https%3a%2f%2fsis-eportal.hku.hk%2fpsc%2fptlprod%2f&PortalHostNode=EMPL&NoCrumbs=yes&PortalKeyStruct=yes");
                webView.setVisibility(View.VISIBLE);
                setTopbar(true);
            }
        });
        ((Button) view.findViewById(R.id.invoice_button)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                webView.loadUrl("https://sis-main.hku.hk/psc/sisprod/EMPLOYEE/PSFT_CS/c/SA_LEARNER_SERVICES.SSF_SS_CHRGS_DUE.GBL?1&FolderPath=PORTAL_ROOT_OBJECT.Z_SIS_MENU.Z_FINANCIAL_SERVICES.Z_MY_INVOICE&IsFolder=false&IgnoreParamTempl=FolderPath%2cIsFolder&PortalActualURL=https%3a%2f%2fsis-main.hku.hk%2fpsc%2fsisprod%2fEMPLOYEE%2fPSFT_CS%2fc%2fSA_LEARNER_SERVICES.SSF_SS_CHRGS_DUE.GBL%3f1&PortalContentURL=https%3a%2f%2fsis-main.hku.hk%2fpsc%2fsisprod%2fEMPLOYEE%2fPSFT_CS%2fc%2fSA_LEARNER_SERVICES.SSF_SS_CHRGS_DUE.GBL%3f1&PortalContentProvider=PSFT_CS&PortalCRefLabel=My%20Invoice&PortalRegistryName=EMPLOYEE&PortalServletURI=https%3a%2f%2fsis-eportal.hku.hk%2fpsp%2fptlprod%2f&PortalURI=https%3a%2f%2fsis-eportal.hku.hk%2fpsc%2fptlprod%2f&PortalHostNode=EMPL&NoCrumbs=yes&PortalKeyStruct=yes");
                webView.setVisibility(View.VISIBLE);
                setTopbar(true);
            }
        });
        ((Button) view.findViewById(R.id.uhs_button)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                webView.loadUrl("https://www.uhs.hku.hk/");
                webView.setVisibility(View.VISIBLE);
                setTopbar(true);
            }
        });
        ((Button) view.findViewById(R.id.uhs_booking_button)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                webView.loadUrl("https://uhs4.hku.hk:8443/CMS3/webBooking/main");
                webView.setVisibility(View.VISIBLE);
                setTopbar(true);
            }
        });
        ((Button) view.findViewById(R.id.main_page_button)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                webView.loadUrl("https://sis-eportal.hku.hk/psp/ptlprod/EMPLOYEE/EMPL/h/?tab=DEFAULT");
                webView.setVisibility(View.VISIBLE);
                setTopbar(true);
            }
        });

        webView.setVisibility(View.GONE);
        return view;
    }
}