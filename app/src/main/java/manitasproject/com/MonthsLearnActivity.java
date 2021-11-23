package manitasproject.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

public class MonthsLearnActivity extends AppCompatActivity {
    FragmentTransaction transaction;
    Fragment fragment_meses1,fragment_meses2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_months_learn);
        fragment_meses1 = new FragmentMeses1();
        fragment_meses2 = new FragmentMeses2();

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,fragment_meses1).commit();
    }
    public void optionsLearnMonths(View view) {
        transaction = getSupportFragmentManager().beginTransaction();
        switch (view.getId()) {

            case R.id.btn_l1:
                transaction.replace(R.id.fragment_container, fragment_meses1);
                transaction.addToBackStack(null);
                break;

            case R.id.btn_l2:
                transaction.replace(R.id.fragment_container, fragment_meses2);
                transaction.addToBackStack(null);
                break;

        }
        transaction.commit();
    }
}