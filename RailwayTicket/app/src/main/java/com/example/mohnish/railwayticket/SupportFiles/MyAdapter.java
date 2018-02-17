package com.example.mohnish.railwayticket.SupportFiles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mohnish.railwayticket.R;

import java.util.List;

/**
 * Created by mohnish on 17-02-2018.
 */

public class MyAdapter extends BaseAdapter {

    Context context;
    List<ticket> ticketList;
    LayoutInflater inflater;

    public MyAdapter(Context context, List<ticket> ticketList) {
        this.context = context;
        this.ticketList = ticketList;
    }

    @Override
    public int getCount() {
        return ticketList.size();
    }

    @Override
    public Object getItem(int i) {
        return getItemId(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View listview=view;

        if (listview==null){
            inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listview=inflater.inflate(R.layout.ticketlist,null);

        }

        TextView tv=(TextView)listview.findViewById(R.id.ticketname);
        tv.setText(ticketList.get(i).getTicketName());

        return listview;
    }
}
