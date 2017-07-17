package com.MinimalSoft.Joiin.Widgets;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.MinimalSoft.Joiin.R;

public class SingleInputAlertDialog implements View.OnClickListener, DialogInterface.OnShowListener, DialogInterface.OnClickListener {
    public static final String EMAIL_INPUT = "EMAIL_INPUT_DIALOG";

    private OnInputEventListener dialogListener;
    private AlertDialog alertDialog;
    private EditText editText;
    private String regex;

    public SingleInputAlertDialog(@NonNull Context context, @Nullable String title, @Nullable String message,
                                  @Nullable String inputType, @Nullable String regex) {
        View view = createView(context, message, inputType);

        alertDialog = new AlertDialog.Builder(context)
                .setNegativeButton("Cancelar", this)
                .setPositiveButton("Aceptar", null)
                .setCancelable(false)
                .setTitle(title)
                .setView(view)
                .create();

        alertDialog.setOnShowListener(this);
        this.regex = regex;
    }

    @Override
    public void onShow(DialogInterface dialog) {
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(this);
        editText.setText("");
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (dialogListener != null) {
            dialogListener.onInputDialogCanceled();
        }
    }

    @Override
    public void onClick(View v) {
        String input = editText.getText().toString();

        if (input.isEmpty()) {
            editText.setError("Campo vacío");
        } else {
            if (regex != null && !input.matches(regex)) {
                editText.setError(editText.getHint() + " no válido");
            } else if (dialogListener != null) {
                dialogListener.onInputRetrieved(input);
                alertDialog.dismiss();
            } else {
                alertDialog.dismiss();
            }
        }
    }

    private View createView(Context context, String text, String input) {
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        int sidePadding = (int) context.getResources().getDimension(R.dimen.default_dialog_padding);
        int topPadding = (int) context.getResources().getDimension(R.dimen.dialog_padding);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setPadding(sidePadding, topPadding, sidePadding, 0);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(layoutParams);

        if (text != null) {
            TextView textView = new TextView(context);
            textView.setPadding(0, 0, 0, topPadding);
            textView.setLayoutParams(layoutParams);
            textView.setText(text);

            linearLayout.addView(textView);
        }

        editText = new EditText(context);

        switch (input) {
            case EMAIL_INPUT:
                editText.setHint("Correo electrónico");
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                break;
        }

        editText.setLayoutParams(layoutParams);
        linearLayout.addView(editText);

        return linearLayout;
    }

    public void show() {
        alertDialog.show();
    }

    public void addOnInputDialogListener(OnInputEventListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    public interface OnInputEventListener {
        /**
         * The method that will be called when the dialog positive button is clicked and the input
         * has successfully passed the regex test.
         *
         * @param input the String representing the returned input
         */
        void onInputRetrieved(String input);

        /**
         * The method that will be called when the dialog negative button is clicked.
         */
        void onInputDialogCanceled();
    }
}