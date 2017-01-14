package activity;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;
import com.inz.przemek.dijkstra.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import enums.ServiceType;
import server.Parameters;
import server.ServerRequest;


/**
 * Created by Przemek on 11.10.2016.
 */

public class SplashActivity extends Activity {

    private Intent toMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        toMainActivity = new Intent(this,MainActivity.class);

        if(isNetworkAvailable()){
            new ServerRequest(ServiceType.GET_CONFIGURATION, new Parameters()).setServerRequestListener(new ServerRequest.ServerRequestListener() {
                @Override
                public void onSuccess(String json) {
                    writeToSDFile("configuration",json);
                    try {
                        JSONObject receivedData = new JSONObject(json);
                        JSONObject metaData = receivedData.getJSONObject("metaData");
                        JSONArray floorsImages = metaData.getJSONArray("floorsImages");
                        for(int i = 0; i < floorsImages.length(); i ++)
                        {
                            File testFile = new File(Environment.getExternalStorageDirectory().getPath() + "/FindYourWay/" + floorsImages.get(i));
                            if(!testFile.exists()) {
                                downloadFile(ServiceType.getURL(ServiceType.DOWNLOAD_FILE) + floorsImages.get(i));
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startActivity(toMainActivity);
                }

                @Override
                public void onError(int code, String description) {

                }

            }).execute();
        }
        else {
            toMainActivity.putExtra("noNetwork","No network. The previous configuration is loaded");
            startActivity(toMainActivity);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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

    public void downloadFile(String uRl) {

        DownloadManager mgr = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri downloadUri = Uri.parse(uRl);
        DownloadManager.Request request = new DownloadManager.Request(
                downloadUri);

        request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI
                        | DownloadManager.Request.NETWORK_MOBILE)
                .setDestinationInExternalPublicDir("/FindYourWay", downloadUri.getLastPathSegment());

        mgr.enqueue(request);

    }

}
