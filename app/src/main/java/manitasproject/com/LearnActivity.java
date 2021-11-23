package manitasproject.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LearnActivity extends AppCompatActivity {


    private Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);


        btnVolver = (Button) findViewById(R.id.btn_volver);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    public void optionsLearn(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btn_colores:
                //code
                intent = new Intent(LearnActivity.this, ColorsLearnActivity.class);
                break;
            case R.id.btn_animales:
                //code
                intent = new Intent(LearnActivity.this, AnimalsLearnActivity.class);
                break;
            case R.id.btn_abecedario:
                //code
                //intent=new Intent(LearnActivity.this, OptionsActivity.class);
                break;

            case R.id.btn_dias:
                //code
                //intent=new Intent(LearnActivity.this, OptionsActivity.class);
                break;

            case R.id.btn_meses:
                //code
                intent = new Intent(LearnActivity.this, MonthsLearnActivity.class);
                break;

            case R.id.btn_saludos:
                //code
                //intent=new Intent(LearnActivity.this, OptionsActivity.class);
                break;

            case R.id.btn_basico:
                //code
                //intent=new Intent(LearnActivity.this, OptionsActivity.class);
                break;

            case R.id.btn_verbos:
                //code
                //intent=new Intent(LearnActivity.this, OptionsActivity.class);
                break;

            case R.id.btn_operaciones:
                //code
                //intent=new Intent(LearnActivity.this, OptionsActivity.class);
                break;
        }
        startActivity(intent);
    }


}