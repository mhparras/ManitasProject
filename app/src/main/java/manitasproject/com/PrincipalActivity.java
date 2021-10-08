package manitasproject.com;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PrincipalActivity extends AppCompatActivity {

    Button btn_learning,btn_games,btn_options;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prinicipal);

        btn_learning= findViewById(R.id.btn_aprendizaje);
        btn_games= findViewById(R.id.btn_juegos);
        btn_options= findViewById(R.id.btn_options);
    }



    public void goToLearning(View view) {
        Intent learning = new Intent(this, LearningActivity.class);
        startActivity(learning);
    }
    public void goToGames(View view) {
        startActivity(new Intent(this, GamesActivity.class));
    }
    public void goToOptions(View view) {
        Intent learning = new Intent(this, OptionsActivity.class);
        startActivity(learning);
    }

    public void options(View view) {

        Intent intent = null;


        switch (view.getId()){
            case R.id.btn_aprendizaje:
                //code
                intent=new Intent(PrincipalActivity.this,LearningActivity.class);
                break;
            case R.id.btn_juegos:
                //code
                intent=new Intent(PrincipalActivity.this,GamesActivity.class);
                break;
            case R.id.btn_options:
                //code
                intent=new Intent(PrincipalActivity.this,OptionsActivity.class);
                break;
        }
        startActivity(intent);
    }
}
