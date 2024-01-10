package com.example.massenger_application.Login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.massenger_application.Animation.ShakeAnimation;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.hbb20.CountryCodePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.massenger_application.R;

import java.util.concurrent.TimeUnit;

public class Number_Verification_Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.num_verification_fragment,container,false);
    }
    private TextInputEditText numberEdit;
    private FirebaseAuth auth;
    private TextView termTxt;
    private Button nextBtn;
    private CountryCodePicker countryCodePicker;
    private ProgressBar progressBar;
    private ImageView phoneImg;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auth = FirebaseAuth.getInstance();
        numberEdit = view.findViewById(R.id.phoneNumberEditText);
        termTxt = view.findViewById(R.id.termText);
        progressBar = view.findViewById(R.id.numProgress);
        countryCodePicker = view.findViewById(R.id.countryCodePicker);
        phoneImg = view.findViewById(R.id.phone_img);

        nextBtn = view.findViewById(R.id.next1);
        nextBtn.setEnabled(true);


        setTermTxt();
        nextBtn.setOnClickListener((v -> {
            String code = countryCodePicker.getSelectedCountryCode();
            String typeNum = numberEdit.getText().toString().trim(); // Trim to remove leading/trailing spaces
            String plus = "+";
            String phoneNumber = plus + code + typeNum;

// Check if the number is empty
            if (typeNum.isEmpty()) {
                ShakeAnimation.setAnimation(getActivity(),phoneImg);
                ShakeAnimation.setAnimation(getActivity(),numberEdit);

                numberEdit.setError("Number is empty");
                return;
            }

// Use libphonenumber to validate the phone number
            PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
            try {
                Phonenumber.PhoneNumber parsedPhoneNumber = phoneNumberUtil.parse(phoneNumber, null);
                boolean isValid = phoneNumberUtil.isValidNumber(parsedPhoneNumber);

                if (isValid) {
                    // Valid phone number
                    Log.e("MyApp", "Valid phone number: " + phoneNumber);
                    Toast.makeText(getActivity(), "Valid Phone number", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.VISIBLE);

                    nextBtn.setEnabled(false);
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            phoneNumber,
                            120,             // Timeout duration
                            TimeUnit.SECONDS,
                            getActivity(),           // Activity (or context) for callback
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(PhoneAuthCredential credential) {
                                    progressBar.setVisibility(View.GONE);
                                    Log.e("MyApp", "credential" + credential.toString());
                                }

                                @Override
                                public void onVerificationFailed(FirebaseException e) {
                                    // Handle verification failure, e.g., display an error message.
                                    progressBar.setVisibility(View.GONE);
                                    Log.e("MyApp", "failed" + e.getLocalizedMessage());
                                    Toast.makeText(getActivity(), "Failed "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                                    // The SMS verification code has been sent to the provided phone number.
                                    // You can use this verificationId to verify the code later.


                                    SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
                                    sharedViewModel.setVerificationId(verificationId);

                                    Log.e("MyApp", "verificationInNumberFragment  ->  " + verificationId);
                                    progressBar.setVisibility(View.VISIBLE);
                                    LoginActivity.setFragment(new OTP_Fragment(),getActivity());

                                }
                            });


                } else {
                    // Invalid phone number
                    // Proceed with the valid phone number
                    ShakeAnimation.setAnimation(getActivity(),phoneImg);
                    ShakeAnimation.setAnimation(getActivity(),numberEdit);

                    numberEdit.setError("Invalid phone number");
                    nextBtn.setEnabled(true);

                }
            } catch (NumberParseException e) {
                // Number parsing failed
                ShakeAnimation.setAnimation(getActivity(),phoneImg);
                ShakeAnimation.setAnimation(getActivity(),numberEdit);

                nextBtn.setEnabled(true);
                numberEdit.setError("Number parsing failed");
                e.printStackTrace();
            }
        }));

    }
    private void setTermTxt() {
        SpannableString spannableString = new SpannableString(termTxt.getText());

        String yourString = "By tapping Sign in or Create account, you agree to our Term of Service. Learn how we process your data in our Privacy Policy and Cookies Policy";

        // Find the starting index of "Term of Service"
        int startIndexOfTerm = yourString.indexOf("Term of Service");
        int startIndexOfTerm1 = yourString.indexOf("Privacy Policy");
        int startIndexOfTerm2 = yourString.indexOf("Cookies Policy");

        int endIndexOfTerm = startIndexOfTerm + "Term of Service".length();
        int endIndexOfTerm1 = startIndexOfTerm1 + "Privacy Policy".length();
        int endIndexOfTerm2 = startIndexOfTerm2 + "Cookies Policy".length();

        ClickableSpan termOfServiceSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(getActivity(), "Under process", Toast.LENGTH_SHORT).show();
            }
        };

        ClickableSpan privacyPolicySpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(getActivity(), "Under process", Toast.LENGTH_SHORT).show();
            }
        };

        ClickableSpan cookiesPolicySpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(getActivity(), "Under process", Toast.LENGTH_SHORT).show();
            }
        };

        spannableString.setSpan(termOfServiceSpan, startIndexOfTerm, endIndexOfTerm, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(privacyPolicySpan, startIndexOfTerm1, endIndexOfTerm1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(cookiesPolicySpan, startIndexOfTerm2, endIndexOfTerm2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        termTxt.setText(spannableString);

        termTxt.setMovementMethod(LinkMovementMethod.getInstance());
    }
}