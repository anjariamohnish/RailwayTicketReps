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
        holder.stations.setText(" Vile Parle - Andheri [Return]");
        holder.creation.setText("10/02/2018 16:31:26");
        holder.expiration.setText("11/02/2018");

        holder.imageView.setImageResource(R.drawable.ic_blackberry_qr_code_variant);
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView stations;
        TextView creation;
        TextView expiration;
        ImageView imageView;


        public ViewHolder(View itemView) {
            super(itemView);
            stations = (TextView) itemView.findViewById(R.id.stations);
            creation = (TextView) itemView.findViewById(R.id.creation);
            expiration = (TextView) itemView.findViewById(R.id.expiration);
            imageView = (ImageView) itemView.findViewById(R.id.imageView2);


        }
    }
}
