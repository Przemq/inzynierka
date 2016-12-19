package activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.inz.przemek.dijkstra.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import domain.Point;
import enums.ServiceType;
import server.Parameters;
import server.ServerRequest;

import static android.content.ContentValues.TAG;

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

        if(isNetworkAvailable()){
            new ServerRequest(ServiceType.GET, new Parameters()).setServerRequestListener(new ServerRequest.ServerRequestListener() {
                @Override
                public void onSuccess(String json) {
                  writeToSDFile("testowyJSON",json);
                    startActivity(toMainActivity);
                }

                @Override
                public void onError(int code, String description) {

                }

            }).execute();
        }
        else {
            Toast.makeText(this, "No network, check your connection", Toast.LENGTH_LONG).show();
        }
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

    private void writeToSDFile(String fileName, String content){
        File root = android.os.Environment.getExternalStorageDirectory();
        File file = new File(root, fileName + ".txt");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(fos);
            pw.println(content);
            pw.flush();
            pw.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
