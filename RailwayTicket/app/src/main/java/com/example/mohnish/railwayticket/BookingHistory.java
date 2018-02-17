package com.example.mohnish.railwayticket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mohnish.railwayticket.SupportFiles.ticket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 10/02/2018.
 */

public class BookingHistory extends android.app.Fragment {

    List<ticket> ticketList=new ArrayList<>();
RecyclerView mRecyclerView;
RecyclerView.LayoutManager layoutManager;
RecyclerView.Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookinghistory,
                container, false);
        ticketList.add(new ticket("1","det1"));
        ticketList.add(new ticket("2","det1"));
        ticketList.add(new ticket("3","det1"));


        mRecyclerView=view.findViewById(R.id.recylerView);
        mRecyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        adapter=new MainAdapter(ticketList);
        mRecyclerView.setAdapter(adapter);

        return view;
    }


}
