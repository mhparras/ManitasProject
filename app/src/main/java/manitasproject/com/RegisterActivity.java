package manitasproject.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText nombre, contrasena, confContrasena, edad, telefono, correo;
    Spinner spinner_sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nombre = findViewById(R.id.txt_nombre);
        correo = findViewById(R.id.txt_correo);
        contrasena = findViewById(R.id.txt_contrasena);
        confContrasena = findViewById(R.id.txt_confContrasena);
        edad = findViewById(R.id.txt_Edad);
        telefono = findViewById(R.id.txt_telefono);
        spinner_sex = (Spinner)findViewById(R.id.spinnerSexo);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.opciones_sexo,android.R.layout.simple_spinner_item);

        spinner_sex.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        String nombre1 = nombre.getText().toString();
        String correo1 = correo.getText().toString();
        String contrasena1 = contrasena.getText().toString();
        String confContrasena1 = confContrasena.getText().toString();
        String edad1 = edad.getText().toString();
        String telefono1 = telefono.getText().toString();
        if(nombre1.equals("") || correo1.equals("") ||contrasena1.equals("") || confContrasena1.equals("") || edad1.equals("") || telefono1.equals(""))
            validacion();
        else {
            Toast.makeText(this, "agregado", Toast.LENGTH_LONG).show();
            limpiarCajas();
        }

        return true;
    }

    private void limpiarCajas() {
        nombre.setText("");
        correo.setText("");
        contrasena.setText("");
        confContrasena.setText("");
        edad.setText("");
        telefono.setText("");
    }

    private void validacion() {
        String nombre1 = nombre.getText().toString();
        String correo1 = correo.getText().toString();
        String contrasena1 = nombre.getText().toString();
        String confContrasena1 = nombre.getText().toString();
        String edad1 = nombre.getText().toString();
        String telefono1 = nombre.getText().toString();
        if(nombre1.equals(""))
            nombre.setError("Campo Requerido");
        if(correo1.equals(""))
            correo.setError("Campo Requerido");
        if(contrasena1.equals(""))
            contrasena.setError("Campo Requerido");
        if(confContrasena1.equals(""))
            confContrasena.setError("Campo Requerido");
        if(edad1.equals(""))
            edad.setError("Campo Requerido");
        if(telefono1.equals(""))
            telefono.setError("Campo Requerido");
    }
}