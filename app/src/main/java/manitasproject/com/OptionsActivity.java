package manitasproject.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OptionsActivity extends AppCompatActivity implements View.OnClickListener{

    private String idUsuario;

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

    public void cargarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("idUsuario", Context.MODE_PRIVATE);
        idUsuario = preferences.getString("id","nulo");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        cargarPreferencias();

        //Conexión con Firebase
        firebaseAuth = FirebaseAuth.getInstance();

        nombreUsuario = (TextView) findViewById(R.id.textNombreUsuario);
        correoUsuario = (EditText) findViewById(R.id.textCorreo);
        edadUsuario = (EditText) findViewById(R.id.textEdad);
        telefonoUsuario = (EditText) findViewById(R.id.textTelefono);
        sexoUsuario = (EditText) findViewById(R.id.textSexo);
        contrasenaActual = (EditText) findViewById(R.id.textContrasenaActual);
        contrasenaNueva = (EditText) findViewById(R.id.textContrasenaNueva);
        confContrasenaNueva = (EditText) findViewById(R.id.textConfContrasenaNueva);

        btnGuardar = (Button) findViewById(R.id.btn_guardar);
        btnCancelar = (Button) findViewById(R.id.btn_cancelar);

        traerDatos(idUsuario);

        btnGuardar.setOnClickListener(this);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        String passActual = contrasenaActual.getText().toString().trim();
        String passNueva = contrasenaNueva.getText().toString().trim();
        String passConfNueva = confContrasenaNueva.getText().toString().trim();
        boolean cambio = true;

        Bundle miBundle = this.getIntent().getExtras();

        if(TextUtils.isEmpty(passActual)) {
            contrasenaActual.setError("Campo requerido");
            cambio = false;
        }
        if(TextUtils.isEmpty(passNueva)) {
            contrasenaNueva.setError("Campo requerido");
            cambio = false;
        }
        if(TextUtils.isEmpty(passNueva)) {
            confContrasenaNueva.setError("Campo requerido");
            cambio = false;
        }
        if(TextUtils.equals(passNueva, passConfNueva) == false) {
            Toast.makeText(OptionsActivity.this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
            cambio = false;
        }

        if(cambio){
            cambiarContrasena(miBundle.getString("uid"), passActual, passNueva, passConfNueva);
        }
    }

    private void traerDatos(String id){

        //Instancia a la base de datos
        FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        //apuntamos al nodo que queremos leer
        DatabaseReference myRef = fdb.getReference("Usuario");

        myRef.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot data: snapshot.getChildren()){
                        if(data.child("uid").getValue().equals(id)){
                            nombreUsuario.setText((String) data.child("nombre").getValue());
                            correoUsuario.setText((String) data.child("correo").getValue());
                            edadUsuario.setText((String) data.child("edad").getValue());
                            telefonoUsuario.setText((String) data.child("telefono").getValue());
                            sexoUsuario.setText((String) data.child("sexo").getValue());
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Fallo la lectura: " + error.getCode());
            }
        });
    }

    private void cambiarContrasena(String id, String passActual, String passNueva, String passConfNueva){
        //Instancia a la base de datos
        FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        //apuntamos al nodo que queremos leer
        DatabaseReference myRef = fdb.getReference("Usuario");

        myRef.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()) {
                    if (data.child("uid").getValue().equals(id)) {
                        //firebaseAuth.confirmPasswordReset(id, passNueva);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Fallo la lectura: " + error.getCode());
            }
        });
    }
}