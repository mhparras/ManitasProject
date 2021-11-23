package manitasproject.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

public class ColorsLearnActivity extends AppCompatActivity {
    FragmentTransaction transaction;
    Fragment fragment_colores1, fragment_colores2,fragment_colores3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors_learn);

        fragment_colores1 = new FragmentColores1();
        fragment_colores2 = new FragmentColores2();
        fragment_colores3 = new FragmentColores3();

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,fragment_colores1).commit();

    }
    public void optionsLearnColors(View view) {
        transaction = getSupportFragmentManager().beginTransaction();
        switch (view.getId()) {

            case R.id.btn_l1:
                transaction.replace(R.id.fragment_container, fragment_colores1);
                transaction.addToBackStack(null);
                break;

            case R.id.btn_l2:
                transaction.replace(R.id.fragment_container, fragment_colores2);
                transaction.addToBackStack(null);
                break;

            case R.id.btn_l3:
                transaction.replace(R.id.fragment_container, fragment_colores3);
                transaction.addToBackStack(null);
                break;
        }
        transaction.commit();

    }
}