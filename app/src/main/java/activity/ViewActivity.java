package activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inz.przemek.dijkstra.R;

import java.util.Objects;

import domain.Point;
import view.ViewResolver;

/**
 * Created by Przemek on 12.10.2016.
 */
public class ViewActivity extends Activity {
    private ViewResolver viewResolver;
    private TextView tv_floor;
    private int floor = 1;
    LinearLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Button button_up = (Button) findViewById(R.id.button_up);
        Button button_down = (Button) findViewById(R.id.button_down);
        Button button_source = (Button) findViewById(R.id.source_button);
        Button button_destination = (Button) findViewById(R.id.destination_button);
        Button button_show_all = (Button) findViewById(R.id.button_show_all);
        Intent i = getIntent();

        tv_floor = (TextView)findViewById(R.id.tv_floor);
        tv_floor.setText(String.valueOf(floor));
        viewResolver = (ViewResolver) findViewById(R.id.drawView);
        layout = (LinearLayout)findViewById(R.id.viewLayout);
        layout.setBackgroundResource(R.drawable.pietro1);
        viewResolver.setDataJSON(i.getStringExtra("data"));

        // trzeba dodać getExtras dla grafiki i domyślnie wczytać pierwszą

        final ArrayAdapter<String> pointsAdapter = new ArrayAdapter<String>(ViewActivity.this, android.R.layout.select_dialog_singlechoice,viewResolver.getPointsId());

        button_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floor++;
                viewResolver.setFloor(viewResolver.getFloor() + 1);
                tv_floor.setText(String.valueOf(viewResolver.getFloor()));
                setBackground();
                viewResolver.invalidate();
            }
        });

        button_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewResolver.setFloor(viewResolver.getFloor() - 1);
                tv_floor.setText(String.valueOf(viewResolver.getFloor()));
                setBackground();
                viewResolver.invalidate();
            }
        });

        button_source.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSourceList(pointsAdapter,"Select Source");
                viewResolver.invalidate();

            }

        });
        button_destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDestinationList(pointsAdapter,"Select Destination");
                viewResolver.invalidate();
            }
        });

        button_show_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewResolver.setShowAllConnections(!viewResolver.isShowAllConnections());
                viewResolver.invalidate();
            }
        });

    }

    public void showSourceList(final ArrayAdapter<String> adapter, String header){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(ViewActivity.this);
        builderSingle.setIcon(R.drawable.ic_action_name);
        builderSingle.setTitle(header);

        builderSingle.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builderSingle.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                viewResolver.invalidate();
                String pos = adapter.getItem(which);
                System.out.println(pos);
                viewResolver.invalidate();


                viewResolver.setSource(pos);

            }
        });
        builderSingle.show();
    }

    public void showDestinationList(final ArrayAdapter<String> adapter, String header){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(ViewActivity.this);
        builderSingle.setIcon(R.drawable.ic_action_name);
        builderSingle.setTitle(header);

        builderSingle.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builderSingle.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                viewResolver.invalidate();
                String pos = adapter.getItem(which);
                viewResolver.setDestination(pos);
                System.out.println(pos);
                viewResolver.invalidate();

            }
        });
        builderSingle.show();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            //System.out.println(event.getX()+" : "+event.getY());
            if(event.getY() < viewResolver.getHeight())
                viewResolver.detectTouchedPoint(event.getX(),event.getY() - viewResolver.getY());
        }

        return super.onTouchEvent(event);

    }
    public void setBackground(){

        if(viewResolver.getFloor() == 1) {
            layout.setBackgroundResource(R.drawable.pietro1);
            layout.invalidate();
        }
        else layout.setBackgroundResource(R.drawable.pietro2);
        layout.invalidate();
    }



}