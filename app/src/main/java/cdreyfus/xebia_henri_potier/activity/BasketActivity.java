package cdreyfus.xebia_henri_potier.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cdreyfus.xebia_henri_potier.R;
import cdreyfus.xebia_henri_potier.activity.models.HenriPotierActivity;

public class CartActivity extends HenriPotierActivity {

    @BindView(R.id.activity_cart_final_price)TextView mTextFinalPrice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);

    }
}
