package activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;

import com.inz.przemek.dijkstra.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Przemek on 11.10.2016.
 */

public class SplashActivity extends Activity {

    Intent toMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        toMainActivity = new Intent(this,MainActivity.class);
        toMainActivity.putExtra("data",readJSONFromFIle("testowyJSON"));
        startActivity(toMainActivity);

      /*  if(isNetworkAvailable()){
            new ServerRequest(ServiceType.GET_DATABASE, new Parameters()).setServerRequestListener(new ServerRequest.ServerRequestListener() {
                @Override
                public void onSuccess(String json) {
                    System.out.println(json);
                    startActivity(toMainActivity);
                }

                @Override
                public void onError(int code, String description) {

                }

            }).execute();
        }*/
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public String readJSONFromFIle(String fileName) {
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root,fileName + ".txt");
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return  text.toString();
    }
}
