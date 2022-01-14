package com.hrushikesh.Bosshomes.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hrushikesh.Bosshomes.R;
import com.hrushikesh.Bosshomes.interfaces.ItemClickListener;
import com.hrushikesh.Bosshomes.modals.FilterModal;
import com.hrushikesh.Bosshomes.modals.MainItemModal;

import java.util.ArrayList;
import java.util.List;

import static com.hrushikesh.Bosshomes.MainActivity.listData_main_search_copy;

class RecyclerViewHolder_filter extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
{
    public TextView filterName,filterValue;
    private ItemClickListener itemClickListener;


    public RecyclerViewHolder_filter(View itemView)
    {
        super(itemView);
        filterName = (TextView)itemView.findViewById(R.id.filterName);
        filterValue = (TextView)itemView.findViewById(R.id.filterValue);
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
public class CustomAdapter_filter extends RecyclerView.Adapter<RecyclerViewHolder_filter>
{

    private List<FilterModal> listData = new ArrayList<>();
    private Context context;
    private RecyclerView main_search_recyler;
    private CustomAdapter_main_search customAdapter_main_search;
    private List<MainItemModal> main_item_list;

    public CustomAdapter_filter(List<FilterModal> listData, Context context, RecyclerView main_search_recyler, CustomAdapter_main_search customAdapter_main_search, List<MainItemModal> main_item_list)
    {
        this.listData = listData;
        this.context = context;
        this.main_search_recyler = main_search_recyler;
        this.customAdapter_main_search = customAdapter_main_search;
        this.main_item_list = main_item_list;
    }

    @Override
    public RecyclerViewHolder_filter onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.filter_element,parent,false);

        return new RecyclerViewHolder_filter(itemView);

    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder_filter holder, int position)
    {

        holder.filterName.setText(listData.get(position).getFilter_name());
        holder.filterValue.setText(listData.get(position).getFilter_value());


        holder.setItemClickListener((view, position1, isLongClick) ->
        {
            if(isLongClick)
            {
                //Toast.makeText(context, "Long Click " + listData.get(position), Toast.LENGTH_SHORT).show();
                //addItem(currentPosition, label); //to duplicate item
                //emoveItem(label); //to remove item
            }
            else
            {
                String selected = listData.get(position).getFilter_name();
                List<MainItemModal> newMainItemList = new ArrayList<>();

                //getting from un_touched_original_copy of main item list
                for(int i=0;i<main_item_list.size();++i)
                {
                    if(selected.equals(main_item_list.get(i).getType()))
                    {
                        newMainItemList.add(main_item_list.get(i));
                    }
                }
                listData_main_search_copy = newMainItemList;
                customAdapter_main_search = new CustomAdapter_main_search(listData_main_search_copy,context);
                main_search_recyler.setAdapter(customAdapter_main_search);
            }
        });
    }
    @Override
    public int getItemCount()
    {
        return listData.size();
        //return 0;
    }

}
