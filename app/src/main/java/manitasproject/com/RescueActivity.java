package manitasproject.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RescueActivity extends AppCompatActivity {

    private EditText textCorreo;
    private Button btnResetContrasena;
    private Button btnVolver;

    private String correo;

    private ProgressDialog pgDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rescue);

        firebaseAuth = FirebaseAuth.getInstance();
        pgDialog = new ProgressDialog(this);

        textCorreo = (EditText) findViewById(R.id.txt_correo);

        btnResetContrasena = (Button) findViewById(R.id.btn_restablecer);
        btnVolver = (Button) findViewById(R.id.btn_volver);

        btnResetContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                correo = textCorreo.getText().toString().trim();
                
                if(!correo.isEmpty()) {
                    pgDialog.setMessage("Espere un momento.");
                    pgDialog.setCanceledOnTouchOutside(false);
                    pgDialog.show();
                    resetConstrasena();
                } else {
                    textCorreo.setError("Campo requerido");
                    Toast.makeText(RescueActivity.this, "Ingrese el correo", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void resetConstrasena(){
        firebaseAuth.setLanguageCode("es");
        firebaseAuth.sendPasswordResetEmail(correo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                    Toast.makeText(RescueActivity.this, "Se le ha enviado un correo para restablecer contraseña", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(RescueActivity.this, "No se pudo enviar el correo de recuperación.", Toast.LENGTH_LONG).show();

                pgDialog.dismiss();
            }
        });
    }
}