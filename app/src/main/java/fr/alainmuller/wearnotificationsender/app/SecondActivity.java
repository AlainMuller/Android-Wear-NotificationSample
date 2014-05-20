package fr.alainmuller.wearnotificationsender.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends Activity {

    public static final String SAVE = "SAVE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView tvLog = (TextView) findViewById(R.id.tv_message);

        Bundle extras = getIntent().getExtras();
        String message = getString(R.string.default_message);
        if (extras != null) {
            String myParam = extras.getString(getString(R.string.param_key));
            if (SAVE.equals(myParam))
                message = getString(R.string.success_message);
        }
        tvLog.setText(message);
    }

}
