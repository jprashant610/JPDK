package com.jp.carpool.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jp.carpool.Adapters.PostAdapter;
import com.jp.carpool.Data.postData;
import com.jp.carpool.Helpers.SwipperHelper;
import com.jp.carpool.R;
import com.jp.carpool.Helpers.PostHelper;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    Button idPost;
    private FirebaseAuth firebaseAuth;

    FloatingActionMenu materialDesignFAM;
    FloatingActionButton idShare,idProfile,idLogout;
    SwipeMenuListView idListView;
    PostAdapter postAdapter;
    ArrayList<postData> arrLstPost = new ArrayList<postData>();
    PostHelper pstHlpr =new PostHelper();
    FirebaseUser user;
    SwipperHelper creator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        idListView= (SwipeMenuListView) findViewById(R.id.idSwipeListview);
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.menu1);
        idShare = (FloatingActionButton) findViewById(R.id.idShare);
        idProfile = (FloatingActionButton) findViewById(R.id.idProfile);
        idLogout = (FloatingActionButton) findViewById(R.id.idLogout);
        firebaseAuth = FirebaseAuth.getInstance();
        postAdapter = new PostAdapter(this,arrLstPost);
        idListView.setAdapter(postAdapter);
        user = FirebaseAuth.getInstance().getCurrentUser();

        //Handle onclick listner here remaining
        creator = new SwipperHelper(this);
        //set MenuCreator

        idListView.setMenuCreator(creator);

        // set SwipeListener
        idListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
                if(arrLstPost.get(position).getUserId().toString().equals(user.getUid().toString())){

                    Toast.makeText(getApplicationContext(), "User Match "+arrLstPost.get(position).getUserId().toString()+"=="+user.getUid().toString(), Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "User Not Match", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        idListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
               // postData value = new postAdapter.getItem(position);
                switch (index) {
                    case 0: // -->confirm & edit
                            if(arrLstPost.get(position).getUserId().toString().equals(user.getUid().toString())){
                                Toast.makeText(getApplicationContext(), "User Match "+arrLstPost.get(position).getUserId().toString()+"=="+user.getUid().toString(), Toast.LENGTH_LONG).show();

                            }else{
                                Toast.makeText(getApplicationContext(), "User Not Match", Toast.LENGTH_SHORT).show();
                            }
                        break;
                    case 1:// -->Call & delete
                        Toast.makeText(getApplicationContext(), "Action 2 for ", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }});
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

/*User define Methods*/
    public void getDaywisePost()
    {
       pstHlpr.getDayWisePost(pstHlpr.getTodayToken(),postAdapter,arrLstPost);
       // postData pd = arrLstPost.get(0);
        //  Log.v("HomeActivity","arrListPost (0.MoNo)--->"+pd.getMoNo());
       // postAdapter = new PostAdapter(getApplicationContext(),arrLstPost);
     //   idListView.setAdapter(postAdapter);

    }

    /*On Click methods for floating action button*/
    public void Post(View V) {
        Intent intent = new Intent(getApplicationContext(), PostActivity.class);
        startActivity(intent);
    }
    public void Logout(View V){
        Toast.makeText(this,"loging out",Toast.LENGTH_LONG).show();
        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    finish();
                    //startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        };
        firebaseAuth.addAuthStateListener(authListener);
        firebaseAuth.getInstance().signOut();
    }

}
