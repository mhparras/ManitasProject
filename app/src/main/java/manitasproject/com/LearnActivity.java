package manitasproject.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LearnActivity extends AppCompatActivity {
    FragmentTransaction transaction;
    Fragment fragment_meses1, fragment_meses2, fragment_animales1,
            fragment_animales2, fragment_colores1, fragment_colores2, fragment_colores3;

    private Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        fragment_meses1 = new FragmentMeses1();
        fragment_meses2 = new FragmentMeses2();
        fragment_animales1 = new FragmentAnimales1();
        fragment_animales2 = new FragmentAnimales2();
        fragment_colores1 = new FragmentColores1();
        fragment_colores2 = new FragmentColores2();
        fragment_colores3 = new FragmentColores3();

        btnVolver = (Button) findViewById(R.id.btn_volver);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment_meses1);
    }

    public void optionsLearn(View view) {
        transaction = getSupportFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.btn_meses:
                transaction.replace(R.id.fragment_container, fragment_meses1);
                transaction.addToBackStack(null);
                break;


            case R.id.btn_animales:
                transaction.replace(R.id.fragment_container, fragment_animales1);
                transaction.addToBackStack(null);
                break;


            case R.id.btn_colores:
                transaction.replace(R.id.fragment_container, fragment_colores1);
                transaction.addToBackStack(null);
                break;
        }
        transaction.commit();

    }
}