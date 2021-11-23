package manitasproject.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

public class AnimalsLearnActivity extends AppCompatActivity {
    FragmentTransaction transaction;
    Fragment fragment_animales1,fragment_animales2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals_learn);
        fragment_animales1 = new FragmentAnimales1();
        fragment_animales2 = new FragmentAnimales2();


        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,fragment_animales1).commit();

    }
    public void optionsLearnAnimals(View view) {
        transaction = getSupportFragmentManager().beginTransaction();
        switch (view.getId()) {

            case R.id.btn_l1:
                transaction.replace(R.id.fragment_container, fragment_animales1);
                transaction.addToBackStack(null);
                break;

            case R.id.btn_l2:
                transaction.replace(R.id.fragment_container, fragment_animales2);
                transaction.addToBackStack(null);
                break;

        }
        transaction.commit();

    }
}