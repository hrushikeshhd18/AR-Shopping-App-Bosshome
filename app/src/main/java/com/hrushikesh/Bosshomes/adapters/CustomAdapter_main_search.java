package com.hrushikesh.Bosshomes.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.hrushikesh.Bosshomes.ItemPage;
import com.hrushikesh.Bosshomes.R;
import com.hrushikesh.Bosshomes.interfaces.ItemClickListener;
import com.hrushikesh.Bosshomes.modals.MainItemModal;
import com.hrushikesh.Bosshomes.modals.MyData_cart;
import com.mukesh.tinydb.TinyDB;

import java.util.ArrayList;
import java.util.List;

import static com.hrushikesh.Bosshomes.MainActivity.my_data_list_cart;


class RecyclerViewHolder_main_search extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
{
    public TextView itemName,itemDescriptionText,itemPrice;
    public ImageView itemImage;
    private ItemClickListener itemClickListener;


    public RecyclerViewHolder_main_search(View itemView)
    {
        super(itemView);
        itemName = (TextView)itemView.findViewById(R.id.itemName);
        itemDescriptionText = (TextView)itemView.findViewById(R.id.itemDescription);
        itemPrice = (TextView)itemView.findViewById(R.id.itemPriceValue);
        itemImage = (ImageView)itemView.findViewById(R.id.itemImage);
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
public class CustomAdapter_main_search extends RecyclerView.Adapter<RecyclerViewHolder_main_search>
{

    private List<MainItemModal> listData = new ArrayList<>();
    private Context context;

    public CustomAdapter_main_search(List<MainItemModal> listData, Context context)
    {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public RecyclerViewHolder_main_search onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.cardview_main_search,parent,false);

        return new RecyclerViewHolder_main_search(itemView);

    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder_main_search holder, int position)
    {
        holder.itemName.setText(listData.get(position).getName());
        holder.itemDescriptionText.setText(listData.get(position).getDescription());
        holder.itemPrice.setText(listData.get(position).getPrice());

        Glide.with(context)
                .load(listData.get(position).getImage())
                .apply(new RequestOptions().placeholder(R.drawable.error_image).error(R.drawable.error_image))
                .into(holder.itemImage);


        holder.setItemClickListener((view, position1, isLongClick) ->
        {
            if(isLongClick)
            {
                Toast.makeText(context, "Long Click " + listData.get(position), Toast.LENGTH_SHORT).show();
                //addItem(currentPosition, label); //to duplicate item
                //removeItem(label); //to remove item
            }
            else
            {
                quickViewDialog(listData.get(position).getItem_id(), listData.get(position).getName(),listData.get(position).getDescription(),listData.get(position).getImage(),listData.get(position).getPrice(),listData.get(position).getColour(), listData.get(position).getQuantity(), listData.get(position).getType());
            }
        });
    }
    @Override
    public int getItemCount()
    {
        return listData.size();
        //return 0;
    }

    public void quickViewDialog(String item_id, String name, String Description, String image, String price, String colour, String item_stock, String type)
    {
        //create alert dialog for asking
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout. quick_view_items, null);
        dialogBuilder.setView(dialogView);
        final ImageView itemImage = (ImageView) dialogView.findViewById(R.id.itemImage);
        final TextView itemName = (TextView) dialogView.findViewById(R.id.itemName);
        final TextView itemPriceValue = (TextView)dialogView.findViewById(R.id.itemPriceValue);
        final TextView itemDescription = (TextView)dialogView.findViewById(R.id.itemDescription);
        final TextView addtoCart = (TextView) dialogView.findViewById(R.id.addtoCart);
        final RelativeLayout fullLayout = (RelativeLayout)dialogView.findViewById(R.id.fullLayout);
        final AlertDialog alertDialog = dialogBuilder.create();

        itemName.setText(name);
        itemPriceValue.setText(price);
        itemDescription.setText(Description);

        Glide.with(context)
                .load(image)
                .apply(new RequestOptions().placeholder(R.drawable.error_image).error(R.drawable.error_image))
                .into(itemImage);

        fullLayout.setOnClickListener(v ->
        {
            Intent intent = new Intent(context,ItemPage.class);
            intent.putExtra("item_id",item_id);
            context.startActivity(intent);
        });

        addtoCart.setOnClickListener(v ->
        {
            Toast.makeText(context, "added to cart", Toast.LENGTH_SHORT).show();
            loadItemToCart(item_id,name,Description,image,price,colour,item_stock,type);
            alertDialog.dismiss();

        });

        //segmentedButtonGroup.setPosition(1,0);
        alertDialog.show();
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
        tinyDB = new TinyDB(context);
        tinyDB.putString("cart_list", jsonCart);
        }
}
