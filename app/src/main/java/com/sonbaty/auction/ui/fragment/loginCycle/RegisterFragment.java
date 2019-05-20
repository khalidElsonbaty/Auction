package com.sonbaty.auction.ui.fragment.loginCycle;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sonbaty.auction.R;
import com.sonbaty.auction.data.api.ApiClient;
import com.sonbaty.auction.data.api.ApiServices;
import com.sonbaty.auction.data.model.User;
import com.sonbaty.auction.data.model.phoneKeys.PhoneKeys;
import com.sonbaty.auction.data.model.signUp.SignUp;
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
public class RegisterFragment extends Fragment {


    @BindView(R.id.Register_EditText_UserName)
    EditText RegisterEditTextUserName;
    @BindView(R.id.Register_EditText_Email)
    EditText RegisterEditTextEmail;
    @BindView(R.id.Register_EditText_Phone)
    EditText RegisterEditTextPhone;
    @BindView(R.id.Register_Spinner_Phone_Keys)
    Spinner RegisterSpinnerPhoneKeys;
    @BindView(R.id.Register_EditText_Password)
    EditText RegisterEditTextPassword;
    Unbinder unbinder;
    @BindView(R.id.Register_imageView_ShowPassword)
    ImageView RegisterImageViewShowPassword;
    private ApiServices apiServices;
    private List<String> keys;
    private DatabaseReference mDatabase;
    private FirebaseDatabase database;
    private Validation validation;
    private String name = null;
    private String eMail = null;
    private String phone = null;
    private String password = null;
    private String code = null;
    private User user;
    private int passwordNotVisible = 1;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        unbinder = ButterKnife.bind(this, view);
        FirebaseApp.initializeApp(getActivity());
        intiObjects();
        initSpinner();

        return view;
    }

    private void intiObjects() {
        apiServices = ApiClient.getGeneral().create(ApiServices.class);
        validation = new Validation();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        database = FirebaseDatabase.getInstance();
        user = new User();

    }

    private void intiViews() {
        name = RegisterEditTextUserName.getText().toString().trim();
        eMail = RegisterEditTextEmail.getText().toString().trim();
        phone = RegisterEditTextPhone.getText().toString().trim();
        password = RegisterEditTextPassword.getText().toString().trim();
        code = RegisterSpinnerPhoneKeys.getSelectedItem().toString();
    }

    private void initValidateData() {
        intiViews();
        boolean allowSave = true;
        if (validation.isNullOrEmpty(name)) {
            RegisterEditTextUserName.setError(getString(R.string.register_error_message_username));
            allowSave = false;
        }
        if (validation.isNullOrEmpty(eMail) || !validation.isValidEmail(eMail)) {
            RegisterEditTextEmail.setError(getString(R.string.register_error_message_email));
            allowSave = false;
        }
        if (validation.isNullOrEmpty(phone) || !validation.isNumeric(phone)) {
            RegisterEditTextPhone.setError(getString(R.string.register_error_message_phone));
            allowSave = false;
        }
        if (validation.isNullOrEmpty(password) || validation.isValidPassword(password, false)) {
            RegisterEditTextPassword.setError(getString(R.string.register_error_message_password));
            allowSave = false;
        }


        if (allowSave) {
            onGetCodeWithRegister();
            emptyInputEditText();
        } else {
            Snackbar.make(getView().getRootView(), R.string.error_message_snackbar, Snackbar.LENGTH_LONG).show();
            emptyInputEditText();
        }
    }

    private void emptyInputEditText() {
        RegisterEditTextUserName.setText(null);
        RegisterEditTextEmail.setText(null);
        RegisterEditTextPhone.setText(null);
        RegisterEditTextPassword.setText(null);
    }

    private void onGetCodeWithRegister() {
        user.setUsername(RegisterEditTextUserName.getText().toString().trim());
        user.setEmail(RegisterEditTextEmail.getText().toString().trim());
        user.setPhone(RegisterEditTextPhone.getText().toString().trim());
        user.setPassword(RegisterEditTextPassword.getText().toString().trim());
        mDatabase.push().setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        String key = database.getReference("users").push().getKey();
                        postRegisterData(key);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), R.string.error_message_snackbar, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void postRegisterData(String device_id) {
        HelperMethod.showProgressDialog(getActivity(), "Loading");
        apiServices.sendRegisterRequest(name, phone, eMail, password, device_id, code).enqueue(new Callback<SignUp>() {
            @Override
            public void onResponse(Call<SignUp> call, Response<SignUp> response) {
                HelperMethod.dismissProgressDialog();
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignUp> call, Throwable t) {
                HelperMethod.dismissProgressDialog();
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSpinner() {

        apiServices.getPhoneKeys().enqueue(new Callback<PhoneKeys>() {
            @Override
            public void onResponse(@NonNull Call<PhoneKeys> call, @NonNull Response<PhoneKeys> response) {
                if (response.isSuccessful() && response.body() != null) {
                    keys = response.body().getData();
                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item
                            , keys);
                    RegisterSpinnerPhoneKeys.setAdapter(spinnerAdapter);

                } else {
                    Toast.makeText(getActivity(), "Error : " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PhoneKeys> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "Error : " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.Register_imageView_ShowPassword, R.id.Register_TextView_Rules, R.id.Register_Button_Login, R.id.Register_ImageView_Facebook, R.id.Register_ImageView_Twitter, R.id.Register_ImageView_Google})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Register_imageView_ShowPassword:
                onShowHidePassword();
                break;
            case R.id.Register_TextView_Rules:
                break;
            case R.id.Register_Button_Login:
                initValidateData();
                break;
            case R.id.Register_ImageView_Facebook:
                break;
            case R.id.Register_ImageView_Twitter:
                break;
            case R.id.Register_ImageView_Google:
                break;
        }
    }

    private void onShowHidePassword() {
        if (passwordNotVisible == 1) {
            RegisterEditTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            RegisterImageViewShowPassword.setImageResource(R.drawable.eyee);
            passwordNotVisible = 0;
        } else {

            RegisterEditTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            RegisterImageViewShowPassword.setImageResource(R.drawable.eye);
            passwordNotVisible = 1;
        }
        RegisterEditTextPassword.setSelection(RegisterEditTextPassword.length());


    }
}
