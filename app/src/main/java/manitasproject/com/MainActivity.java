package manitasproject.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import manitasproject.com.model.CredencialPersona;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnIniciar;
    private Button btnRegistro;
    private EditText textCorreo;
    private EditText textContrasena;

    private TextView textOlvidar;
    private Switch recordarSesion;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recordarSesion = (Switch) findViewById(R.id.recordarLogin);

        if(obtenerEstadoBoton()){
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        }

        firebaseAuth = FirebaseAuth.getInstance();

        textCorreo = (EditText) findViewById(R.id.txt_correo);
        textContrasena = (EditText) findViewById(R.id.txt_contrasena);

        btnIniciar = (Button) findViewById(R.id.btn_iniciar);
        btnRegistro = (Button) findViewById(R.id.btn_registrarse);

        textOlvidar = (TextView) findViewById(R.id.txt_olvidar);

        textOlvidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RescueActivity.class);
                startActivity(intent);

                Toast.makeText(MainActivity.this, "Recuperación de contraseña.", Toast.LENGTH_SHORT).show();
            }
        });

        btnIniciar.setOnClickListener(this);
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onClick(View view) {
        this.logearUsuario();
    }

    //****** lógica para inicio de sesión ******//

    private void logearUsuario(){

        CredencialPersona credencialPersona;
        try{
            credencialPersona = validarLogin();
        }catch(Exception exc){
            Toast.makeText(this, exc.getMessage(),Toast.LENGTH_LONG).show();
            return;
        }

        logeador(credencialPersona);
    }

    private CredencialPersona validarLogin() throws Exception {

        //creamos la instancia del objeto credencial persona
        CredencialPersona credencialPersona = new CredencialPersona();

        //Obtenemos el correo y la contraseña de las cajas de texto
        String correo = textCorreo.getText().toString().trim();
        String contrasena = textContrasena.getText().toString().trim();

        //Validamos que no nos llegen datos vacios
        if(TextUtils.isEmpty(correo))
            throw new Exception("Ingresa tu correo");

        if(TextUtils.isEmpty(contrasena))
            throw new Exception("Ingresa tu contraseña");

        credencialPersona.setCorreo(correo);
        credencialPersona.setContrasena(contrasena);

        return credencialPersona;
    }

    private void logeador(final CredencialPersona credencialPersona) {
        firebaseAuth.signInWithEmailAndPassword(credencialPersona.getCorreo(), credencialPersona.getContrasena())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            pedirId(credencialPersona.getCorreo());
                            guardarEstadoLogin();
                        }
                        else{
                            if(task.getException() instanceof FirebaseAuthUserCollisionException)
                                Toast.makeText(MainActivity.this, "Correo o contraseña incorrectos.", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(MainActivity.this, "Error, no se pudo iniciar la sesión.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void pedirId(final String correo){
        final String[] response = {""};
        final String[] nombreUsuario = {""};

        //Instancia a la base de datos
        FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        //apuntamos al nodo que queremos leer
        DatabaseReference databaseReference = fdb.getReference("Usuario");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot data: snapshot.getChildren()){
                        if(data.child("correo").getValue().equals(correo)) {
                            response[0] = (String) data.child("uid").getValue();
                            nombreUsuario[0] = (String) data.child("nombre").getValue();
                            break;
                        }
                    }
                    Toast.makeText(MainActivity.this, "Bienvenido: " + nombreUsuario[0], Toast.LENGTH_LONG).show();
                }
                siguienteActividad(response[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Error en lectura: " + error.getCode());
            }
        });
    }

    public void siguienteActividad(String id){

        SharedPreferences preferences = getSharedPreferences("idUsuario", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("id", id);

        editor.commit();

        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    public void guardarEstadoLogin(){
        SharedPreferences pref = getSharedPreferences("SESION_BOTON", MODE_PRIVATE);
        pref.edit().putBoolean("PREFERENCE_ESTADO_BTN_SESION", recordarSesion.isChecked()).apply(); //Guardar estado del boton en la actividad
    }

    public boolean obtenerEstadoBoton(){
        SharedPreferences pref = getSharedPreferences("SESION_BOTON", MODE_PRIVATE);
        return pref.getBoolean("PREFERENCE_ESTADO_BTN_SESION", false); //retornar por defecto el estado en false de sesion iniciada
    }
}