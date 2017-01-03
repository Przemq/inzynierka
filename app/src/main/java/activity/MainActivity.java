package activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.KeyEvent;
import android.view.View;

import com.inz.przemek.dijkstra.R;

/**
 * Created by Przemek on 11.10.2016.
 */
public class MainActivity extends Activity {


    private Intent toViewActivity;


    private FloatingActionButton buttonExit;
    private FloatingActionButton buttonAbout;
    private FloatingActionButton buttonManual;
    private FloatingActionButton viewResolerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonExit = (FloatingActionButton) findViewById(R.id.fab1);
        buttonAbout = (FloatingActionButton) findViewById(R.id.fab2);
        buttonManual = (FloatingActionButton) findViewById(R.id.fab3);
        viewResolerButton = (FloatingActionButton) findViewById(R.id.fab4);
        toViewActivity = new Intent(MainActivity.this, ViewActivity.class);
        final Intent toAboutActivity = new Intent(MainActivity.this, AboutActivity.class);
        final Intent toManualActivity = new Intent(MainActivity.this, ManualActivity.class);

        viewResolerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(toViewActivity);
            }
        });

        buttonAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(toAboutActivity);
            }
        });

        buttonManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(toManualActivity);

            }
        });

        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               closeApp();
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            closeApp();
        }

        return super.onKeyDown(keyCode, event);
    }

    private void closeApp() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        android.os.Process.killProcess(android.os.Process.myPid());
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Exit, are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }


}
