package com.example.mohnish.railwayticket;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohnish.railwayticket.SupportFiles.ticket;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Administrator on 17/02/2018.
 */

public class MainAdapter extends android.support.v7.widget.RecyclerView.Adapter<MainAdapter.ViewHolder> {

    List<ticket> ticketList;
    Context context;

    public MainAdapter(List<ticket> ticketList, Context context) {
        this.ticketList = ticketList;
        this.context = context;
    }

    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.ticket_row, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MainAdapter.ViewHolder holder, int position) {

    //    holder.textView.setText(ticketList.get(position).getTicketName());


        holder.qrImageView.setImageResource(R.drawable.ic_blackberry_qr_code_variant);
        holder.ticketIconImageView.setImageResource(R.drawable.ic_ticket);
       holder.rightArrowImageView.setImageResource(R.drawable.ic_right_arrow);
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView qrImageView;
        ImageView ticketIconImageView;
        ImageView rightArrowImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            qrImageView = (ImageView) itemView.findViewById(R.id.qrImageView);
            ticketIconImageView=(ImageView)itemView.findViewById(R.id.ticketIconImageView);
            rightArrowImageView=(ImageView)itemView.findViewById(R.id.rightArrowImageView);
        }
    }
}
