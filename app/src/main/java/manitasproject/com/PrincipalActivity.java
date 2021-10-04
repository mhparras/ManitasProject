package manitasproject.com;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class PrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prinicipal);
    }
    public void goToLearning(View view) {
        Intent learning = new Intent(this, LearningActivity.class);
        startActivity(learning);
    }
    public void goToGames(View view) {
        Intent learning = new Intent(this, GamesActivity.class);
        startActivity(learning);
    }
    public void goToOptions(View view) {
        Intent learning = new Intent(this, OptionsActivity.class);
        startActivity(learning);
    }
}
