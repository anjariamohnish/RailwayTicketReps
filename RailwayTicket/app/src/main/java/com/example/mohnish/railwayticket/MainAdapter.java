package com.example.mohnish.railwayticket;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohnish.railwayticket.SupportFiles.ticket;

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


        holder.qrImageView.setImageResource(R.drawable.ic_blackberry_qr_code_variant);
        holder.ticketIconImageView.setImageResource(R.drawable.ic_ticket);
        holder.rightArrowImageView.setImageResource(R.drawable.ic_right_arrow);

        holder.dateCreation.setText(ticketList.get(position).getCreation());
        holder.srcDest.setText(ticketList.get(position).getSrc()+"-"+ticketList.get(position).getDes());
        holder.source.setText(ticketList.get(position).getSources());
        holder.destination.setText(ticketList.get(position).getDestination());
        holder.src.setText(ticketList.get(position).getSrc());
        holder.des.setText(ticketList.get(position).getDes());
        holder.type.setText(ticketList.get(position).getType());
        holder.route.setText(ticketList.get(position).getclassType());
        holder.passengerNumber.setText(ticketList.get(position).getNumberOfPassenger());
        holder.validity.setText(ticketList.get(position).getValidity());


    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView qrImageView;
        ImageView ticketIconImageView;
        ImageView rightArrowImageView;
        TextView dateCreation, srcDest, source, destination, src, des, type, route, passengerNumber, validity;

        public ViewHolder(View itemView) {
            super(itemView);
            qrImageView = (ImageView) itemView.findViewById(R.id.qrImageView);

            ticketIconImageView = (ImageView) itemView.findViewById(R.id.ticketIconImageView);
            rightArrowImageView = (ImageView) itemView.findViewById(R.id.rightArrowImageView);

            dateCreation = (TextView) itemView.findViewById(R.id.dateCreation);
            srcDest = (TextView) itemView.findViewById(R.id.srcDest);
            source = (TextView) itemView.findViewById(R.id.source);
            destination = (TextView) itemView.findViewById(R.id.destination);
            src = (TextView) itemView.findViewById(R.id.src);
            des = (TextView) itemView.findViewById(R.id.des);
            type = (TextView) itemView.findViewById(R.id.type);
            route = (TextView) itemView.findViewById(R.id.classType);
            passengerNumber = (TextView) itemView.findViewById(R.id.passengerNumber);
            validity = (TextView) itemView.findViewById(R.id.validity);

            qrImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 // onclick open Popup Windows
                    
                }
            });

        }
    }
}
