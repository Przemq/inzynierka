package activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.inz.przemek.dijkstra.R;

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
        startActivity(toMainActivity);

       /* if(isNetworkAvailable()){
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
}
