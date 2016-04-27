package br.com.blackseed.blackimob;

import android.text.Editable;
import android.text.TextWatcher;

import java.text.NumberFormat;
import java.util.concurrent.atomic.AtomicReference;

public class CurrencyTextWatcher implements TextWatcher {

    boolean mEditing;
    AtomicReference<Double> mValor;

    public CurrencyTextWatcher(AtomicReference<Double> valor) {
        mValor = valor;
        mEditing = false;
    }

    public synchronized void afterTextChanged(Editable s) {
        if (!mEditing) {
            mEditing = true;

            String digits = s.toString().replaceAll("\\D", "");
            NumberFormat nf = NumberFormat.getCurrencyInstance();
            try {
                String formatted = nf.format(Double.parseDouble(digits) / 100);
                s.replace(0, s.length(), formatted);
                mValor.set(Double.parseDouble(digits));
            } catch (NumberFormatException nfe) {
                s.clear();
            }

            mEditing = false;
        }
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

}
