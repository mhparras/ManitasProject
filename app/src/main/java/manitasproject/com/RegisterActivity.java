package manitasproject.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

import manitasproject.com.model.Persona;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText nombre, contrasena, confContrasena, edad, telefono, correo;
    private Button btnRegistro;
    private Button btnCancelar;
    Spinner spinner_sex;

    private FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inicializarFirebase();

        nombre = findViewById(R.id.txt_nombre);
        correo = findViewById(R.id.txt_correo);
        contrasena = findViewById(R.id.txt_contrasena);
        confContrasena = findViewById(R.id.txt_confContrasena);
        edad = findViewById(R.id.txt_edad);
        telefono = findViewById(R.id.txt_telefono);
        spinner_sex = (Spinner)findViewById(R.id.spinnerSexo);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.opciones_sexo,android.R.layout.simple_spinner_item);

        spinner_sex.setAdapter(adapter);

        btnRegistro = (Button) findViewById(R.id.btn_registrarse);
        btnCancelar = (Button) findViewById(R.id.btn_cancelar);
        btnRegistro.setOnClickListener(this);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //Iniciamos los servicios y la conexión de Firebase
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onClick(View view) {
        userRegister();
    }

    private void userRegister() {
        final Persona persona;
        try{
            persona = validateRegister();
        }catch (Exception exc){
            Toast.makeText(this, exc.getMessage(),Toast.LENGTH_LONG).show();
            return;
        }

        generateId(persona);
    }

    public void nextActivityMain(){
        Intent intent = new Intent(getApplication(), MainActivity.class);
        startActivity(intent);
    }

    private final Persona validateRegister() throws Exception {

        final Persona persona = new Persona();

        String nombre1 = nombre.getText().toString().trim();
        String correo1 = correo.getText().toString().trim();
        String contrasena1 = contrasena.getText().toString().trim();
        String confContrasena1 = confContrasena.getText().toString().trim();
        String edad1 = edad.getText().toString().trim();
        String telefono1 = telefono.getText().toString().trim();
        String sexo1 = spinner_sex.getSelectedItem().toString().trim();
        boolean registroValido = true;
        if(TextUtils.isEmpty(nombre1)) {
            nombre.setError("Campo requerido");
            registroValido = false;
        }
        if(TextUtils.isEmpty(correo1)){
            correo.setError("Campo requerido");
            registroValido = false;
        }
        if(TextUtils.isEmpty(contrasena1)) {
            contrasena.setError("Campo requerido");
            registroValido = false;
        }
        if(TextUtils.isEmpty(confContrasena1)) {
            confContrasena.setError("Campo requerido");
            registroValido = false;
        }
        if(TextUtils.equals(contrasena1, confContrasena1) == false) {
            registroValido = false;
            throw new Exception("Las contraseñas no coinciden.");
        }
        if(TextUtils.isEmpty(edad1)) {
            edad.setError("Campo requerido");
            registroValido = false;
        }
        if(TextUtils.isEmpty(telefono1)) {
            telefono.setError("Campo requerido");
            registroValido = false;
        }
        if(sexo1.equals("") || TextUtils.isEmpty(sexo1)) {
            registroValido = false;
            throw new Exception("Debe seleccionar el sexo.");
        }

        if(registroValido) {
            persona.setUid(UUID.randomUUID().toString());
            persona.setNombre(nombre1);
            persona.setCorreo(correo1);
            persona.setContrasena(contrasena1);
            persona.setEdad(edad1);
            persona.setTelefono(telefono1);
            persona.setSexo(sexo1);
        }

        return persona;
    }

    //*** Metodos para solicitudes a Firebase ***//

    private void generateId(final Persona persona) {
        //Instancia a la base de datos
        FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        //apuntamos al nodo que queremos leer
        DatabaseReference myRef = fdb.getReference("Usuario");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String id = String.valueOf(snapshot.getChildrenCount()+1);
                    String idUser = "";
                    for(long count = id.length(); count < 6; count++){
                        idUser = idUser + "0";
                    }
                    idUser = idUser + id;
                }
                registerNow(persona);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Fallo la lectura: " + error.getCode());
            }
        });
    }

    public void registerNow(final Persona persona){
        firebaseAuth.createUserWithEmailAndPassword(persona.getCorreo(), persona.getContrasena()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                //checking if success
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this,"Registro exitoso.",Toast.LENGTH_LONG).show();
                }else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException) {//si se presenta una colisión
                        Toast.makeText(RegisterActivity.this, "El usuario ya se encuentra registrado ", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        Toast.makeText(RegisterActivity.this, "Error del Servidor - no se pudo registrar el usuario", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                databaseReference.child("Usuario").push().setValue(persona);
                nextActivityMain();
            }
        });
    }
}