package com.jp.carpool.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jp.carpool.Data.userInfoData;
import com.jp.carpool.R;

public class SignupActivity extends AppCompatActivity {
    //defining firebaseauth object
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference mDatabaseRef;
    EditText editFullName;
    EditText editMoNo;
    EditText editITSID;
    EditText editPassword;
    EditText editLicenceNo;
    EditText editCarName;
    EditText editCarNo;
    EditText editNumberSeat;
    CheckBox chkIsHaveCar;
    LinearLayout carDetailView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

         editFullName =(EditText) findViewById(R.id.editFullName);
         editMoNo = (EditText) findViewById(R.id.editMoNo);
         editITSID = (EditText) findViewById(R.id.editITSID);
         editPassword = (EditText) findViewById(R.id.editPassword);
         editLicenceNo = (EditText) findViewById(R.id.editLicenceNo);
         editCarName = (EditText) findViewById(R.id.editCarName);
         editCarNo = (EditText) findViewById(R.id.editCarNo);
         editNumberSeat = (EditText) findViewById(R.id.editNumberSeat);
        chkIsHaveCar = (CheckBox)findViewById(R.id.chkIsHaveCar);
        carDetailView  = (LinearLayout)findViewById(R.id.carDetailView);
        carDetailView.setVisibility(LinearLayout.GONE);
        progressDialog = new ProgressDialog(this);


        firebaseAuth = FirebaseAuth.getInstance();
        //if getCurrentUser does not returns null
        if(firebaseAuth.getCurrentUser() != null){
            //that means user is already logged in
            //so close this activity
            finish();

            //and open profile activity
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }

        chkIsHaveCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chkIsHaveCar.isChecked()){
                    carDetailView.setVisibility(LinearLayout.VISIBLE);
                }else{
                    carDetailView.setVisibility(LinearLayout.GONE);
                }

            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void Save(View V){

        String email = editITSID.getText().toString().trim();
        String password  = editPassword.getText().toString().trim();

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        if(validateForm()) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @SuppressWarnings({"ThrowableResultOfMethodCallIgnored", "ConstantConditions"})
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            //checking if success
                            if (task.isSuccessful()) {
                                Toast.makeText(SignupActivity.this, "Registration Successfull", Toast.LENGTH_LONG).show();
                                wirteUserInfo();
                               // progressDialog.dismiss();
                                finish();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                //display some message here
                                Toast.makeText(SignupActivity.this, "Registration Error " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
        else{
            progressDialog.dismiss();
            Toast.makeText(SignupActivity.this, "Please fill Detail Correctly", Toast.LENGTH_LONG).show();
        }
      //  progressDialog.dismiss();
    }


    public void wirteUserInfo()
    {
        Log.d("TAG","User data is valid");

        String FullName = editFullName.getText().toString().trim();
        String MoNo = editMoNo.getText().toString().trim();
        String ITSID = editITSID.getText().toString().trim();
        String Password = editPassword.getText().toString().trim();
        String LicenceNo = editLicenceNo.getText().toString().trim();
        String CarName = editCarName.getText().toString().trim();
        String CarNo = editCarNo.getText().toString().trim();
        String NumberSeat = editNumberSeat.getText().toString().trim();
        boolean IsCar = chkIsHaveCar.isChecked();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user == null) {
            Log.d("TAG","Firebase user is NULL");
            return;
        }
        String uid = user.getUid();
        Log.d("TAG","User UID ="+uid);

        userInfoData userData = new userInfoData();
        userData.setEmail(ITSID);
        userData.setPassword(Password);
        userData.setFullName(FullName);
        userData.setMoNo(MoNo);
        userData.setITSID(ITSID);
        userData.setPassword(Password);
        userData.setLicenceNo(LicenceNo);
        userData.setCarName(CarName);
        userData.setCarNo(CarNo);
        userData.setNumberSeat(NumberSeat);
        userData.setIsCar(IsCar);


        mDatabase = FirebaseDatabase.getInstance();
        Log.d("TAG","db ref: "+mDatabase.getReference("users"));
        mDatabaseRef = mDatabase.getReference("users");
        mDatabaseRef.child(uid).setValue(userData);
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(editFullName.getText().toString())) {
            editFullName.setError("Required");
            result = false;
        } else {
            editFullName.setError(null);
        }

        if (TextUtils.isEmpty(editMoNo.getText().toString())) {
            editMoNo.setError("Required");
            result = false;
        } else {
            editMoNo.setError(null);
        }


        if (TextUtils.isEmpty(editITSID.getText().toString())) {
            editITSID.setError("Required");
            result = false;
        } else {
            editITSID.setError(null);
        }


        if (TextUtils.isEmpty(editPassword.getText().toString())) {
            editPassword.setError("Required");
            result = false;
        } else {
            editPassword.setError(null);
        }


        if (TextUtils.isEmpty(editLicenceNo.getText().toString())) {
            editLicenceNo.setError("Required");
            result = false;
        } else {
            editLicenceNo.setError(null);
        }

        if(chkIsHaveCar.isChecked())
        {
            if (TextUtils.isEmpty(editCarName.getText().toString())) {
                editCarName.setError("Required");
                result = false;
            } else {
                editCarName.setError(null);
            }

            if (TextUtils.isEmpty(editCarNo.getText().toString())) {
                editCarNo.setError("Required");
                result = false;
            } else {
                editCarNo.setError(null);
            }

            if (TextUtils.isEmpty(editNumberSeat.getText().toString())) {
                editNumberSeat.setError("Required");
                result = false;
            } else {
                editNumberSeat.setError(null);
            }

        }
        return result;
    }

}
