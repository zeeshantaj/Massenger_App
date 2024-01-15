package com.example.massenger_application.Settings;

import static com.example.massenger_application.Settings.Settings_Home_Fragment.setProfileInfo;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.auth.User;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Setting_Profile_Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.setting_profile_fragment,container,false);
    }

    private TextView name,status,number;
    private CircleImageView imageView;
    private CardView userNameCard,statusCard;
    private ActivityResultLauncher<Intent> imagePickLauncher;
    private Uri selectedImageUri;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagePickLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result ->{

            if (result.getResultCode() == Activity.RESULT_OK){
                Intent data = result.getData();
                if (data != null && data.getData() != null){
                    selectedImageUri =  data.getData();
                }
            }
                });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = view.findViewById(R.id.textView2);
        status = view.findViewById(R.id.textView6);
        imageView = view.findViewById(R.id.circleImageView2);
        userNameCard = view.findViewById(R.id.userNameCard);
        statusCard = view.findViewById(R.id.statusCard);

          userNameCard.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  showCustomSaveDialog();
              }
          });
          setProfileInfo(getActivity(),imageView,name,status);
          imageView.setOnClickListener(v -> {
              ImagePicker.with(this)
                      .cropSquare()	    			//Crop image(Optional), Check Customization for more option
                      .compress(512)			//Final image size will be less than 4 MB(Optional)
                      .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                      .createIntent(new Function1<Intent, Unit>() {
                          @Override
                          public Unit invoke(Intent intent) {
                              imagePickLauncher.launch(intent);
                              return null;
                          }
                      });
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
