package com.marco.mvp.views.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.marco.mvp.R;
import com.marco.mvp.util.Constants;

public class ForgotPasswordDialog extends DialogFragment implements View.OnClickListener {

    private EditText editText_username;
    private Button button_sendNewPassword;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(layoutInflater.inflate(R.layout.dialog_forgot_password, null, false));
        builder.setTitle(R.string.forgot_password);

        builder.setPositiveButton(R.string.send_me_password, null);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        editText_username = (EditText) alertDialog.findViewById(R.id.editText_username);
        button_sendNewPassword = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);

        editText_username.addTextChangedListener(new CustomTextWatcher());
        button_sendNewPassword.setOnClickListener(this);

        updateSendNewPasswordButton();

        return alertDialog;
    }


    // Update Send New Password Button
    private void updateSendNewPasswordButton() {
        String username = editText_username.getText().toString().trim();
        button_sendNewPassword.setEnabled(!username.isEmpty());
    }


    @Override
    public void onClick(View view) {
        if (view == button_sendNewPassword) {
            String username = editText_username.getText().toString().trim();

            Intent intent = new Intent();
            intent.putExtra(Constants.EXTRA_USERNAME, username);
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        }
    }


    // Custom Text Watcher
    private class CustomTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            updateSendNewPasswordButton();
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }

    }

}
