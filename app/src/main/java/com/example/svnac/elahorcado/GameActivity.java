package com.example.svnac.elahorcado;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    ArrayList<String> palabras = new ArrayList<>();
    String letra;
    String mi_palabra;
    String arteriscos = "";
    int fallos = 0;
    boolean perdido = false, ganado = false;

    private void leerFicheroRecurso () throws IOException {
        InputStream fraw = getResources().openRawResource(R.raw.palabras);
        BufferedReader fin = new BufferedReader(new InputStreamReader(fraw));

        // Leer hasta fin de fichero
        String s;
        while ((s = fin.readLine())!=null){
            palabras.add(s);
        }

        fraw.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final ImageView ahorcado = (ImageView) findViewById(R.id.ahorcado0);
        final TextView tv_palabra = (TextView) findViewById(R.id.tv_palabra);
        final TextView tv_palabra_debug = (TextView) findViewById(R.id.tv_palabra_debug);
        Button bt_probar = (Button) findViewById(R.id.bt_probar);
        final EditText et_letra = (EditText) findViewById(R.id.et_letra);

        try {
            leerFicheroRecurso();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int n = palabras.size();    //calcula el número de elementos en el diccionario
        Random rand = new Random();
        int nr = rand.nextInt(n - 1);     //crea un número aleatorio entre 0 y el número de palabras

        mi_palabra = palabras.get(nr);   //guarda la palabra del diccionario que esté en la posición aleatoria
        int long_mi_palabra = mi_palabra.length();  //y calcula su longitud

        for (int i = 0; i < long_mi_palabra; i++) {   //pone arteriscos en el String arteriscos
            arteriscos += "*";
        }

        tv_palabra.setText(arteriscos); //pinta los arteriscos
        tv_palabra_debug.setText(mi_palabra);   //en debug: pinta la palabra descubierta debajo

        bt_probar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                letra = et_letra.getText().toString(); //guarda la letra introducida como un String
                letra = letra.toLowerCase();

                int i = mi_palabra.indexOf(letra.charAt(0));
                if(perdido){ //si ya había perdido
                    Context context = getApplicationContext();  //toast: ¡ya has perdido!
                    Toast msg_ya_perdido = Toast.makeText(context, "¡Ya has perdido!\nBuen intento", Toast.LENGTH_SHORT);
                    msg_ya_perdido.setGravity(Gravity.CENTER_VERTICAL, 0, 50);
                    msg_ya_perdido.show();

                } else if(!ganado) { // no tiene la partida perdida
                    if(i >= 0){ //no ha perdido ya y la letra es correcta
                        while(i >= 0) {
                            mi_palabra = mi_palabra.substring(0, i) + " " + mi_palabra.substring(i + 1);  //actualiza String mi_palabra eliminando
                            tv_palabra_debug.setText("Encontrada la \"" + letra + "\" en la pos. " + Integer.toString(i));
                            arteriscos = arteriscos.substring(0, i) + letra + arteriscos.substring(i + 1); //actualiza String arteriscos descubriendo
                            tv_palabra.setText(arteriscos);

                            i = mi_palabra.indexOf(letra.charAt(0));
                        }

                        if(arteriscos.indexOf("*") < 0) { //detectamos si gana justo en este turno
                            ganado = true;

                            Context context = getApplicationContext();  //toast: ¡has perdido!
                            Toast msg_has_ganado = Toast.makeText(context, "¡Has ganado!\n Enhorabuena", Toast.LENGTH_SHORT);
                            msg_has_ganado.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                            msg_has_ganado.show();
                        }

                    } else { //no ha perdido y la letra no es correcta
                        tv_palabra_debug.setText("no encontrada la \"" + letra + "\"");
                        fallos++;

                        int id;
                        switch (fallos){
                            case 0:
                                id = getResources().getIdentifier("ahorcado_0", "mipmap", getPackageName());
                                break;
                            case 1:
                                id = getResources().getIdentifier("ahorcado_1", "mipmap", getPackageName());
                                break;
                            case 2:
                                id = getResources().getIdentifier("ahorcado_2", "mipmap", getPackageName());
                                break;
                            case 3:
                                id = getResources().getIdentifier("ahorcado_3", "mipmap", getPackageName());
                                break;
                            case 4:
                                id = getResources().getIdentifier("ahorcado_4", "mipmap", getPackageName());
                                break;
                            case 5:
                                id = getResources().getIdentifier("ahorcado_5", "mipmap", getPackageName());
                                break;
                            case 6:
                                id = getResources().getIdentifier("ahorcado_6", "mipmap", getPackageName());
                                break;
                            case 7:
                                id = getResources().getIdentifier("ahorcado_completo", "mipmap", getPackageName());
                                tv_palabra_debug.setText("¡Has perdido!");

                                Context context = getApplicationContext();  //toast: ¡ya has perdido!
                                Toast msg_has_perdido = Toast.makeText(context, "¡Has perdido!\n Lo sentimos", Toast.LENGTH_SHORT);
                                msg_has_perdido.setGravity(Gravity.CENTER_VERTICAL, 0, 50);
                                msg_has_perdido.show();

                                perdido = true;
                                break;
                            default:
                                id = getResources().getIdentifier("ahorcado_0", "mipmap", getPackageName());
                                break;
                        }
                        ahorcado.setImageResource(id);
                    }
                }else{ //caso restante: no ha perdido pero ya tiene la partida ganada
                    Context context = getApplicationContext();  //toast: ¡ya has perdido!
                    Toast msg_ya_ganado = Toast.makeText(context, "¡Ya has ganado!", Toast.LENGTH_SHORT);
                    msg_ya_ganado.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    msg_ya_ganado.show();
                }
            }
        });
    }
}
