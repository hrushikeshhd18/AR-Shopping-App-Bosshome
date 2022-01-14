package com.hrushikesh.Bosshomes;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class MainActivityar extends AppCompatActivity implements View.OnClickListener {

    ArFragment arFragment;
    public ModelRenderable bearRenderable,catRenderable,cowRenderable,tableRenderable;
    ImageView bear,cat,cow1,ttable;

    View  arrayView[];
    ViewRenderable name_animal;

    int selected = 1;//Default Bear is choose

    ViewRenderable animal_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainar);
        arFragment = (ArFragment)getSupportFragmentManager().findFragmentById(R.id.sceneform_ux_fragment);


        //View
        bear = (ImageView)findViewById(R.id.bear);
        cat = (ImageView)findViewById(R.id.cat);
        cow1 = (ImageView)findViewById(R.id.cow);
        ttable = (ImageView)findViewById(R.id.table);

        setArrayView();
        setClickListener();

        setupModel();



        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {

                    Anchor anchor = hitResult.createAnchor();
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(arFragment.getArSceneView().getScene());

                    createModel(anchorNode,selected);
            }
        });



    }
    private void setupModel() {

        ViewRenderable.builder()
                .setView(this,R.layout.name_animal)
                .build()
                .thenAccept(renderable -> name_animal = renderable);

        ViewRenderable.builder()
                .setView(this,R.layout.name_animal)
                .build()
                .thenAccept(renderable -> name_animal = renderable);


        ModelRenderable.builder()
                .setSource(this,R.raw.cat)
                .build().thenAccept(renderable -> bearRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast.makeText(this, "Unnable to load", Toast.LENGTH_SHORT).show();
                            return null;
                        }


                );

        ModelRenderable.builder()
                .setSource(this,R.raw.cat)
                .build().thenAccept(renderable -> catRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast.makeText(this, "Unnable to load", Toast.LENGTH_SHORT).show();
                            return null;
                        }


                );
        ModelRenderable.builder()
                .setSource(this,R.raw.cow)
                .build().thenAccept(renderable -> cowRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast.makeText(this, "Unnable to load", Toast.LENGTH_SHORT).show();
                            return null;
                        }


                );
         ModelRenderable.builder()
                .setSource(this,R.raw.table)
                .build().thenAccept(renderable -> tableRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast.makeText(this, "Unnable to load", Toast.LENGTH_SHORT).show();
                            return null;
                        }


                );



    }




    private void createModel(AnchorNode anchorNode, int selected) {
        if(selected == 1){

            TransformableNode bear = new TransformableNode(arFragment.getTransformationSystem());
            bear.setParent(anchorNode);
            bear.setRenderable(bearRenderable);
            bear.select();

            addName(anchorNode,bear,"sofa");

        }
        if(selected == 2){

            TransformableNode cat = new TransformableNode(arFragment.getTransformationSystem());
            cat.setParent(anchorNode);
            cat.setRenderable(catRenderable);
            cat.select();

            addName(anchorNode,cat,"tv");

        }
        if(selected == 3){

            TransformableNode cow = new TransformableNode(arFragment.getTransformationSystem());
            cow.setParent(anchorNode);
            cow.setRenderable(cowRenderable);
            cow.select();

            addName(anchorNode,cow,"ladder");

        } if(selected == 4){

            TransformableNode table = new TransformableNode(arFragment.getTransformationSystem());
            table.setParent(anchorNode);
            table.setRenderable(tableRenderable);
            table.select();

            addName(anchorNode,table,"table");

        }

    }

    private void addName(AnchorNode anchorNode, TransformableNode model, String name) {

        TransformableNode nameView = new TransformableNode(arFragment.getTransformationSystem());
        nameView.setLocalPosition(new Vector3(0f,model.getLocalPosition().y+0.5f,0));
        nameView.setParent(anchorNode);
        nameView.setRenderable(name_animal);
        nameView.select();


        ///set text
        TextView txt_name = (TextView)name_animal.getView();
        txt_name.setText(name);
        //click to text view to remove animal
        txt_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anchorNode.setParent(null);

            }
        });

    }

    private void setClickListener() {

        for(int i=0;i<arrayView.length;i++)
            arrayView[i].setOnClickListener(this);
    }

    private void setArrayView() {
        arrayView = new View[]{
                bear,cat,cow1,ttable

        };
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.bear) {
            selected = 1;
            setBackground(view.getId());
        }
         else if(view.getId() == R.id.cat) {


            selected = 2;
            setBackground(view.getId());
        }
         else if(view.getId() == R.id.cow) {
            selected = 3;
            setBackground(view.getId());
        }
         else if(view.getId() == R.id.table) {
            selected = 4;
            setBackground(view.getId());
        }
    }

    private void setBackground(int id) {
        for(int i = 0;i<arrayView.length;i++)
        {
            if(arrayView[i].getId() == id)
                arrayView[i].setBackgroundColor(Color.parseColor("#80333639"));///set background for selected decor
            else
                arrayView[i].setBackgroundColor(Color.TRANSPARENT);
        }
    }
}
