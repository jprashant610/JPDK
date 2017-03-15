package com.jp.carpool.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.jp.carpool.Manifest;
import com.jp.carpool.R;

public class LoginActivity extends AppCompatActivity {

    TextView idSignUp;
    EditText editITSID;
    EditText editPassword;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        editITSID = (EditText) findViewById(R.id.editITSID);
        editPassword = (EditText) findViewById(R.id.editPassword);
        idSignUp= (TextView) findViewById(R.id.idSignUp);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();


        //if getCurrentUser does not returns null
        if(firebaseAuth.getCurrentUser() != null){
            //that means user is already logged in
            //so close this activity
            Log.w("TAG", "Loged in Success");
            finish();
            //and open after login activity
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }

        idSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }
        });
    }

    public void login(View v){
        String email = editITSID.getText().toString().trim();
        String password  = editPassword.getText().toString().trim();

        progressDialog.setMessage("Loging Please Wait...");
        progressDialog.show();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @SuppressWarnings({"ThrowableResultOfMethodCallIgnored", "ConstantConditions"})
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        if(task.isSuccessful()){
                            //start the profile activity
                            Log.w("TAG", "signin success", task.getException());
                            Toast.makeText(LoginActivity.this,"Login Successfull",Toast.LENGTH_LONG).show();
                            finish();
                           // startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else{
                            Toast.makeText(LoginActivity.this,"Registration Error "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            Log.w("TAG", "signin failed", task.getException());
                        }
                    }
                });
      //  startActivity(new Intent(LoginActivity.this,HomeActivity.class));
    }


}
