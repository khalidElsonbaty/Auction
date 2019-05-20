package com.sonbaty.auction.ui.fragment.loginCycle;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sonbaty.auction.R;
import com.sonbaty.auction.data.api.ApiClient;
import com.sonbaty.auction.data.api.ApiServices;
import com.sonbaty.auction.data.model.updatePassword.UpdatePassword;
import com.sonbaty.auction.helper.HelperMethod;
import com.sonbaty.auction.helper.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResetPasswordFragment extends Fragment {
    View view;
    @BindView(R.id.Reset_Password_EditText_Code)
    EditText ResetPasswordEditTextCode;
    @BindView(R.id.Reset_Password_EditText_Password)
    EditText ResetPasswordEditTextPassword;
    @BindView(R.id.Reset_Password_imageView_ShowPassword)
    ImageView ResetPasswordImageViewShowPassword;
    @BindView(R.id.Reset_Password_EditText_RePassword)
    EditText ResetPasswordEditTextRePassword;
    @BindView(R.id.Reset_Password_imageView_ShowRePassword)
    ImageView ResetPasswordImageViewShowRePassword;
    Unbinder unbinder;
    private String code;
    private String password;
    private String repassword;
    private ApiServices apiServices;
    private Validation validation;
    private String userId;
    private int passwordNotVisible = 1;
    private int rePasswordNotVisible = 1;

    public ResetPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        unbinder = ButterKnife.bind(this, view);
        intiObjects();
        intiViews();
        return view;
    }

    private void intiViews() {
        code = ResetPasswordEditTextCode.getText().toString().trim();
        password = ResetPasswordEditTextPassword.getText().toString().trim();
        repassword = ResetPasswordEditTextRePassword.getText().toString().trim();
        userId = getArguments().getString("userId");
    }

    private void intiObjects() {
        apiServices = ApiClient.getGeneral().create(ApiServices.class);
        validation = new Validation();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void onDataValidate() {
        intiViews();
        boolean allowSave = true;
        if (validation.isNullOrEmpty(code) || !validation.isNumeric(code)) {
            ResetPasswordEditTextCode.setError(getString(R.string.reset_password_error_message_code));
            allowSave = false;
        }
        if (validation.isNullOrEmpty(password) || validation.isValidPassword(password, false)) {
            ResetPasswordEditTextPassword.setError(getString(R.string.reset_password_error_message_password));
            allowSave = false;
        }
        if (validation.isNullOrEmpty(repassword) || !repassword.equals(password)) {
            ResetPasswordEditTextRePassword.setError(getString(R.string.reset_password_error_message_password));
            allowSave = false;
        }

        if (allowSave) {
            onSendResetPasswordRequest();
            Snackbar.make(getView().getRootView(), R.string.success_message_snackbar, Snackbar.LENGTH_LONG).show();
            emptyInputEditText();


        } else {
            Snackbar.make(getView().getRootView(), R.string.error_message_snackbar, Snackbar.LENGTH_LONG).show();
        }
    }

    private void onSendResetPasswordRequest() {
        apiServices.sendResetPasswordRequest(userId, code, password).enqueue(new Callback<UpdatePassword>() {
            @Override
            public void onResponse(@NonNull Call<UpdatePassword> call, @NonNull Response<UpdatePassword> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                    HelperMethod.replace(new LoginFragment(), getActivity().getSupportFragmentManager(), R.id.Login_FrameLayout_Container, null, null);
                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UpdatePassword> call, @NonNull Throwable t) {
                HelperMethod.dismissProgressDialog();
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void emptyInputEditText() {
        ResetPasswordEditTextCode.setText(null);
        ResetPasswordEditTextPassword.setText(null);
        ResetPasswordEditTextRePassword.setText(null);

    }

    @OnClick({R.id.Reset_Password_imageView_ShowPassword, R.id.Reset_Password_imageView_ShowRePassword, R.id.Reset_Password_Button_Done})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Reset_Password_imageView_ShowPassword:
                onShowHidePassword();
                break;
            case R.id.Reset_Password_imageView_ShowRePassword:
                onShowHideRePassword();
                break;
            case R.id.Reset_Password_Button_Done:
                onDataValidate();
                break;
        }
    }


    private void onShowHidePassword() {
        if (passwordNotVisible == 1) {
            ResetPasswordEditTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            ResetPasswordImageViewShowPassword.setImageResource(R.drawable.eyee);
            passwordNotVisible = 0;
        } else {

            ResetPasswordEditTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            ResetPasswordImageViewShowPassword.setImageResource(R.drawable.eye);
            passwordNotVisible = 1;
        }
        ResetPasswordEditTextPassword.setSelection(ResetPasswordEditTextPassword.length());

    }

    private void onShowHideRePassword() {
        if (rePasswordNotVisible == 1) {
            ResetPasswordEditTextRePassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            ResetPasswordImageViewShowRePassword.setImageResource(R.drawable.eyee);
            rePasswordNotVisible = 0;
        } else {

            ResetPasswordEditTextRePassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            ResetPasswordImageViewShowRePassword.setImageResource(R.drawable.eye);
            rePasswordNotVisible = 1;
        }
        ResetPasswordEditTextRePassword.setSelection(ResetPasswordEditTextRePassword.length());
    }
}
