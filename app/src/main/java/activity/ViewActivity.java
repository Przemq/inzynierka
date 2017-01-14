package activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.inz.przemek.dijkstra.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import utils.IOHelper;
import view.ViewResolver;

/**
 * Created by Przemek on 12.10.2016.
 */
public class ViewActivity extends Activity {
    private ViewResolver viewResolver;
    private TextView tv_floor;
    private int floor = 1;
    private HashMap<Integer,String> graphicsMap;
    private int numberOfFloor = 0;

    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        final Button button_up = (Button) findViewById(R.id.button_up);
        final Button button_down = (Button) findViewById(R.id.button_down);
        Button button_source = (Button) findViewById(R.id.source_button);
        Button button_destination = (Button) findViewById(R.id.destination_button);
        Button button_show_all = (Button) findViewById(R.id.button_show_all);
        if (floor == 1) button_down.setBackgroundDrawable(getResources().getDrawable(R.mipmap.ic_floor_down_grey));

        graphicsMap = new HashMap<>();

        try {
            JSONObject json = new JSONObject( IOHelper.readJSONFromFIle("configuration"));
            JSONObject metaData = json.getJSONObject("metaData");
            JSONArray graphicsNames =  metaData.getJSONArray("floorsImages");
            numberOfFloor = metaData.getInt("numberOfFloor");

            for (int i = 0; i < graphicsNames.length(); i++){
                graphicsMap.put(i+1,graphicsNames.getString(i));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        tv_floor = (TextView)findViewById(R.id.tv_floor);
        tv_floor.setText(String.valueOf(floor));
        viewResolver = (ViewResolver) findViewById(R.id.drawView);
        layout = (LinearLayout)findViewById(R.id.viewLayout);
        layout.setBackgroundDrawable(setBackgroundFromSD(graphicsMap.get(1)));


        final ArrayAdapter<String> pointsAdapter = new ArrayAdapter<>(ViewActivity.this, android.R.layout.select_dialog_singlechoice,viewResolver.getPointsId());


        button_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floor++;
                if(floor >= numberOfFloor){
                    v.setBackgroundDrawable(getResources().getDrawable(R.mipmap.ic_floor_up_grey));
                    floor = numberOfFloor;
                }
                else
                    button_down.setBackgroundDrawable(getResources().getDrawable(R.mipmap.ic_floor_down));
                viewResolver.setFloor(viewResolver.getFloor() + 1);
                tv_floor.setText(String.valueOf(viewResolver.getFloor()));
                layout.setBackgroundDrawable(setBackgroundFromSD(graphicsMap.get(floor)));
                viewResolver.invalidate();
            }
        });

        button_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floor--;
                if(floor <=1){
                    floor =1;
                    v.setBackgroundDrawable(getResources().getDrawable(R.mipmap.ic_floor_down_grey));
                    button_up.setBackgroundDrawable(getResources().getDrawable(R.mipmap.ic_floor_up));
                }
                else
                    v.setBackgroundDrawable(getResources().getDrawable(R.mipmap.ic_floor_down));
                viewResolver.setFloor(viewResolver.getFloor() - 1);
                tv_floor.setText(String.valueOf(viewResolver.getFloor()));
                layout.setBackgroundDrawable(setBackgroundFromSD(graphicsMap.get(floor)));
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
                //System.out.println(pos);
                viewResolver.invalidate();


                viewResolver.setSource(pos);

            }
        });
        builderSingle.show();
    }

    public void showUpPrompt(){
        YoYo.with(Techniques.Pulse)
                .duration(1500)
                .playOn(findViewById(R.id.button_up));
    }

    public void showDownPrompt(){
        YoYo.with(Techniques.Pulse)
                .duration(1500)
                .playOn(findViewById(R.id.button_down));
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

    private BitmapDrawable setBackgroundFromSD(String fileName){
        String pathName =  Environment.getExternalStorageDirectory().getPath() + "/FindYourWay/" + fileName ;
        Resources res = getResources();
        Bitmap bitmap = BitmapFactory.decodeFile(pathName);
        BitmapDrawable bd = new BitmapDrawable(res, bitmap);
        return bd;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(event.getY() < viewResolver.getHeight())
                viewResolver.detectTouchPoint(event.getX(),event.getY() - viewResolver.getY());
        }
        return super.onTouchEvent(event);

    }
}