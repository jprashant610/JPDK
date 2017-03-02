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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jp.carpool.Data.postData;
import com.jp.carpool.R;
import com.jp.carpool.postHelper.postHelper;

public class PostActivity extends AppCompatActivity {
    Button idPostPost;
    EditText editFrom;
    EditText editTo;
    EditText pickDate;
    EditText pickTime;
    EditText editNumberSeat;

    //defining firebaseauth object
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference mDatabaseRef;


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

        firebaseAuth = FirebaseAuth.getInstance();

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

//Check user is not null
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user == null) {
            Log.d("TAG","Firebase user is NULL");
            return;
        }
        String uid = user.getUid();

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

//        mDatabase.child(userId).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                User user = dataSnapshot.getValue(User.class);
//
//                Log.d(TAG, "User name: " + user.getName() + ", email " + user.getEmail());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
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

