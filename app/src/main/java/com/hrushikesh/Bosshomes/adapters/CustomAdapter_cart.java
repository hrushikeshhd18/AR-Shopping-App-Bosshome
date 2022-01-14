package com.hrushikesh.Bosshomes.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hrushikesh.Bosshomes.CartView;
import com.hrushikesh.Bosshomes.R;
import com.hrushikesh.Bosshomes.interfaces.ItemClickListener;
import com.hrushikesh.Bosshomes.modals.MyData_cart;
import com.mukesh.tinydb.TinyDB;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.hrushikesh.Bosshomes.MainActivity.my_data_list_cart;


class RecyclerViewHolder_cart extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
{
    public TextView cartDescription,cartItemPrice,cartItemCurrency;
    public TextView itemIncrease, itemDecrease, cartQuantityValue;
    public ImageView itemImage, cartDeleteImage;

    //for empty cart
    private ItemClickListener itemClickListener;


    public RecyclerViewHolder_cart(View itemView)
    {
        super(itemView);
        cartDescription = (TextView)itemView.findViewById(R.id.cartDescription);
        itemIncrease =(TextView)itemView.findViewById(R.id.itemIncrease);
        itemDecrease = (TextView)itemView.findViewById(R.id.itemDecrease);
        cartQuantityValue = (TextView)itemView.findViewById(R.id.cartQuantityValue);
        cartItemPrice = (TextView)itemView.findViewById(R.id.deliveryCostValue);
        cartItemCurrency = (TextView)itemView.findViewById(R.id.cartItemCurrency);

        itemImage = (ImageView)itemView.findViewById(R.id.homeOwnerLogo);
        cartDeleteImage = (ImageView)itemView.findViewById(R.id.cartDeleteImage);


        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);


    }

    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v)
    {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }

    @Override
    public boolean onLongClick(View v)
    {
        itemClickListener.onClick(v,getAdapterPosition(),true);
        return true;
    }
}
public class CustomAdapter_cart extends RecyclerView.Adapter<RecyclerViewHolder_cart>
{

    private List<MyData_cart> listData = new ArrayList<>();
    private Context context;
    private double total = 0.000;
    private RecyclerView recyclerView_cart;
    private CustomAdapter_cart adapter_cart;
    private ImageView cartImageEmpty;
    private TinyDB tinyDB;



    public CustomAdapter_cart(List<MyData_cart> listData, Context context, RecyclerView recyclerView_cart, CustomAdapter_cart adapter_cart, ImageView cartImageEmpty)
    {
        this.listData = listData;
        this.context = context;
        this.recyclerView_cart = recyclerView_cart;
        this.adapter_cart = adapter_cart;
        this.cartImageEmpty = cartImageEmpty;
        tinyDB = new TinyDB(context);

        //to calculate total price of all items in the cart modal class
        calculateTotal(tinyDB.getString("currency"));
    }

    @Override
    public RecyclerViewHolder_cart onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.cardview_cart,parent,false);
        return new RecyclerViewHolder_cart(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder_cart holder, final int position)
    {
        //if cart not empty
        if(my_data_list_cart.size()!=0)
        {
            if(context.getResources().getString(R.string.local).equals("INR"))
            {
                holder.cartDescription.setText(listData.get(position).getName());
            }
            else
            {
                holder.cartDescription.setText(listData.get(position).getName());
            }


            holder.cartItemPrice.setText("INR "+" "+listData.get(position).getPrice());
            holder.cartQuantityValue.setText("    "+listData.get(position).getQuantity()+"    ");
            Picasso.with(context).load(listData.get(position).getImage()).resize(200,200).placeholder(R.drawable.error_image).error(R.drawable.error_image).into(holder.itemImage);

            holder.cartItemCurrency.setText("INR");
            holder.cartItemCurrency.setVisibility(View.GONE);


            final int[] quantity = {Integer.parseInt(my_data_list_cart.get(position).getQuantity())};


            holder.itemIncrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    quantity[0] +=1;
                    holder.cartQuantityValue.setText("    "+ quantity[0] +"    ");

                    listData.get(position).setQuantity(String.valueOf(quantity[0]));
                    total += Double.parseDouble(listData.get(position).getPrice());

                    //adding to gson
                    Gson gson = new Gson();
                    TinyDB tinyDB = new TinyDB(context);
                    String jsonCart = gson.toJson(my_data_list_cart);
                    Log.d("TAG","jsonCART = " + jsonCart);
                    //storing into TinyDB
                    tinyDB.putString("cart_list",jsonCart);


                    CartView.getCartTotalValue().setText("INR"+total);
                    CartView.totalValueOFCart =String.valueOf(total);

                }
            });
            holder.itemDecrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if(quantity[0] <=1)
                    {
                        holder.cartQuantityValue.setText("    "+ quantity[0] +"    ");
                    }
                    else
                    {
                        quantity[0] -=1;
                        holder.cartQuantityValue.setText("    "+ quantity[0] +"    ");

                        my_data_list_cart.get(position).setQuantity(String.valueOf(quantity[0]));
                        total -= Double.parseDouble(my_data_list_cart.get(position).getPrice());

                        //adding to gson
                        Gson gson = new Gson();
                        TinyDB tinyDB = new TinyDB(context);
                        String jsonCart = gson.toJson(my_data_list_cart);
                        Log.d("TAG","jsonCART = " + jsonCart);
                        //storing into TinyDB
                        tinyDB.putString("cart_list",jsonCart);

                        CartView.getCartTotalValue().setText("INR"+" "+(total));
                        CartView.totalValueOFCart = String.valueOf(total);
                    }

                }
            });




            //final int currentPosition = position;
            final MyData_cart label = listData.get(position); //here we added Strings so String is the modal class



            holder.setItemClickListener((view, position1, isLongClick) ->
            {
                if(isLongClick)
                {

                }
                else
                {
                    //  Toast.makeText(context, "Normal Click " + listData.get(position), Toast.LENGTH_SHORT).show();
                }

            });
            holder.cartDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    removeItem(label,tinyDB.getString("currency")); //to remove item
                }
            });

        }
        else
        {
            //when cart is empty
            //recyclerView_cart.setAdapter(null);
            recyclerView_cart.setVisibility(View.INVISIBLE);
            recyclerView_cart.setEnabled(false);
            cartImageEmpty.setVisibility(View.VISIBLE);
            cartImageEmpty.setEnabled(true);


            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick)
                {
                    if(isLongClick)
                    {
                        //do something
                    }
                    else
                    {
                        //do something
                    }
                }
            });
        }

    }
    //removes data from arraylist and updates the recycler view
    private void removeItem(MyData_cart label, String Currency)
    {
        int position = listData.indexOf(label);

        //for quantity more than 1
        if(Integer.parseInt(listData.get(position).getQuantity())>1)
        {
            int q = Integer.parseInt(listData.get(position).getQuantity());
            total -= q*(Double.parseDouble(listData.get(position).getPrice()));
        }
        else
        {
            total -= Double.parseDouble(listData.get(position).getPrice());
        }

        listData.remove(position);
        notifyItemRemoved(position);

        //adding to gson
        Gson gson = new Gson();
        TinyDB tinyDB = new TinyDB(context);
        String jsonCart = gson.toJson(my_data_list_cart);
        Log.d("TAG","jsonCART = " + jsonCart);
        //storing into TinyDB
        tinyDB.putString("cart_list",jsonCart);

        adapter_cart = new CustomAdapter_cart(my_data_list_cart,context,recyclerView_cart,adapter_cart,cartImageEmpty);
        recyclerView_cart.setAdapter(adapter_cart);


        CartView.getCartTotalValue().setText("INR"+" "+(total));
        CartView.totalValueOFCart = String.valueOf(total);

        if(my_data_list_cart.size()!=0)
        {
            CartView.getCardViewCheckout().setVisibility(View.VISIBLE);
        }
        else
        {
            CartView.getCardViewCheckout().setVisibility(View.GONE);
        }


    }
    private void calculateTotal(String Currency)
    {
        for(int i = 0; i<listData.size();++i)
        {
            //for quantity more than 1
            if(Integer.parseInt(listData.get(i).getQuantity())>1)
            {
                double q = Double.parseDouble(listData.get(i).getQuantity());
                total += q*(Double.parseDouble(listData.get(i).getPrice()));
            }
            else
            {
                total+=Double.parseDouble(listData.get(i).getPrice());

            }
        }

        CartView.getCartTotalValue().setText("INR"+" "+(total)+"");
        CartView.totalValueOFCart = String.valueOf(total);
    }


    @Override
    public int getItemCount()
    {
        if(listData.size()==0)
        {
            return 1;
        }
        else
        {
            return listData.size();
        }
        //return 0;
    }
}

