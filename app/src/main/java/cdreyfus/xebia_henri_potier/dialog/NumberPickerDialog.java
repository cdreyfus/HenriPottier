package cdreyfus.xebia_henri_potier.dialog;


import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cdreyfus.xebia_henri_potier.R;


public class NumberPickerDialog extends AlertDialog {

    @BindView(R.id.number_picker_title)
    TextView mTitle;
    @BindView(R.id.numberPicker)
    NumberPicker mNumberPicker;
    @BindView(R.id.number_picker_cancel)
    Button bCancel;
    @BindView(R.id.number_picker_ok)
    Button bOk;

    public NumberPickerDialog(Context context, int currentValue) {
        super(context);
        ButterKnife.bind(this, View.inflate(getContext(), R.layout.dialog_number_picker, null));
        mNumberPicker.setMinValue(1);
        mNumberPicker.setMaxValue(10);
        mNumberPicker.setValue(currentValue);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle.setText(title);
    }

    public int getValue() {
        return mNumberPicker.getValue();
    }

    public void setValue(int value) {
        mNumberPicker.setValue(value);
    }

    public void setButton1(final android.view.View.OnClickListener onClickListener) {
        bOk.setText(R.string.ok);
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(v);

            }
        };
        bOk.setOnClickListener(clickListener);
    }


    public void setButton2(final android.view.View.OnClickListener onClickListener) {
        bCancel.setText(R.string.cancel);
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(v);
            }
        };
        bCancel.setOnClickListener(clickListener);
    }
}
