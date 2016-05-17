package br.com.blackseed.blackimob.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import br.com.blackseed.blackimob.R;

/**
 * Created by tabocu on 28/04/16.
 */
public class MultiEditView extends LinearLayout {

    private int numberOfViews = 1;
    private String mHint;
    private String mAddText;
    private int mInputType;

    private List<EditText> editTextList;


    public MultiEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.MultiEditView,
                0, 0);

        try {
            mHint = a.getString(R.styleable.MultiEditView_hint);
            mAddText = a.getString(R.styleable.MultiEditView_addText);
            mInputType = a.getInt(R.styleable.MultiEditView_inputType, InputType.TYPE_CLASS_TEXT);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        editTextList = new ArrayList<>();

        View view = inflate(getContext(), R.layout.single_item, null);
        view.findViewById(R.id.deleteBtn).setOnClickListener(new RemoveClickListener(view));
        EditText editText = (EditText) view.findViewById(R.id.editText);
        editText.setHint(mHint);
        editText.setInputType(mInputType);
        addView(view);

        editTextList.add(editText);

        View addBtn = inflate(getContext(), R.layout.add_field_btn, null);
        addBtn.findViewById(R.id.addFieldBtn).setOnClickListener(new AddClickListener());
        ((Button) addBtn.findViewById(R.id.addFieldBtn)).setText(mAddText);
        addView(addBtn);


    }

    public List<EditText> getEditTextList() {
        return  editTextList;
    }

    public class RemoveClickListener implements OnClickListener {

        private View parent;

        public RemoveClickListener(View parent) {
            this.parent = parent;

        }

        @Override
        public void onClick(View view) {
            EditText editText = (EditText) parent.findViewById(R.id.editText);
            Log.v("EDIT TEXT: ", editText.toString());
            editTextList.remove(editText);
            removeView(parent);
            numberOfViews--;
        }
    }

    public class AddClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            View view = inflate(getContext(), R.layout.single_item, null);
            view.findViewById(R.id.deleteBtn).setOnClickListener(new RemoveClickListener(view));
            EditText editText = (EditText) view.findViewById(R.id.editText);
            editText.setHint(mHint);
            editText.setInputType(mInputType);
            editTextList.add(editText);

            addView(view, numberOfViews);
            numberOfViews++;
        }
    }


}
