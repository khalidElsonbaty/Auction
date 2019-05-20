package com.sonbaty.auction.ui.fragment.navigationCycle;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.sonbaty.auction.R;
import com.sonbaty.auction.data.api.ApiClient;
import com.sonbaty.auction.data.api.ApiServices;
import com.sonbaty.auction.data.model.contactUs.ContactUs;
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
public class ContactUsFragment extends Fragment {

    private View view;
    @BindView(R.id.Contact_Us_EditText_Name)
    EditText ContactUsEditTextName;
    @BindView(R.id.Contact_Us_EditText_Email)
    EditText ContactUsEditTextEmail;
    @BindView(R.id.Contact_Us_EditText_Message)
    EditText ContactUsEditTextMessage;
    Unbinder unbinder;
    private Validation validation;
    private ApiServices apiServices;
    private String name;
    private String eMail;
    private String message;

    public ContactUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        unbinder = ButterKnife.bind(this, view);
        initObjects();
        return view;
    }

    private void initObjects() {
        validation = new Validation();
        apiServices = ApiClient.getGeneral().create(ApiServices.class);
    }

    private void intiValidate() {
        intiViews();
        boolean allowSave = true;
        if (validation.isNullOrEmpty(name)) {
            ContactUsEditTextName.setError("تاكد من اسم المستخدم!");
            allowSave = false;
        }
        if (validation.isNullOrEmpty(eMail) || !validation.isValidEmail(eMail)) {
            ContactUsEditTextEmail.setError("تاكد من البريد الالكتروني!");
            allowSave = false;
        }
        if (validation.isNullOrEmpty(message)) {
            ContactUsEditTextMessage.setError("تاكد من وجود رسالة!");
            allowSave = false;
        }
        if (allowSave) {
            onSendContactData();
            emptyInputEditText();
        } else {
            emptyInputEditText();
        }
    }

    private void onSendContactData() {
        HelperMethod.showProgressDialog(getActivity(), "Loading");
        apiServices.sendContactUsRequest(name, eMail, message).enqueue(new Callback<ContactUs>() {
            @Override
            public void onResponse(@NonNull Call<ContactUs> call, @NonNull Response<ContactUs> response) {
                HelperMethod.dismissProgressDialog();
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<ContactUs> call, @NonNull Throwable t) {
                HelperMethod.dismissProgressDialog();
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void emptyInputEditText() {
        ContactUsEditTextName.setText(null);
        ContactUsEditTextEmail.setText(null);
        ContactUsEditTextMessage.setText(null);
    }

    private void intiViews() {
        name = ContactUsEditTextName.getText().toString().trim();
        eMail = ContactUsEditTextEmail.getText().toString().trim();
        message = ContactUsEditTextMessage.getText().toString().trim();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Contact_Us_Button_Register)
    public void onViewClicked() {
        intiValidate();
    }
}
