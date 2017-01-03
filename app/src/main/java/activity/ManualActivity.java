package activity;

import android.app.Activity;
import android.os.Bundle;

import com.inz.przemek.dijkstra.R;

/**
 * Created by PrzemekMadzia on 2017-01-03.
 */

public class ManualActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_activity);
    }
}
