package br.com.blackseed.blackimob.utils;

import android.text.Editable;
import android.text.TextWatcher;

import java.text.NumberFormat;

public class MaskTextWatcher implements TextWatcher {

    private boolean mEditing;
    private Mask mMask;

    public MaskTextWatcher(Mask mask) {
        mEditing = false;
        mMask = mask;
    }

    public synchronized void afterTextChanged(Editable s) {
        if (!mEditing) {
            mEditing = true;
            if (s.toString().isEmpty()) {
                mEditing = false;
                return;
            }

            String digits = s.toString().replaceAll("\\D", "");
            NumberFormat nf = NumberFormat.getCurrencyInstance();
            try {
                String formatted = format(digits, mMask.getMask(), mMask.getSize());
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

    public enum Mask {

        CPF("###.###.###-##", 11),
        CNPJ("##.###.###/####-##", 14),
        CEP("##.###-###", 8);

        private final String text;
        private final int size;

        Mask(final String text, final int size) {
            this.text = text;
            this.size = size;
        }

        public String getMask() {
            return text;
        }

        public int getSize() {
            return size;
        }
    }


}

