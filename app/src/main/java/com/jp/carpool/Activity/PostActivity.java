package com.jp.carpool.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jp.carpool.Data.postData;
import com.jp.carpool.Data.userInfoData;
import com.jp.carpool.R;
import com.jp.carpool.postHelper.postHelper;

public class PostActivity extends AppCompatActivity {
    Button idPostPost;
    EditText editFrom;
    EditText editTo;
    EditText pickDate;
    EditText pickTime;
    EditText editNumberSeat;
    TextView textfullName;
    TextView textCarName;
    TextView textMono;

    String uid;


    //defining firebaseauth object
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference mDatabaseRef;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        idPostPost= (Button) findViewById(R.id.idPostPost);
        editFrom = (EditText)findViewById(R.id.editFrom);
        editTo =  (EditText)findViewById(R.id.editTo);
        pickDate =  (EditText)findViewById(R.id.pickDate);
        pickTime =  (EditText)findViewById(R.id.pickTime);
        editNumberSeat =  (EditText)findViewById(R.id.editNumberSeat);

        textfullName = (TextView)findViewById(R.id.textFullName);
        textCarName = (TextView)findViewById(R.id.textCarName);
        textMono = (TextView)findViewById(R.id.textMoNo);

        firebaseAuth = FirebaseAuth.getInstance();

        user = firebaseAuth.getCurrentUser();
        if(user == null) {
            Log.d("TAG","Firebase user is NULL");
            finish();
        }
        uid = user.getUid();
        getUserDetailForPost();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onPost(View v){

        postHelper postHelper = new postHelper();
        String postId = postHelper.getPostId();
        String From = editFrom.getText().toString().trim();
        String To = editTo.getText().toString().trim();
        String Date = pickDate.getText().toString().trim();
        String Time = pickTime.getText().toString().trim();
        String NumberSeat = editNumberSeat.getText().toString().trim();

// reead user input post data
        postData post = new postData();

        post.setFrom(From);
        post.setTo(To);
        post.setDate(Date);
        post.setTime(Time);
        post.setNumberSeat(NumberSeat);
        post.setUserId(uid);
        post.setPostId(postId);

        Log.d("TAG","User UID ="+uid);
// Set post ID using time of posting
        mDatabase = FirebaseDatabase.getInstance();
        Log.d("TAG","db ref: "+mDatabase.getReference("posts"));
        mDatabaseRef = mDatabase.getReference("posts");

        if(validateForm()) {
            postHelper.uploadPost(post);
            getUserDetailForPost();
            finish();
        }
        else
            Toast.makeText(PostActivity.this, "Please fill Detail Correctly", Toast.LENGTH_LONG).show();

     }

    private boolean getUserDetailForPost(){
        mDatabase = FirebaseDatabase.getInstance();
        Log.d("TAG","db ref: "+mDatabase.getReference("users"));
        mDatabaseRef = mDatabase.getReference("users");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 userInfoData user = dataSnapshot.child(uid).getValue(userInfoData.class);
                textfullName.setText(user.getFullName());
                textCarName.setText(user.getCarName()+"-"+user.getCarNo());
                textMono.setText(user.getMoNo());
                Log.d("TAG", "read values from db" + user.getFullName());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("TAG", "Failed to read value.", error.toException());
            }
        });
        return true;
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(editFrom.getText().toString())) {
            editFrom.setError("Required");
            result = false;
        } else {
            editFrom.setError(null);
        }

        if (TextUtils.isEmpty(editTo.getText().toString())) {
            editTo.setError("Required");
            result = false;
        } else {
            editTo.setError(null);
        }


        if (TextUtils.isEmpty(pickDate.getText().toString())) {
            pickDate.setError("Required");
            result = false;
        } else {
            pickDate.setError(null);
        }


        if (TextUtils.isEmpty(pickTime.getText().toString())) {
            pickTime.setError("Required");
            result = false;
        } else {
            pickTime.setError(null);
        }


        if (TextUtils.isEmpty(editNumberSeat.getText().toString())) {
            editNumberSeat.setError("Required");
            result = false;
        } else {
            editNumberSeat.setError(null);
        }

        return result;
    }

}

