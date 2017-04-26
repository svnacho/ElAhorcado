package com.example.svnac.elahorcado;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvPulsa;
    private RelativeLayout rl;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPulsa = (TextView) findViewById(R.id.tvPulsa);
        rl = (RelativeLayout) findViewById(R.id.activity_main);
        animation = AnimationUtils.loadAnimation(this, R.anim.fadeinout);

        tvPulsa.startAnimation(animation);

        rl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);

                return true;
            }
        });
    }
}
