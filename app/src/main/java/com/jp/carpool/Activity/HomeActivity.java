package com.jp.carpool.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.jp.carpool.Adapters.PostAdapter;
import com.jp.carpool.Data.postData;
import com.jp.carpool.Data.userInfoData;
import com.jp.carpool.Helpers.SwipperHelper;
import com.jp.carpool.NotificationServer.NotificationSender;
import com.jp.carpool.R;
import com.jp.carpool.Helpers.PostHelper;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class HomeActivity extends AppCompatActivity {

    FloatingActionButton idPost;
    private FirebaseAuth firebaseAuth;
    private SwipeRefreshLayout swipeRefresh;
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton idShare, idProfile, idLogout;
    SwipeMenuListView idListView;
    PostAdapter postAdapter;
    ArrayList<postData> arrLstPost = new ArrayList<postData>();
    PostHelper pstHlpr = new PostHelper();
    FirebaseUser user;
    SwipperHelper creator;
    Context context;
    FirebaseDatabase mDatabase;
    DatabaseReference mDatabaseRef;
    userInfoData currentUserData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = this;
        idListView = (SwipeMenuListView) findViewById(R.id.idSwipeListview);
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.menu1);
        idPost = (FloatingActionButton)findViewById(R.id.idPost);
        idShare = (FloatingActionButton) findViewById(R.id.idShare);
        idProfile = (FloatingActionButton) findViewById(R.id.idProfile);
        idLogout = (FloatingActionButton) findViewById(R.id.idLogout);
        firebaseAuth = FirebaseAuth.getInstance();
        postAdapter = new PostAdapter(this, arrLstPost);
        idListView.setAdapter(postAdapter);
        user = FirebaseAuth.getInstance().getCurrentUser();
/*************************Check user is present and  have Car*********************************/
        if(user == null) {
            Logout(null);
        }
        idPost.setVisibility(FloatingActionButton.GONE);
        getCurrentUserDetail();
/************************NotificationReceiver*********************************/
        FirebaseMessagingService msg = new FirebaseMessagingService(){
            @Override
            public void onMessageReceived(RemoteMessage remoteMessage) {
                // ...

                // TODO(developer): Handle FCM messages here.
                // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
                Log.d("HomeActivity", "From: " + remoteMessage.getFrom());

                // Check if message contains a data payload.
                if (remoteMessage.getData().size() > 0) {
                    Log.d("HomeActivity", "Message data payload: " + remoteMessage.getData());
                }

                // Check if message contains a notification payload.
                if (remoteMessage.getNotification() != null) {
                    Log.d("HomeActivity", "Message Notification Body: " + remoteMessage.getNotification().getBody());
                }

                // Also if you intend on generating your own notifications as a result of a received FCM
                // message, here is where that should be initiated. See sendNotification method below.
            }
        };


/************************Swipe to refresh COMPONENTS**************************/
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setRefreshing(true);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                arrLstPost.clear();
                postAdapter.notifyDataSetChanged();
                getDaywisePost();
                Log.i("TAG", "onRefresh called from SwipeRefreshLayout");
            }
        });

/************************LIST VIEW COMPONENTS**************************/
        //Handle onclick listner here remaining
        creator = new SwipperHelper(this);
        //set MenuCreator
        idListView.setMenuCreator(creator);
        // set SwipeListener

        idListView.setOnItemClickListener(new SwipeMenuListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(), "Post Id "+arrLstPost.get(i).getPostId(), Toast.LENGTH_SHORT).show();
                //finish();
                Intent myintent = new Intent(HomeActivity.this, PostDetailsActivity.class);
                myintent.putExtra("postItem", arrLstPost.get(i));
                startActivity(myintent);
            }
        });

        idListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                swipeRefresh.setEnabled(false);
                // swipe start
                if (arrLstPost.get(position).getUserId().toString().equals(user.getUid().toString())) {
                    // Toast.makeText(getApplicationContext(), "User Match "+arrLstPost.get(position).getUserId().toString()+"=="+user.getUid().toString(), Toast.LENGTH_LONG).show();
                } else {
                    //  Toast.makeText(getApplicationContext(), "User Not Match", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
                swipeRefresh.setEnabled(true);
            }

        });

        idListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                // postData value = new postAdapter.getItem(position);
                switch (index) {
                    case 0: // -->confirm & edit
                        if (arrLstPost.get(position).getUserId().toString().equals(user.getUid().toString())) {
                            //   Toast.makeText(getApplicationContext(), "User Match "+arrLstPost.get(position).getUserId().toString()+"=="+user.getUid().toString(), Toast.LENGTH_LONG).show();
                        } else {
                            //  Toast.makeText(getApplicationContext(), "User Not Match", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 1:// -->Call & delete
                        if (arrLstPost.get(position).getUserId().toString().equals(user.getUid().toString())) {
                            //   Toast.makeText(getApplicationContext(), "User Match "+arrLstPost.get(position).getUserId().toString()+"=="+user.getUid().toString(), Toast.LENGTH_LONG).show();
                            pstHlpr.deletePost(postAdapter, arrLstPost, position);
                            getDaywisePost();
                            Toast.makeText(getApplicationContext(), "Deleting Post", Toast.LENGTH_SHORT).show();
                        } else {
                            //  Toast.makeText(getApplicationContext(), "User Not Match", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            intent.setData(Uri.parse("tel:" + arrLstPost.get(index).toString()));
                            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                Toast.makeText(getApplicationContext(), "Please Grant Application Call Permission", Toast.LENGTH_SHORT).show();
                            }
                            else
                                startActivity(intent);
                        }
                       // Toast.makeText(getApplicationContext(), "Action 2 for", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
/**********************End LIST VIEW COMPONENTS**********************/
        //postAdapter.notifyDataSetChanged();
        //getDaywisePost();

    }

    @Override
    public void onResume() {
        super.onResume();
        arrLstPost.clear();
        postAdapter.notifyDataSetChanged();
        getDaywisePost();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //*** setOnQueryTextFocusChangeListener ***
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchQuery) {
//                homeScreenAdapter.filter(searchQuery.toString().trim());
//                abc.invalidate();
                return true;
            }
        });

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when collapsed
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true;  // Return true to expand action view
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

/*******************************User define Methods*********************************/

private boolean getCurrentUserDetail(){
    mDatabase = FirebaseDatabase.getInstance();
    Log.d("TAG","db ref: "+mDatabase.getReference("users"));
    mDatabaseRef = mDatabase.getReference("users");

    mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            currentUserData = dataSnapshot.child(user.getUid()).getValue(userInfoData.class);
            if(user != null) {
                Log.d("TAG", "Read Current user Data " +currentUserData.getFullName());
                if(currentUserData.getIsCar())
                {
                    idPost.setVisibility(FloatingActionButton.VISIBLE);
                }
            }
            else
                Log.d("TAG", "Cant reach to Server");
        }

        @Override
        public void onCancelled(DatabaseError error) {
            // Failed to read value
            Log.d("TAG", "Failed to read value.", error.toException());
            makeText(HomeActivity.this, "Cant reach to Server", LENGTH_LONG).show();
        }
    });
    return true;
}


    public void getDaywisePost() {
        /*give (post Adapter -> for notify data update) and arrLst to helper for get data from DB*/
        //pstHlpr.getDayWisePost(postAdapter, arrLstPost);
        // postData pd = arrLstPost.get(0);
        //  Log.v("HomeActivity","arrListPost (0.MoNo)--->"+pd.getMoNo());
        // postAdapter = new PostAdapter(getApplicationContext(),arrLstPost);
        //   idListView.setAdapter(postAdapter);
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mDatabase.getReference("posts/" + pstHlpr.getTodayToken());
        swipeRefresh.setRefreshing(true);
        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrLstPost.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    postData post = postSnapshot.getValue(postData.class);
                    arrLstPost.add(post);
                    Log.e("Post ID : ", post.getPostId());
                }
                for(int i=0;i< arrLstPost.size();i++)
                    Log.e("Uid : ", arrLstPost.get(i).getUserId());
                postAdapter.notifyDataSetChanged();
                swipeRefresh.setRefreshing(false);

            }

            @Override
            public void onCancelled(DatabaseError error){
                // Failed to read value
                Log.d("TAG", "Failed to read value.");
                swipeRefresh.setRefreshing(false);
            }
        });

    }

    /*On Click methods for floating action button*/
    public void Post(View V) {

//        Intent intent = new Intent(getApplicationContext(), PostActivity.class);
//        startActivity(intent);

        Intent myintent = new Intent(HomeActivity.this, PostActivity.class);
        myintent.putExtra("currentUserData", currentUserData);
        startActivity(myintent);

    }

    public void Profile(View V) {
        NotificationSender notifyMe = new NotificationSender();
        notifyMe.sendNotification();
    }

    public void Share(View V) {
    }

    public void Logout(View V) {
        Toast.makeText(this, "loging out", Toast.LENGTH_LONG).show();
        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    finish();
//                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
//                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
                }
            }
        };
        firebaseAuth.addAuthStateListener(authListener);
        firebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
