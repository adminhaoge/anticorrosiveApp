package com.example.imitationtaobao.customview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.imitationtaobao.R;

public class EditTextWithDelete extends RelativeLayout {

    private EditText etContent;
    private ImageView ivDelete;

    public EditTextWithDelete(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.custom_layout_edittextwithdelete, this);

        etContent = findViewById(R.id.Layout_EditTextWithDelete_etContent);
        ivDelete = findViewById(R.id.Layout_EditTextWithDelete_ivDelete);

        ivDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                etContent.setText("");
            }
        });

        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 1) {
                    ivDelete.setVisibility(VISIBLE);
                } else {
                    ivDelete.setVisibility(INVISIBLE);
                }

            }
        });
    }

    /**
     * 获取EditText的内容
     * @return
     */
    public String getEditText() {
        return etContent.getText().toString();
    }

    public void setDrawableLeft(int img){
        Drawable drawable = getResources().getDrawable(img);
        drawable.setBounds(1,1,60,50);
        etContent.setCompoundDrawables(drawable,null,null,null);
    }
}
