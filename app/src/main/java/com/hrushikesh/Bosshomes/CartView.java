package com.hrushikesh.Bosshomes;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hrushikesh.Bosshomes.adapters.CustomAdapter_cart;

import static com.hrushikesh.Bosshomes.MainActivity.my_data_list_cart;

public class CartView extends AppCompatActivity {

    private RecyclerView recyclerView_cart;
    private GridLayoutManager gridLayoutManager_cart; //for displaying more than one card in one line
    private CustomAdapter_cart adapter_cart;
    //private List<MyData_cart> data_list_cart;
    //private List<MyData_cart> listData = new ArrayList<>();

    TextView cartCheckOut,myOrdersSubTotalValueCurrency;
    CardView cardCartCheckOut;
    ImageView cartImageEmpty;

    //static variable for it to be accessed by CustomAdapter_cart.java
    public static TextView cartTotalValue_static;

    CardView cardViewCheckout;
    public static CardView cardViewCheckout_static;
    public static String totalValueOFCart;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_view);
        cartImageEmpty = (ImageView)findViewById(R.id.cartImageEmpty);
        cartCheckOut = (TextView)findViewById(R.id.cartCheckOut);
        cardViewCheckout = (CardView)findViewById(R.id.cardViewCheckout);
        cardViewCheckout_static = (CardView)findViewById(R.id.cardViewCheckout);

        //for showing Total
        cartTotalValue_static =(TextView)findViewById(R.id.cartTotalValue);

        //for back button on action bar
        if(getResources().getString(R.string.local).equals("INR"))
        {
            //for back button on action bar
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
            //upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\">" +getResources().getString(R.string.cart)+ "</font>"));
        }
        else
        {
            //for back button on action bar
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_arabic);
            //upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\">" +getResources().getString(R.string.cart)+ "</font>"));
        }

        if(getResources().getString(R.string.local).equals("INR"))
        {
            cartImageEmpty.setImageDrawable(getResources().getDrawable(R.drawable.cartemptyimage));
            cartCheckOut.setText("Check Out");
        }
        else
        {
            cartImageEmpty.setImageDrawable(getResources().getDrawable(R.drawable.cartemptyimage));
            cartCheckOut.setText("PAY");

        }
        cartImageEmpty.setEnabled(false);
        cartImageEmpty.setVisibility(View.INVISIBLE);

        cartCheckOut = (TextView)findViewById(R.id.cartCheckOut);
        myOrdersSubTotalValueCurrency = (TextView)findViewById(R.id.myOrdersSubTotalValueCurrency);
        if(my_data_list_cart.size()!=0)
        {
            //myOrdersSubTotalValueCurrency.setText(my_data_list_cart.get(0).getCurrency());
            myOrdersSubTotalValueCurrency.setVisibility(View.GONE);
        }
        cardCartCheckOut = (CardView)findViewById(R.id.cardCartCheckOut);
        if(my_data_list_cart.size()==0)
        {
            // Toast.makeText(this, "Your Cart is Empty", Toast.LENGTH_SHORT).show();
            cardViewCheckout.setVisibility(View.GONE);
        }

        cardCartCheckOut.setOnClickListener(v ->
        {
            //normal login
            if(my_data_list_cart.size()!=0)
            {
                Toast.makeText(this, "Checkout", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView_cart = (RecyclerView)findViewById(R.id.recycler_view_cart);
        gridLayoutManager_cart = new GridLayoutManager(this,1);
        recyclerView_cart.setLayoutManager(gridLayoutManager_cart);

        //my_data_list_cart is taken from ItemPage View
        adapter_cart = new CustomAdapter_cart(my_data_list_cart,this,recyclerView_cart,adapter_cart,cartImageEmpty);
        recyclerView_cart.setAdapter(adapter_cart);
    }

    public static TextView getCartTotalValue()
    {
        return cartTotalValue_static;
    }
    public static CardView getCardViewCheckout()
    {
        return cardViewCheckout_static;
    }


    //for back button
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
