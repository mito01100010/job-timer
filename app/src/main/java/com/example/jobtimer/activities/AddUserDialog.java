package com.example.jobtimer.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.jobtimer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddUserDialog extends AppCompatDialogFragment {

    public static final String TAG = "TAG";
    private EditText editTextUserEmail;
    private EditText editTextPassword;
    private EditText editTextPhoneNumber;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private String userID;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_user_dialog, null);

        builder.setView(view)
                .setTitle("Add User")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String email = editTextUserEmail.getText().toString().trim();
                        String password = editTextPassword.getText().toString().trim();
                        String phone = editTextPhoneNumber.getText().toString().trim();

                        if(TextUtils.isEmpty(email)){
                            Toast.makeText(getContext(),"Email required.", Toast.LENGTH_SHORT).show();
                            return;
                        }else if(TextUtils.isEmpty(password)){
                            Toast.makeText(getContext(),"Password required.", Toast.LENGTH_SHORT).show();
                            return;
                        }else if(password.length() < 6){
                            Toast.makeText(getContext(),"Short password.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    //Toast.makeText(getActivity(), "User Created", Toast.LENGTH_SHORT).show();
                                    userID = firebaseAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
                                    Map<String,Object> user = new HashMap<>();
                                    user.put("phoneNumber", phone);
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d(TAG, "onSuccess: user profile is created uid: " + userID);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(Exception e) {
                                            Log.d(TAG, "onFailure: " + e.toString());
                                        }
                                    });
                                }else{
                                    Toast.makeText(getContext(), "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    //ne bachka..
                                }
                            }
                        });
                    }
                });

        editTextUserEmail = view.findViewById(R.id.editTexAddUserEmail);
        editTextPassword = view.findViewById(R.id.editTexAddUserPassword);
        editTextPhoneNumber = view.findViewById(R.id.editTexAddUserPhoneNumber);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        return builder.create();
    }
}
