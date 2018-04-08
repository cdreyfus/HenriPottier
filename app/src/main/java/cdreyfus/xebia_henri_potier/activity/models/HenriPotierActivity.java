package cdreyfus.xebia_henri_potier.activity.models;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import cdreyfus.xebia_henri_potier.R;
import cdreyfus.xebia_henri_potier.activity.CartActivity;

@SuppressLint("Registered")
public class HenriPotierActivity extends AppCompatActivity {

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_cart:
                Intent goToCart = new Intent(HenriPotierActivity.this, CartActivity.class);
                startActivity(goToCart);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
