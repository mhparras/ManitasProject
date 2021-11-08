package manitasproject.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    Button btn_learning,btn_games,btn_options;
    private TextView textCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btn_learning= findViewById(R.id.btn_aprendizaje);
        btn_games= findViewById(R.id.btn_juegos);
        btn_options= findViewById(R.id.btn_options);

        textCerrarSesion = (TextView) findViewById(R.id.txt_cerrarSesion);

        textCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                Toast.makeText(MenuActivity.this, "Se ha cerrado la sesión.", Toast.LENGTH_LONG).show();

                cerrarSesion(); //Llamar a la funcion para cerrar sesion
                finish(); //finalizar la actividad
            }
        });
    }

    public void options(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.btn_aprendizaje:
                //code
                intent=new Intent(MenuActivity.this,LearnActivity.class);
                break;
            case R.id.btn_juegos:
                //code
                intent=new Intent(MenuActivity.this, GamesActivity.class);
                break;
            case R.id.btn_options:
                //code
                intent=new Intent(MenuActivity.this, OptionsActivity.class);
                break;
        }
        startActivity(intent);
    }

    public void cerrarSesion(){
        SharedPreferences pref = getSharedPreferences("SESION_BOTON", MODE_PRIVATE);
        pref.edit().clear().apply();
    }
}