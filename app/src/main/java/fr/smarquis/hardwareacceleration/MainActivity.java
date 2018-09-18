package fr.smarquis.hardwareacceleration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/**
 * 开启硬件加速和不开硬件加速的差异，用alcate手机测试出来的效果为 开启的时候 1毫秒即可完成 1 frame 不开的时候 6 ms。
 */
public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.btn);


        Button btn_2 = (Button) findViewById(R.id.btn_1);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_1 = new Intent();
                intent_1.setClass(getApplicationContext(), HardwareAcceleratedActivity.class);

                startActivity(intent_1);
            }
        });


        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent_1 = new Intent();
                intent_1.setClass(getApplicationContext(), NotHardwareAcceleratedActivity.class);

                startActivity(intent_1);
            }
        });


    }
}
