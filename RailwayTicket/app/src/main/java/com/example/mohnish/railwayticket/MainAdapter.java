package com.example.mohnish.railwayticket;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mohnish.railwayticket.SupportFiles.ticket;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Administrator on 17/02/2018.
 */

public class MainAdapter extends android.support.v7.widget.RecyclerView.Adapter<MainAdapter.ViewHolder> {

    List<ticket> ticketList;

    public MainAdapter(List<ticket> ticketList) {
        this.ticketList = ticketList;
    }

    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_row, parent, false);
        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(MainAdapter.ViewHolder holder, int position) {

        holder.textView.setText(ticketList.get(position).getTicketName());

    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textview1);
        }
    }
}
