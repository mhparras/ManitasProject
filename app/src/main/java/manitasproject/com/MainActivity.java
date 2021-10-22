package manitasproject.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.database.core.Context;

import manitasproject.com.model.CredencialPersona;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnIniciar;
    private Button btnRegistro;
    private EditText textCorreo;
    private EditText textContrasena;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        textCorreo = (EditText) findViewById(R.id.txt_correo);
        textContrasena = (EditText) findViewById(R.id.txt_contrasena);

        btnIniciar = (Button) findViewById(R.id.btn_iniciar);
        btnRegistro = (Button) findViewById(R.id.btn_registrarse);

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
                    if(task.isSuccessful())
                        pedirId(credencialPersona.getCorreo());
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

        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        intent.putExtra("uid", id);

        //Creamos el trasnporte del id del usuario logeado
        Bundle miBundle = new Bundle();
        miBundle.putString("uid", id);

        //Enviamos el elemento a la siguiente actividad
        intent.putExtras(miBundle);

        startActivity(intent);

        //Limpiamos las cajas de texto después de hacer el loggin
        textCorreo.setText("");
        textContrasena.setText("");
    }
}