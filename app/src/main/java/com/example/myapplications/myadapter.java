package com.example.myapplications;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplications.data.MyDbHandler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class myadapter extends RecyclerView.Adapter  implements Filterable {
    SparseBooleanArray itemStateArray= new SparseBooleanArray();
    List<string> data;
    Context context;
    List<string> alldata;
    List<string> selectedvalues;
    boolean mCheckBox;
    public myadapter(List<string> data,Context contex) {
        this.data = data;
        this.context=contex;
        this.alldata=new ArrayList<>(data);
        this.selectedvalues=new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        if(data.get(position).check)
        {
        return 1;
        }
        return 0;
    }


    @Override
    public Filter getFilter() {

        return filter;
    }
    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<string> filteredlist = new ArrayList<>();
            if (constraint.toString().isEmpty()) {
                filteredlist.addAll(alldata);

            } else {
                for (string d : data) {
                    if (d.city.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredlist.add(d);

                    }

                }

            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredlist;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            data.clear();
            data.addAll((Collection<? extends string>) results.values);
            notifyDataSetChanged();
        }
    };

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view;
        if(viewType==1)
        {
        view=layoutInflater.inflate(R.layout.singlerow2,parent,false);
        return new ViewHolderTwo(view);
        }
        view=layoutInflater.inflate(R.layout.singlerow,parent,false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        MyDbHandler db=new MyDbHandler(holder.itemView.getContext());
        if(getItemViewType(position)==0)
        {

            ViewHolderOne viewHolderOne=(ViewHolderOne) holder;
            viewHolderOne.tv.setText(data.get(position).city);
            viewHolderOne.tvc.setText(data.get(position).time);
            viewHolderOne.chk.setChecked(data.get(position).isSelected());
        //    viewHolderOne.chk.setTag(data.get(position));
            viewHolderOne.chk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   data.get(position).setSelected(viewHolderOne.chk.isChecked());
                   if(data.get(position).isSelected())
                   {
                        db.saveclock(data.get(position));
                       selectedvalues=db.getAllClocks();
                   }
                    else if(!data.get(position).isSelected())
                    {

                                for(int i=0;i<selectedvalues.size();i++)
                                {
                                    if(data.get(position).city.equals(selectedvalues.get(i).city))
                                    {
                                        db.deleteclock(selectedvalues.get(i));
                                        selectedvalues.remove(i);
                                    }

                                }

                   }

                }
            });
        }
        else if(getItemViewType(position)==1)
        {

            ViewHolderTwo viewHolderTwo;
            viewHolderTwo = (ViewHolderTwo) holder;
            viewHolderTwo.tv1.setText(data.get(position).city);
            viewHolderTwo.tv2.setText(data.get(position).time);

            final string temp=data.get(position);
            viewHolderTwo.img.setOnClickListener(new View.OnClickListener()
            {
               @Override
                public void onClick(View v)
                {
                    db.deleteclock(temp);
                    data.remove(temp);
                    notifyDataSetChanged();

                }


            });

        }

    }

    @Override
    public int getItemCount() {
        if(data==null)
        {
            return 0;
        }
        return data.size();
    }

    static class ViewHolderOne extends RecyclerView.ViewHolder{
        TextView tv,tvc;
        CheckBox chk;
        public ViewHolderOne(@NonNull View itemView)
        {
            super(itemView);
            tv=itemView.findViewById(R.id.t1);
            tvc=itemView.findViewById(R.id.t2);
            chk=itemView.findViewById(R.id.checkBox);
        }


    }
    static  class ViewHolderTwo extends RecyclerView.ViewHolder
    {
        TextView tv1;
        TextView tv2;
        ImageButton img;
        public ViewHolderTwo(@NonNull View itemView) {
            super(itemView);
            tv1=itemView.findViewById(R.id.text1);
            tv2=itemView.findViewById(R.id.textt2);
            img=itemView.findViewById(R.id.b4);
        }
    }
    public List<string> listofselectedvalues()
    {

        return selectedvalues;
    }
}
