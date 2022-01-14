package com.hrushikesh.Bosshomes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseUser;

public class HomeActivity extends AppCompatActivity {

    TextView tvName, tvEmail,tvagentid;
    Button b1,b2,b3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ParseUser currentUser = ParseUser.getCurrentUser();

        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvagentid = findViewById(R.id.agentid);
        b1=findViewById(R.id.add_post);
        b3 = findViewById(R.id.add_header);
        b2=findViewById(R.id.developasset);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivitythree();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivityone();
            }
        });


        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivityfour();
            }
        });

        if(currentUser!=null){
            tvName.setText(currentUser.getString("name"));
            tvagentid.setText(currentUser.getString("agentid"));
            tvEmail.setText(currentUser.getEmail());
        }
    }

    private void openNewActivityfour() {
        Intent intent = new Intent(this, BlogActivity.class);
        startActivity(intent);
    }

    private void openNewActivityone() {
        Intent intent = new Intent(this, webviewhd.class);
        startActivity(intent);
    }

    private void openNewActivitythree() {
        Intent intent = new Intent(this, FeedActivity.class);
        startActivity(intent);
    }


    public void logout(View view) {
        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Loading ...");
        progress.show();
        ParseUser.logOut();
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        progress.dismiss();
    }
}

