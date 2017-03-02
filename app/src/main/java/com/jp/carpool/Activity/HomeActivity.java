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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jp.carpool.R;

public class HomeActivity extends AppCompatActivity {


LinearLayout post1,post2,post3,post4,post5,post6,post7,post8,post9,post10,post11,post12,post13;

    Button idPost;
    Button idLogout;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        idPost= (Button) findViewById(R.id.idPost);
        idLogout= (Button) findViewById(R.id.idLogout);
        firebaseAuth = FirebaseAuth.getInstance();
        post1= (LinearLayout) findViewById(R.id.post1);
        post2= (LinearLayout) findViewById(R.id.post2);
        post3= (LinearLayout) findViewById(R.id.post3);
        post4= (LinearLayout) findViewById(R.id.post4);
        post5= (LinearLayout) findViewById(R.id.post5);
        post6= (LinearLayout) findViewById(R.id.post6);
        post7= (LinearLayout) findViewById(R.id.post7);
        post8= (LinearLayout) findViewById(R.id.post8);
        post9= (LinearLayout) findViewById(R.id.post9);
        post10= (LinearLayout) findViewById(R.id.post10);
        post11= (LinearLayout) findViewById(R.id.post11);
        post12= (LinearLayout) findViewById(R.id.post12);
        post13= (LinearLayout) findViewById(R.id.post13);

        idPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,PostActivity.class));
            }
        });

        post1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,PostDetailsActivity.class));
            }
        });

        post2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,PostDetailsActivity.class));
            }
        });

        post3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,PostDetailsActivity.class));
            }
        });

        post4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,PostDetailsActivity.class));
            }
        });

        post5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,PostDetailsActivity.class));
            }
        });

        post6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,PostDetailsActivity.class));
            }
        });

        post7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,PostDetailsActivity.class));
            }
        });

        post8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,PostDetailsActivity.class));
            }
        });

        post9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,PostDetailsActivity.class));
            }
        });

        post10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,PostDetailsActivity.class));
            }
        });

        post11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,PostDetailsActivity.class));
            }
        });

        post12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,PostDetailsActivity.class));
            }
        });

        post13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,PostDetailsActivity.class));
            }
        });

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



    public void Logout(View V){
        Toast.makeText(this,"loging out",Toast.LENGTH_LONG).show();
        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
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
