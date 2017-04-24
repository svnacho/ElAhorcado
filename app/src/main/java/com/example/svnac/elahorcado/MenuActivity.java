package com.example.svnac.elahorcado;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    boolean sonido=true;
    boolean admin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        RelativeLayout rl = (RelativeLayout)findViewById(R.id.activity_menu);
        final TextView tv_fingers = (TextView)findViewById(R.id.tv_fingers);
        Button bt_comenzar_partida = (Button)findViewById(R.id.bt_comenzar_part);
        Button bt_creditos = (Button)findViewById(R.id.bt_creditos);
        Button bt_salir = (Button)findViewById(R.id.bt_salir);
        Button bt_comojugar = (Button)findViewById(R.id.bt_comojugar);
        final ImageButton ib_mute = (ImageButton)findViewById(R.id.ib_mute);
        final ImageButton ib_sound = (ImageButton)findViewById(R.id.ib_sound);

        bt_comenzar_partida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_partida = new Intent (MenuActivity.this, GameActivity.class);
                startActivity(intent_partida);//Llamar a la actividad de la partida
            }
        });

        bt_comojugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_comojugar = new Intent (MenuActivity.this, ComoJugarActivity.class);
                startActivity(intent_comojugar);//Llamar a la actividad de la ayuda
            }
        });

        bt_creditos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_creditos = new Intent (MenuActivity.this, CreditosActivity.class);
                startActivity(intent_creditos);//Llamar a la actividad de los creditos
            }
        });

        bt_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); //sale del menú
            }
        });

        ib_mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sonido){
                    // deshabilitar sonido,
                    ib_mute.setVisibility(View.INVISIBLE);
                    ib_sound.setVisibility(View.VISIBLE);
                    //cambiar imagen por la de "habilitar sonido"
                    sonido=false;
                }else{
                    // toast: ya están desactivados los sonidos
                }
            }
        });

        ib_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sonido){
                    // toast: ya están activados los sonidos
                }else{
                    //habilitar sonido,
                    ib_mute.setVisibility(View.VISIBLE);
                    ib_sound.setVisibility(View.INVISIBLE);
                    //cambiar imagen por la de "habilitar sonido"
                    sonido=true;
                }
            }
        });

        //Listener de la entrada admin
        rl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int fingers = event.getPointerCount();

                tv_fingers.setText(Integer.toString(fingers));

                if(fingers >= 3) {
                    Context context = getApplicationContext();
                    Toast msg_admin_on = Toast.makeText(context, "Admin detectado", Toast.LENGTH_SHORT);
                    msg_admin_on.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    msg_admin_on.show();

                    admin = true;
                    return true;
                }

                return false;
            }
        });
    }
}
