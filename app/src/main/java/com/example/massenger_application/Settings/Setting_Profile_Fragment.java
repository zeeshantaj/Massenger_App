package com.example.massenger_application.Settings;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.massenger_application.FragmentUtil.FragmentUtils;
import com.example.massenger_application.Model.Users;
import com.example.massenger_application.R;
import com.example.massenger_application.Utils.FirebaseUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.auth.User;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class Setting_Profile_Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.setting_profile_fragment,container,false);
    }

    private TextView name,status,number;
    private CircleImageView imageView;
    private CardView userNameCard,statusCard;
      @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = view.findViewById(R.id.textView2);
        status = view.findViewById(R.id.textView6);
        imageView = view.findViewById(R.id.circleImageView2);
        number = view.findViewById(R.id.textView9);
        userNameCard = view.findViewById(R.id.userNameCard);
        statusCard = view.findViewById(R.id.statusCard);

          DocumentReference userRef = FirebaseUtils.currentUserDetails();
          userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
              @Override
              public void onSuccess(DocumentSnapshot documentSnapshot) {
                  Users users = documentSnapshot.toObject(Users.class);
                  if (users != null) {
                      name.setText(users.getName());
                      status.setText(users.getStatus());
                      number.setText(users.getPhoneNumber());
                      Glide.with(getActivity())
                              .load(users.getImage())
                              .into(imageView);
                  }
                  else {
                      Toast.makeText(getActivity(), "User is not exist", Toast.LENGTH_SHORT).show();
                  }
              }
          }).addOnFailureListener(new OnFailureListener() {
              @Override
              public void onFailure(@NonNull Exception e) {
                  Toast.makeText(getActivity(), "Error "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
              }
          });


          userNameCard.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  showCustomSaveDialog();
              }
          });
      }
    private void showCustomSaveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View dialogView = getLayoutInflater().inflate(R.layout.name_bottom_dialogue, null);
        builder.setView(dialogView);

        EditText editTextFileName = dialogView.findViewById(R.id.editText);
        TextView buttonSave = dialogView.findViewById(R.id.textView10);
        TextView buttonCancel = dialogView.findViewById(R.id.textView11);

        editTextFileName.setText(name.getText());

        AlertDialog dialog = builder.create();
        dialog.show();

        buttonSave.setOnClickListener(v -> {
                dialog.dismiss();
        });
        buttonCancel.setOnClickListener(v -> {

                dialog.dismiss();
        });


    }

}
