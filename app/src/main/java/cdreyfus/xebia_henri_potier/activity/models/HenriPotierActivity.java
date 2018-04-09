package cdreyfus.xebia_henri_potier.activity.models;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.idescout.sql.SqlScoutServer;

import cdreyfus.xebia_henri_potier.R;
import cdreyfus.xebia_henri_potier.activity.BasketActivity;

@SuppressLint("Registered")
public class HenriPotierActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SqlScoutServer.create(this, getPackageName());

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_basket, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_basket:
                Intent goToBasket = new Intent(HenriPotierActivity.this, BasketActivity.class);
                startActivity(goToBasket);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
