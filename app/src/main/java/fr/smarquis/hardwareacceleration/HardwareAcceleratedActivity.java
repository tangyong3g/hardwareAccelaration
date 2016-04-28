package fr.smarquis.hardwareacceleration;

import android.app.Activity;
import android.os.Bundle;

public class HardwareAcceleratedActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CustomView(this));
    }

}
