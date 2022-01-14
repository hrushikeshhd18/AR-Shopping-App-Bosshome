package com.hrushikesh.Bosshomes;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BlogClass extends ArrayAdapter<String> {

    private final ArrayList<String> userName;
    private final ArrayList<String> userDescription;
    private final ArrayList<String> userHeader;
    private final ArrayList<Bitmap> userImage;
    private final Activity context;

    public BlogClass(ArrayList<String> userName, ArrayList<String> userDescription,ArrayList<String> userHeader, ArrayList<Bitmap> userImage, Activity context) {

        super(context, R.layout.custom_view, userName);
        this.userName = userName;
        this.userDescription = userDescription;
        this.userHeader = userHeader;
        this.userImage = userImage;
        this.context = context;

    }

    @NonNull
    @Override

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();

        View customView = layoutInflater.inflate(R.layout.customblog, null,true);

        TextView userNameText = customView.findViewById(R.id.customViewUsernameTextView);
        TextView commentText = customView.findViewById(R.id.customViewCommentTextView);
        TextView headerText = customView.findViewById(R.id.customViewHeaderTextView);
        ImageView imageView = customView.findViewById(R.id.customViewImageView);

        userNameText.setText(userName.get(position));
        commentText.setText(userDescription.get(position));
        headerText.setText(userHeader.get(position));
        imageView.setImageBitmap(userImage.get(position));

        return customView;
    }
}
