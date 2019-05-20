package com.sonbaty.auction.ui.fragment.loginCycle;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.sonbaty.auction.R;
import com.sonbaty.auction.data.api.ApiClient;
import com.sonbaty.auction.data.api.ApiServices;
import com.sonbaty.auction.data.model.forgetPassword.ForgetPassword;
import com.sonbaty.auction.data.model.phoneKeys.PhoneKeys;
import com.sonbaty.auction.helper.HelperMethod;
import com.sonbaty.auction.helper.Validation;

import java.util.List;

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
public class ForgetPasswordFragment extends Fragment {

    View view;
    @BindView(R.id.Forget_Password_EditText_UserPhone)
    EditText ForgetPasswordEditTextUserPhone;
    @BindView(R.id.Forget_Password_Spinner_Phone_Keys)
    Spinner ForgetPasswordSpinnerPhoneKeys;
    Unbinder unbinder;
    private ApiServices apiServices;
    private Validation validation;
    private List<String> keys;
    private String phone;
    private String code;

    public ForgetPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_forget_password, container, false);
        unbinder = ButterKnife.bind(this, view);
        intiObjects();
        initSpinner();
        return view;
    }


    private void intiViews() {
        phone = ForgetPasswordEditTextUserPhone.getText().toString().trim();
        code = ForgetPasswordSpinnerPhoneKeys.getSelectedItem().toString();
    }

    private void intiObjects() {
        apiServices = ApiClient.getGeneral().create(ApiServices.class);
        validation = new Validation();
    }


    private void initSpinner() {
        apiServices.getPhoneKeys().enqueue(new Callback<PhoneKeys>() {
            @Override
            public void onResponse(@NonNull Call<PhoneKeys> call, @NonNull Response<PhoneKeys> response) {
                if (response.isSuccessful() && response.body() != null) {
                    keys = response.body().getData();
                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item
                            , keys);
                    ForgetPasswordSpinnerPhoneKeys.setAdapter(spinnerAdapter);

                } else {
                    Toast.makeText(getActivity(), "Error1 : " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PhoneKeys> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "Error2 : " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Forget_Password_Button_Done)
    public void onViewClicked() {
        onPhoneValidate();
    }

    private void onPhoneValidate() {
        intiViews();
        boolean allowSave = true;
        if (validation.isNullOrEmpty(phone) || validation.isNumeric(phone)) {
            ForgetPasswordEditTextUserPhone.setError(getString(R.string.forget_password_error_message_phone));
            allowSave = false;
        }
        if (allowSave) {
            onSendForgetPassword();
            Snackbar.make(getView().getRootView(), R.string.success_message_snackbar, Snackbar.LENGTH_LONG).show();
            emptyInputEditText();

        } else {
            Snackbar.make(getView().getRootView(), R.string.error_message_snackbar, Snackbar.LENGTH_LONG).show();
            emptyInputEditText();

        }
    }

    private void onSendForgetPassword() {
        HelperMethod.showProgressDialog(getActivity(), getString(R.string.progressDialog_Loading));
        apiServices.sendForgetPasswordRequest(phone, code).enqueue(new Callback<ForgetPassword>() {
            @Override
            public void onResponse(@NonNull Call<ForgetPassword> call, @NonNull Response<ForgetPassword> response) {
                HelperMethod.dismissProgressDialog();
                if (response.isSuccessful() && response.body() != null){
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                    Integer userId = response.body().getData().getUserId();
                    ResetPasswordFragment resetPasswordFragment = new ResetPasswordFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("userId", String.valueOf(userId));
                    resetPasswordFragment.setArguments(bundle);
                    HelperMethod.replace(resetPasswordFragment,getActivity().getSupportFragmentManager(),R.id.Login_FrameLayout_Container,null,null);
                }else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ForgetPassword> call, @NonNull Throwable t) {
                HelperMethod.dismissProgressDialog();
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void emptyInputEditText() {
        ForgetPasswordEditTextUserPhone.setText(null);

    }
}
