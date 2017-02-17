package us.in.k12.sacs.sacsandroid;

import android.content.Intent;
import android.media.audiofx.BassBoost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.logging.Level;
import java.util.logging.Logger;

public class WebViewActivity extends AppCompatActivity {

    WebView web;
    boolean exitFlag = false;
    final Logger LOGGER = Logger.getLogger("SACS-WebView");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        web = (WebView) findViewById(R.id.web_viewer);
        web.loadUrl("http://sacs.school");
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                exitFlag = false;

                if(url.contentEquals("sacs://settings")){
                    // TODO
                    Toast.makeText(WebViewActivity.this, "Not available yet.", Toast.LENGTH_SHORT).show();

                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        if(web.canGoBack()) {
            web.goBack();
        }else{
            if(!exitFlag){
                exitFlag = true;
                Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_LONG).show();
            }else{
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.settings:
                // TODO
                Toast.makeText(this, "Not available yet.", Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
