package activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.inz.przemek.dijkstra.R;

import view.ViewResolver;

/**
 * Created by Przemek on 12.10.2016.
 */
public class ViewActivity extends Activity {
    private ViewResolver viewResolver;
    private Button button_up;
    private Button button_down;
    private TextView tv_floor;
    private int floor = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        button_up = (Button)findViewById(R.id.button_up);
        button_down = (Button)findViewById(R.id.button_down);
        viewResolver = (ViewResolver) findViewById(R.id.drawView);
        tv_floor = (TextView)findViewById(R.id.tv_floor);
        tv_floor.setText(String.valueOf(floor));
        final ListView source = (ListView) findViewById(R.id.source);
        final ListView destination = (ListView) findViewById(R.id.destination);

        final ArrayAdapter sourceAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ViewResolver.getPointsId());
        source.setAdapter(sourceAdapter);

        final ArrayAdapter destinationAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ViewResolver.getPointsId());
        destination.setAdapter(destinationAdapter);

        source.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewResolver.setSource(position);
                System.out.println("Position: " + position + "id: " + id);
                sourceAdapter.notifyDataSetChanged();
            }
        });

        destination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               viewResolver.setDestination(position);
                destinationAdapter.notifyDataSetChanged();
            }
        });

        button_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floor++;
                if(floor > 3) floor =3;
               viewResolver.setFloor(floor);
                tv_floor.setText(String.valueOf(floor));
                viewResolver.invalidate();
            }
        });

        button_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floor--;
                if(floor < 1) floor =1;
                viewResolver.setFloor(floor);
                tv_floor.setText(String.valueOf(floor));
                viewResolver.invalidate();
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            System.out.println(event.getX()+" : "+event.getY());
            if(event.getY() < viewResolver.getHeight())
            viewResolver.detectTouchedPoint(event.getX(),event.getY() - viewResolver.getY());
        }
        return super.onTouchEvent(event);
    }


}
