package manitasproject.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button btn_learning,btn_games,btn_options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btn_learning= findViewById(R.id.btn_aprendizaje);
        btn_games= findViewById(R.id.btn_juegos);
        btn_options= findViewById(R.id.btn_options);
    }

    public void options(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.btn_aprendizaje:
                //code
                intent=new Intent(MenuActivity.this,LearnActivity.class);
                break;
            case R.id.btn_juegos:
                //code
                intent=new Intent(MenuActivity.this, GamesActivity.class);
                break;
            case R.id.btn_options:
                //code
                intent=new Intent(MenuActivity.this, OptionsActivity.class);
                break;
        }
        startActivity(intent);
    }
}