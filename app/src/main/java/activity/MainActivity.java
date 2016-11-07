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


    Intent toTestActivity;

    FloatingActionButton bt1;
    FloatingActionButton bt2;
    FloatingActionButton bt3;
    FloatingActionButton bt4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt1 = (FloatingActionButton)findViewById(R.id.fab1);
        bt2 = (FloatingActionButton)findViewById(R.id.fab2);
        bt3 = (FloatingActionButton)findViewById(R.id.fab3);
        bt4 = (FloatingActionButton)findViewById(R.id.fab4);

        toTestActivity = new Intent(MainActivity.this, ViewActivity.class);

        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("1");
                startActivity(toTestActivity);
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("2");

            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("3");

            }
        });

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("1");

            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
            if(keyCode == KeyEvent.KEYCODE_BACK)
            {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
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
        return super.onKeyDown(keyCode, event);
    }
}
