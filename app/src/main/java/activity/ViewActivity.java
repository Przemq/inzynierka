package activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.inz.przemek.dijkstra.R;

import view.DijkstraAlgorithmView;

/**
 * Created by Przemek on 12.10.2016.
 */
public class ViewActivity extends Activity {
    private DijkstraAlgorithmView drawView;
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
        drawView = (DijkstraAlgorithmView) findViewById(R.id.drawView);
        tv_floor = (TextView)findViewById(R.id.tv_floor);
        tv_floor.setText(String.valueOf(floor));


        button_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floor++;
                if(floor > 3) floor =3;
               drawView.setFloor(floor);
                tv_floor.setText(String.valueOf(floor));
                drawView.invalidate();
            }
        });

        button_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floor--;
                if(floor < 1) floor =1;
                drawView.setFloor(floor);
                tv_floor.setText(String.valueOf(floor));
                drawView.invalidate();
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            System.out.println(event.getX()+" : "+event.getY());
            if(event.getY() < drawView.getHeight())
            drawView.detectTouchedPoint(event.getX(),event.getY() - drawView.getY());
        }
        return super.onTouchEvent(event);
    }


}
