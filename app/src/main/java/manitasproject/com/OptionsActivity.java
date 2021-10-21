package manitasproject.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class OptionsActivity extends AppCompatActivity {

    private TextView nombreUsuario;
    private EditText correoUsuario;
    private EditText contrasenaUsuario;
    private EditText edadUsuario;
    private EditText telefonoUsuario;
    private EditText sexoUsuario;

    private EditText contrasenaActual;
    private EditText contrasenaNueva;
    private EditText confContrasenaNueva;

    private Button btnGuardar;
    private Button btnCancelar;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        nombreUsuario = (TextView) findViewById(R.id.textNombreUsuario);
        correoUsuario = (EditText) findViewById(R.id.textCorreo);
        edadUsuario = (EditText) findViewById(R.id.textEdad);
        telefonoUsuario = (EditText) findViewById(R.id.textTelefono);
        sexoUsuario = (EditText) findViewById(R.id.textSexo);

        btnGuardar = (Button) findViewById(R.id.btn_guardar);
        btnCancelar = (Button) findViewById(R.id.btn_cancelar);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OptionsActivity.this, MenuActivity.class);
                startActivity(i);
            }
        });
    }
}