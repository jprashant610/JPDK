package com.jp.carpool.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jp.carpool.Data.postData;
import com.jp.carpool.Helpers.PostHelper;
import com.jp.carpool.Manifest;
import com.jp.carpool.R;

public class PostDetailsActivity extends AppCompatActivity {

    TextView textFullName;
    TextView textMoNo;
    TextView textCar;
    TextView textCarNumber;
    TextView textNumberSeat;
    TextView textDate;
    TextView textTime;
    TextView textFrom;
    TextView textTo;
    FirebaseDatabase mDatabase;
    DatabaseReference mDatabaseRef;
    PostHelper pstHlpr = new PostHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textFullName = (TextView) findViewById(R.id.textFullName);
        textMoNo = (TextView) findViewById(R.id.textMoNo);
        textCar = (TextView) findViewById(R.id.textCar);
        textCarNumber = (TextView) findViewById(R.id.textCarNumber);
        textNumberSeat = (TextView) findViewById(R.id.textNumberSeat);
        textDate = (TextView) findViewById(R.id.textDate);
        textTime = (TextView) findViewById(R.id.textTime);
        textFrom = (TextView) findViewById(R.id.textFrom);
        textTo = (TextView) findViewById(R.id.textTo);

        Bundle extras = getIntent().getExtras();
        postData post = new postData();
        Intent intentIncoming = getIntent();
        if(extras != null) {
            post = (postData) intentIncoming.getParcelableExtra("postItem");// OK
            Toast.makeText(getApplicationContext(), "Post Id " + post.getPostId(), Toast.LENGTH_SHORT).show();

//        mDatabase = FirebaseDatabase.getInstance();
//        Log.d("TAG", "db ref: " + mDatabase.getReference("posts/" + pstHlpr.getTodayToken() + "/" + postId));
//        mDatabaseRef = mDatabase.getReference("posts/" + pstHlpr.getTodayToken() + "/" + postId);
//        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                postData post = dataSnapshot.getValue(postData.class);
            textFullName.setText(post.getFullName());
            textMoNo.setText(post.getMoNo());
            textCar.setText(post.getCarName());
            textCarNumber.setText(post.getCarNo());
            textNumberSeat.setText(post.getNumberSeat());
            textDate.setText(post.getDate());
            textTime.setText(post.getTime());
            textFrom.setText(post.getFrom());
            textTo.setText(post.getTo());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void makeCall(View v) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + textMoNo.getText().toString()));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(intent);
        }
}
