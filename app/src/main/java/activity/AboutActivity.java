package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.inz.przemek.dijkstra.R;

/**
 * Created by PrzemekMadzia on 2017-01-03.
 */

public class AboutActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
        final Intent toMainActivity = new Intent(AboutActivity.this,MainActivity.class);
        Button button_back = (Button)findViewById(R.id.button_about_back);

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(toMainActivity);
            }
        });

    }
}