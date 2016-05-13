package br.com.blackseed.blackimob.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import br.com.blackseed.blackimob.R;


/**
 * Created by tabocu on 28/04/16.
 */
public class AdressEditView extends LinearLayout {

    private ImageButton mExpandBtn;

    private EditText mStreetEditText;
    private EditText mNumberEditText;
    private EditText mCompEditText;
    private EditText mPostalEditText;
    private EditText mCityEditText;
    private EditText mStateEditText;


    public AdressEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        View view = inflate(getContext(), R.layout.address_item, null);

        mStreetEditText = (EditText) view.findViewById(R.id.streetEditText);
        mNumberEditText = (EditText) view.findViewById(R.id.numberEditText);
        mCompEditText = (EditText) view.findViewById(R.id.compEditText);
        mPostalEditText = (EditText) view.findViewById(R.id.postalEditText);
        mCityEditText = (EditText) view.findViewById(R.id.cityEditText);
        mStateEditText = (EditText) view.findViewById(R.id.stateEditText);
        mExpandBtn = (ImageButton) view.findViewById(R.id.expandBtn);

        mStreetEditText.setOnFocusChangeListener(new ExpandFocusChangeListener());
        mNumberEditText.setOnFocusChangeListener(new ExpandFocusChangeListener());
        mCompEditText.setOnFocusChangeListener(new ExpandFocusChangeListener());
        mPostalEditText.setOnFocusChangeListener(new ExpandFocusChangeListener());
        mCityEditText.setOnFocusChangeListener(new ExpandFocusChangeListener());
        mStateEditText.setOnFocusChangeListener(new ExpandFocusChangeListener());

        view.setOnFocusChangeListener(new ExpandFocusChangeListener());
        addView(view);


    }

    public boolean hasFocus() {
        if (mStreetEditText.hasFocus()
                || mNumberEditText.hasFocus()
                || mCompEditText.hasFocus()
                || mPostalEditText.hasFocus()
                || mCityEditText.hasFocus()
                || mStateEditText.hasFocus())
            return true;
        else return false;
    }

    public class ExpandFocusChangeListener implements OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus()) {
                findViewById(R.id.detailAddress).setVisibility(VISIBLE);
                ((ImageButton) findViewById(R.id.expandBtn)).setImageResource(R.drawable.ic_expand_less_black_24dp);
                ((EditText) findViewById(R.id.streetEditText)).setHint("Rua");
            } else {
                findViewById(R.id.detailAddress).setVisibility(GONE);
                ((ImageButton) findViewById(R.id.expandBtn)).setImageResource(R.drawable.ic_expand_more_black_24dp);
                ((EditText) findViewById(R.id.streetEditText)).setHint("Endereço");
            }
        }
    }


}
