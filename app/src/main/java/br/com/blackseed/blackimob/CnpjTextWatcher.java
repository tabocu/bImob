package br.com.blackseed.blackimob;

import android.text.Editable;
import android.text.TextWatcher;

import java.text.NumberFormat;
import java.util.concurrent.atomic.AtomicReference;

public class CnpjTextWatcher implements TextWatcher {

    private static final String maskCNPJ = "##.###.###/####-##";
    private static final String maskCPF = "###.###.###-##";

    boolean mEditing;
    AtomicReference<String> mCnpj;

    public CnpjTextWatcher(AtomicReference<String> cnpj) {
        mEditing = false;
        mCnpj = cnpj;
    }

    public synchronized void afterTextChanged(Editable s) {
        if (!mEditing) {
            mEditing = true;

            String digits = s.toString().replaceAll("\\D", "");
            NumberFormat nf = NumberFormat.getCurrencyInstance();
            try {
                String formatted = format(digits, maskCNPJ, 14);
                mCnpj.set(digits);
                s.replace(0, s.length(), formatted);
            } catch (NumberFormatException nfe) {
                s.clear();
            }

            mEditing = false;
        }
    }

    public String format(String source, String mask, int digits) {

        if (source.length() > digits)
            source = source.substring(source.length() - digits);

        StringBuilder result = new StringBuilder();

        int j = 0;
        for (int i = 0; i < mask.length(); i++) {
            if (mask.charAt(i) == '#') {
                if (j < source.length())
                    result.append(source.charAt(j++));
                else
                    result.append('0');
            } else {
                result.append(mask.charAt(i));
            }
        }

        return result.toString();
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }


}

