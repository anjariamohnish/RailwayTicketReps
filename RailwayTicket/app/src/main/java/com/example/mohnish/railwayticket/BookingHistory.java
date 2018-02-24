package com.example.mohnish.railwayticket;

import android.graphics.Bitmap;
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

    List<ticket> ticketList = new ArrayList<>();
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookinghistory,
                container, false);

       /* ticketList.add(
                new ticket("10/02/2018",
                        "VLP",
                        "Vile Parle",
                        "AND",
                        "Andheri",
                        "Single",
                        "Western",
                        "1",
                        "11/02/2018",
                        null
                ));


        ticketList.add(
                new ticket("10/02/2018",
                        "VLP",
                        "Vile Parle",
                        "AND",
                        "Andheri",
                        "Single",
                        "Western",
                        "1",
                        "11/02/2018",
                        null
                ));
*/
       DatabaseHandler databaseHandler=new DatabaseHandler(getActivity());
        try {
            ticketList=databaseHandler.GetFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mRecyclerView = view.findViewById(R.id.recylerView);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new MainAdapter(ticketList, getActivity().getApplicationContext());
        mRecyclerView.setAdapter(adapter);

        return view;
    }


}
