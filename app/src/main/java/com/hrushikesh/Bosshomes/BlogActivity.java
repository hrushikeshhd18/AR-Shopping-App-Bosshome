package com.hrushikesh.Bosshomes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class BlogActivity extends AppCompatActivity   {

    ListView listView;
    ArrayList<String> userNamesFromParse;
    ArrayList<String> userDescriptionFromParse;
    ArrayList<String> userHeaderFromParse;
    ArrayList<Bitmap> userImagesFromParse;
    BlogClass blogClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blogactivity);


        listView = findViewById(R.id.listView);

        userNamesFromParse = new ArrayList<>();
        userDescriptionFromParse = new ArrayList<>();
        userHeaderFromParse = new ArrayList<>();
        userImagesFromParse = new ArrayList<>();

        blogClass = new BlogClass(userNamesFromParse, userHeaderFromParse, userDescriptionFromParse, userImagesFromParse, this);

        listView.setAdapter(blogClass);

        download();

    }


    public void download() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Blog");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e != null) {

                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                } else {

                    if (objects.size() > 0) {

                        for (final ParseObject object : objects) {

                            ParseFile parseFile = (ParseFile) object.get("Image");

                            parseFile.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {

                                    if (e == null && data != null) {

                                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                        userImagesFromParse.add(bitmap);
                                        userNamesFromParse.add(object.getString("Username"));
                                        userDescriptionFromParse.add(object.getString("description"));
                                        userHeaderFromParse.add(object.getString("Header"));

                                        blogClass.notifyDataSetChanged();
                                    }
                                }
                            });
                        }
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.blogmenu, menu);

        return super.onCreateOptionsMenu(menu);



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.add_header) {
            // Intent
            Intent intentAddPostImage = new Intent(getApplicationContext(), BlogUpload.class);
            startActivity(intentAddPostImage);

        } else if (item.getItemId() == R.id.report) {
            LayoutInflater inflater = getLayoutInflater();

            View toastLayout = inflater.inflate(R.layout.toastlayout,
                    (ViewGroup) findViewById(R.id.toast_root_view));

            TextView header = (TextView) toastLayout.findViewById(R.id.toast_header);
            header.setText("tq for reporting ");

            TextView body = (TextView) toastLayout.findViewById(R.id.toast_body);
            body.setText("the post will be taken down soon");

            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(toastLayout);
            toast.show();


        } else if (item.getItemId() == R.id.logout) {

            ParseUser.logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {

                    if (e != null) {

                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    } else {

                        // Intent
                        Intent intentLogout = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intentLogout);
                    }
                }
            });
        }

        return super.onOptionsItemSelected(item);
    }


}