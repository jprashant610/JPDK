package com.jp.carpool;

import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.api.model.StringList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jp.carpool.Data.userInfoData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    //defining firebaseauth object
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

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase =  FirebaseDatabase.getInstance();
        mDatabaseRef = mDatabase.getReference("users");

        //if getCurrentUser does not returns null
        if(firebaseAuth.getCurrentUser() != null){
            //that means user is already logged in
            //so close this activity
            finish();

            //and open profile activity
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }
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

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @SuppressWarnings({"ThrowableResultOfMethodCallIgnored", "ConstantConditions"})
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            Toast.makeText(SignupActivity.this,"Registration Successfull",Toast.LENGTH_LONG).show();
                            wirteUserInfo();
                            finish();
                            //startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else{
                            //display some message here
                            Toast.makeText(SignupActivity.this,"Registration Error "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }


    public void wirteUserInfo()
    {
        if(validateForm())
        {
            Log.d("TAG","User data is valid");
            String email = editITSID.getText().toString().trim();
            String password = editPassword.getText().toString().trim();
            String FullName = editPassword.getText().toString().trim();
            String MoNo = editPassword.getText().toString().trim();
            String ITSID = editPassword.getText().toString().trim();
            String Password = editPassword.getText().toString().trim();
            String LicenceNo = editPassword.getText().toString().trim();
            String CarName = editPassword.getText().toString().trim();
            String CarNo = editPassword.getText().toString().trim();
            String NumberSeat = editPassword.getText().toString().trim();

            FirebaseUser user = firebaseAuth.getCurrentUser();
            if(user == null) {
                Log.d("TAG","Firebase user is NULL");
                return;
            }
            String uid = user.getUid();
            Log.d("TAG","User UID ="+uid);

            userInfoData userData = new userInfoData();
            userData.setEmail(email);
            userData.setPassword(password);
            userData.setFullName(FullName);
            userData.setMoNo(MoNo);
            userData.setITSID(ITSID);
            userData.setPassword(Password);
            userData.setLicenceNo(LicenceNo);
            userData.setCarName(CarName);
            userData.setCarNo(CarNo);
            userData.setNumberSeat(NumberSeat);

            mDatabaseRef.setValue(userData);
//            Map<String, Object> childUpdates = new HashMap<>();
//            childUpdates.put("/listItem/" + key, FullName);
//            FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates);

        }
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

        return result;
    }

}
