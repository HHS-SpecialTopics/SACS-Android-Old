package us.in.k12.sacs.sacsandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.onesignal.OneSignal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OneSignal.startInit(this).init();

        setContentView(R.layout.activity_splash);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                downloadImage();
            }
        });
        t.start();
        while (t.isAlive());

        if(checkInternetConnection()){

            Intent i = new Intent(this, WebViewActivity.class);
            startActivity(i);
            finish();
        }
    }

    private void downloadImage(){
        try {
            File cDir = getApplication().getExternalFilesDir(null);
            File cacheDir = new File(cDir.getPath() + "/cache");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            File file = new File(cDir.getPath() + "/cache/splashscreen.png");
            if (file.exists()) {
                InputStream is = file.toURI().toURL().openConnection().getInputStream();
                Bitmap bmp = BitmapFactory.decodeStream(is);
                ImageView view = (ImageView) findViewById(R.id.sacsLogoSplash);
                view.setImageBitmap(bmp);
                is.close();
            }

            InputStream is = new URL("https://sacs.school/wp-content/uploads/2016/12/SplashScreen.png").openConnection().getInputStream();
            if (is != null) {
                FileOutputStream os = new FileOutputStream(file);

                byte[] buffer = new byte[4 * 1024];
                int read;

                while ((read = is.read(buffer)) != -1) {
                    os.write(buffer, 0, read);
                }
                os.flush();

                os.close();
                is.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private boolean checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (null == ni) {
            Toast.makeText(this, "No internet connection found!", Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }
    }
}
