package com.jp.carpool.Helpers;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jp.carpool.Adapters.PostAdapter;
import com.jp.carpool.Data.postData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

import static android.widget.Toast.makeText;

/**
 * Created by dkhairnar on 2/3/2017.
 */

public class postHelper {
    private String yearString;
    private String monthString;
    private String dayString;

    private String hourString;
    private String minutString;
    private String secondString;
    private String milliSecondString;
    private String postId;

    FirebaseDatabase mDatabase;
    DatabaseReference mDatabaseRef;

    public postHelper(){
        // create child link "posts/YYYY/MM/DD/currenttime+miliseconds"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss:SSS");
        String currentDateandTime = sdf.format(new java.util.Date());

        StringTokenizer tokens = new StringTokenizer(currentDateandTime, ".");
        String dateString = tokens.nextToken();// this will contain "Fruit"
        String timeString = tokens.nextToken();

        tokens = new StringTokenizer(dateString,"-");
         yearString = tokens.nextToken();
         monthString = tokens.nextToken();
         dayString = tokens.nextToken();

        tokens = new StringTokenizer(timeString,":");
         hourString = tokens.nextToken();
         minutString = tokens.nextToken();
         secondString = tokens.nextToken();
         milliSecondString = tokens.nextToken();
        postId = yearString+monthString+dayString+hourString+minutString+secondString+milliSecondString;
        Log.d("TAG","Post ID : "+ postId);
    }

    public String getPostId()
    {
        return postId;
    }

    public void uploadPost(postData post){
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mDatabase.getReference("posts");
        mDatabaseRef.child(yearString).child(monthString).child(dayString).child(postId).setValue(post);
    }

    public String getTodayToken(){
        return yearString+"/"+monthString+"/"+dayString;
    }

    public void getDayWisePost(String todayToken, final PostAdapter postAdapter, final ArrayList<postData> arrLstPost) {

        mDatabase = FirebaseDatabase.getInstance();
        Log.d("TAG","db ref: "+mDatabase.getReference("posts/"+todayToken));
        mDatabaseRef = mDatabase.getReference("posts/"+todayToken);

        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    postData post = postSnapshot.getValue(postData.class);
                    arrLstPost.add(post);
                    Log.e("Post ID : ", post.getPostId());
                }
                for(int i=0;i< arrLstPost.size();i++)
                    Log.e("Uid : ", arrLstPost.get(i).getUserId());
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error){
                // Failed to read value
                Log.d("TAG", "Failed to read value.");
            }
        });

    }
}
