package com.hrushikesh.Bosshomes;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.hrushikesh.Bosshomes.adapters.CustomAdapter_main_search;
import com.hrushikesh.Bosshomes.modals.MainItemModal;
import com.hrushikesh.Bosshomes.modals.MyData_cart;
import com.mukesh.tinydb.TinyDB;

import java.util.ArrayList;
import java.util.List;

import static com.hrushikesh.Bosshomes.MainActivity.listData_main_search;
import static com.hrushikesh.Bosshomes.MainActivity.my_data_list_cart;

public class ItemPage extends AppCompatActivity {

    ImageView itemImage;
    TextView itemName;
    TextView price;
    TextView itemDescription;
    TextView colourText;
    TextView checkoutButton;
    TextView stockText;

    String item_id;
    String image,name,price_value,description,type,color,stock;
    String quantity_selected = "1";

    RecyclerView similarItemsRecyclerView;
    private GridLayoutManager gridLayoutManager_main_search; //for displaying more than one card in one line
    private CustomAdapter_main_search adapter_main_search;

    List<MainItemModal> similarItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_page);

        if(getResources().getString(R.string.local).equals("INR"))
        {
            //for back button on action bar
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
            //upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\">" +"Item Page"+ "</font>"));
        }
        else
        {
            //for back button on action bar
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_arabic);
            //upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\">" +"Item Page"+ "</font>"));
        }

        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        scrollView.setFocusableInTouchMode(true);
        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);

        itemImage = (ImageView)findViewById(R.id.itemImage);
        itemName = (TextView)findViewById(R.id.itemName);
        price = (TextView)findViewById(R.id.price);
        itemDescription = (TextView)findViewById(R.id.itemDescription);
        colourText = (TextView)findViewById(R.id.colourText);
        checkoutButton = (TextView)findViewById(R.id.checkoutButton);
        stockText = (TextView)findViewById(R.id.stockText);
        similarItemsRecyclerView = (RecyclerView)findViewById(R.id.similarItemsRecyclerView);

        Intent i=getIntent();
        item_id = i.getStringExtra("item_id");

        for(int j=0;j<listData_main_search.size();++j)
        {
            if(item_id.equals(listData_main_search.get(j).getItem_id()))
            {
                name = listData_main_search.get(j).getName();
                price_value = listData_main_search.get(j).getPrice();
                description = listData_main_search.get(j).getDescription();
                color = listData_main_search.get(j).getColour();
                stock = listData_main_search.get(j).getQuantity();
                type = listData_main_search.get(j).getType();


                itemName.setText(listData_main_search.get(j).getName());
                price.setText("INR "+listData_main_search.get(j).getPrice());
                itemDescription.setText(listData_main_search.get(j).getDescription());
                colourText.setText("Color : "+listData_main_search.get(j).getColour());
                stockText.setText("Inventory : "+listData_main_search.get(j).getQuantity());
                image = listData_main_search.get(j).getImage();

                Glide.with(ItemPage.this)
                        .load(listData_main_search.get(j).getImage())
                        .apply(new RequestOptions().placeholder(R.drawable.error_image).error(R.drawable.error_image))
                        .into(itemImage);
            }
        }

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                loadItemToCart(item_id,name,description,image,price_value,color,stock,type);
            }
        });

        similarItemList = new ArrayList<>();
        for(int kk=0;kk<listData_main_search.size();++kk)
        {
            if(type.equals(listData_main_search.get(kk).getType())&&!item_id.equals(listData_main_search.get(kk).getItem_id()))
            {
                Log.d("type",type+":"+listData_main_search.get(kk).getType());
                similarItemList.add(listData_main_search.get(kk));
            }
        }

        if(similarItemList.size()==0)
        {
            similarItemsRecyclerView.setVisibility(View.GONE);
        }
        else
        {
            gridLayoutManager_main_search = new GridLayoutManager(ItemPage.this,2);
            similarItemsRecyclerView.setLayoutManager(gridLayoutManager_main_search);
            adapter_main_search = new CustomAdapter_main_search(similarItemList,ItemPage.this);
            similarItemsRecyclerView.setAdapter(adapter_main_search);
        }
    }

    private void loadItemToCart(String item_id, String name, String Description, String image, String price, String colour, String item_stock, String type)
    {
        MyData_cart m = new MyData_cart(item_id,name,Description,price,type,"1",colour,image,"special request",item_stock);


        if(my_data_list_cart.size()==0)
        {
            my_data_list_cart.add(m);
        }
        else
        {
            //Before adding check if the item has the same id,
            //if same id then check if it has messages or no messages
            //if cart has an item with no message, and adding same item with no message then just increment the quantity
            //if cart has an item with message, and adding item with same message then just increment the quantity
            //same principle for Attributes

            int size_before_adding = my_data_list_cart.size();
            for(int v=0;v<size_before_adding; ++v)
            {
                // same item id
                if (m.getItem_id().equals(my_data_list_cart.get(v).getItem_id()))
                {
                    //same message (even if no message)
                    if (m.getSpecial_request().trim().equals(my_data_list_cart.get(v).getSpecial_request().trim()))
                    {
                        //same variation (even if no variation)
                        if (m.getSpecial_request().trim().equals(my_data_list_cart.get(v).getSpecial_request().trim()))
                        {
                            //just increment the item quantity
                            int quantity = Integer.parseInt(my_data_list_cart.get(v).getQuantity());
                            quantity += Integer.parseInt(m.getQuantity());
                            my_data_list_cart.get(v).setQuantity(String.valueOf(quantity));
                            break;
                        }
                        //different variation
                        else
                        {
                            if (v + 1 == my_data_list_cart.size())
                                my_data_list_cart.add(m);
                        }
                    }
                    //different message
                    else
                    {
                        if (v + 1 == my_data_list_cart.size())
                            my_data_list_cart.add(m);
                    }

                }
                //different item id
                else
                {
                    if (v + 1 == my_data_list_cart.size())
                        my_data_list_cart.add(m);
                }
            }
        }

        //adding to gson
        Gson gson;
        gson = new Gson();
        String jsonCart = gson.toJson(my_data_list_cart);
        Log.d("TAG", "jsonCART = " + jsonCart);
        //storing into TinyDB
        TinyDB tinyDB;
        tinyDB = new TinyDB(ItemPage.this);
        tinyDB.putString("cart_list", jsonCart);

        Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
        finish();
    }

    //for back button
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
