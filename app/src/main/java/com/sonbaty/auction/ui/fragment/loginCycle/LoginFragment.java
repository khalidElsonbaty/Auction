package com.sonbaty.auction.ui.fragment.loginCycle;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sonbaty.auction.R;
import com.sonbaty.auction.data.api.ApiClient;
import com.sonbaty.auction.data.api.ApiServices;
import com.sonbaty.auction.data.local.SharedPreferencesManger;
import com.sonbaty.auction.data.model.phoneKeys.PhoneKeys;
import com.sonbaty.auction.data.model.signIn.SignIn;
import com.sonbaty.auction.helper.HelperMethod;
import com.sonbaty.auction.helper.Validation;
import com.sonbaty.auction.ui.activity.HomeActivity;

import java.util.ArrayList;
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
public class LoginFragment extends Fragment {

    View view;
    @BindView(R.id.Login_EditText_UserName)
    EditText LoginEditTextUserName;
    @BindView(R.id.Login_Spinner_Phone_Keys)
    Spinner LoginSpinnerPhoneKeys;
    @BindView(R.id.Login_EditText_Password)
    EditText LoginEditTextPassword;
    @BindView(R.id.Login_CheckBox_RememberMe)
    CheckBox LoginCheckBoxRememberMe;
    Unbinder unbinder;
    ApiServices apiServices;
    DatabaseReference reference;
    Validation validation;
    @BindView(R.id.Login_imageView_ShowPassword)
    ImageView LoginImageViewShowPassword;
    private List<String> keys = new ArrayList<>();
    private String phone;
    private String password;
    private String userId;
    private String code;
    private int passwordNotVisible = 1;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        intiObjects();
        intiSpinner();
        initUserShared();
        return view;
    }

    private void initUserShared() {
        String user = SharedPreferencesManger.LoadStringData(getActivity(), "username");
        String pass = SharedPreferencesManger.LoadStringData(getActivity(), "password");
        String codePlus = SharedPreferencesManger.LoadStringData(getActivity(), "code");
        LoginEditTextUserName.setText(user);
        LoginEditTextPassword.setText(pass);
        String[] keyTest = keys.toArray(new String[keys.size()]);
        if (keys.size() != 0) {
            for (int i = 1; i < keyTest.length; i++) {
                if (keyTest[i].equalsIgnoreCase(codePlus)) {
                    LoginSpinnerPhoneKeys.setSelection(i);
                }
            }
        }
    }


    private void intiObjects() {
        apiServices = ApiClient.getGeneral().create(ApiServices.class);
        validation = new Validation();
        reference = FirebaseDatabase.getInstance().getReference("users");
    }

    private void intiViews() {
        phone = LoginEditTextUserName.getText().toString().trim();
        password = LoginEditTextPassword.getText().toString().trim();
        code = LoginSpinnerPhoneKeys.getSelectedItem().toString();
    }


    private void intiSpinner() {
        apiServices.getPhoneKeys().enqueue(new Callback<PhoneKeys>() {
            @Override
            public void onResponse(@NonNull Call<PhoneKeys> call, @NonNull Response<PhoneKeys> response) {
                if (response.isSuccessful() && response.body() != null) {
                    keys = response.body().getData();
                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item
                            , keys);
                    LoginSpinnerPhoneKeys.setAdapter(spinnerAdapter);

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

    @OnClick({R.id.Login_imageView_ShowPassword, R.id.Login_Button_Login, R.id.Login_ImageView_Facebook, R.id.Login_ImageView_Twitter, R.id.Login_ImageView_Google, R.id.Login_TextView_Forget_Password, R.id.Login_TextView_Register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Login_imageView_ShowPassword:
                onShowHidePassword();
                break;
            case R.id.Login_Button_Login:
                onSignIn();
                break;
            case R.id.Login_ImageView_Facebook:
                break;
            case R.id.Login_ImageView_Twitter:
                break;
            case R.id.Login_ImageView_Google:
                break;
            case R.id.Login_TextView_Forget_Password:
                HelperMethod.replace(new ForgetPasswordFragment(), getActivity().getSupportFragmentManager(), R.id.Login_FrameLayout_Container, null, null);
                break;
            case R.id.Login_TextView_Register:
                HelperMethod.replace(new RegisterFragment(), getActivity().getSupportFragmentManager(), R.id.Login_FrameLayout_Container, null, null);
                break;
        }
    }

    private void onShowHidePassword() {
        if (passwordNotVisible == 1) {
            LoginEditTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            LoginImageViewShowPassword.setImageResource(R.drawable.eyee);
            passwordNotVisible = 0;
        } else {

            LoginEditTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            LoginImageViewShowPassword.setImageResource(R.drawable.eye);
            passwordNotVisible = 1;
        }


        LoginEditTextPassword.setSelection(LoginEditTextPassword.length());


    }

    private void onSignIn() {
        intiViews();
        boolean allowSave = true;
        if (validation.isNullOrEmpty(phone)) {
            LoginEditTextUserName.setError(getString(R.string.login_error_message_phone));
            allowSave = false;
        }

        if (validation.isNullOrEmpty(password) || validation.isValidPassword(password, true)) {
            LoginEditTextPassword.setError(getString(R.string.login_error_message_password));
            allowSave = false;
        }

        if (allowSave) {
            reference.orderByChild("phone").equalTo(phone).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot datas : dataSnapshot.getChildren()) {
                        userId = datas.getKey();
                        onLoginRequest(userId);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

        } else {
            Toast.makeText(getActivity(), getString(R.string.login_error_message_not_found), Toast.LENGTH_LONG).show();
            emptyInputEditText();

        }
    }

    private void emptyInputEditText() {
        LoginEditTextUserName.setText(null);
        LoginEditTextPassword.setText(null);
    }

    private void onLoginRequest(String userId) {
        intiRememberMe();
        HelperMethod.showProgressDialog(getActivity(), "Loading");
        apiServices.sendUserLoginRequest(phone, password, userId, code).enqueue(new Callback<SignIn>() {
            @Override
            public void onResponse(@NonNull Call<SignIn> call, @NonNull Response<SignIn> response) {
                HelperMethod.dismissProgressDialog();
                if (response.isSuccessful() && response.body() != null) {
                    startActivity(new Intent(getActivity(), HomeActivity.class));
                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SignIn> call, @NonNull Throwable t) {
                HelperMethod.dismissProgressDialog();
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void intiRememberMe() {
        if (LoginCheckBoxRememberMe.isChecked()) {

            SharedPreferencesManger.SaveData(getActivity(), "checkbox", "True");
            String user = LoginEditTextUserName.getText().toString();
            SharedPreferencesManger.SaveData(getActivity(), "username", user);
            String pass = LoginEditTextPassword.getText().toString();
            SharedPreferencesManger.SaveData(getActivity(), "password", pass);
            String countryCode = LoginSpinnerPhoneKeys.getSelectedItem().toString();
            SharedPreferencesManger.SaveData(getActivity(), "code", countryCode);



        } else {
            SharedPreferencesManger.SaveData(getActivity(), "checkbox", "False");
            SharedPreferencesManger.SaveData(getActivity(), "username", "");
            SharedPreferencesManger.SaveData(getActivity(), "password", "");
            SharedPreferencesManger.clean(getActivity());

        }
    }

}
