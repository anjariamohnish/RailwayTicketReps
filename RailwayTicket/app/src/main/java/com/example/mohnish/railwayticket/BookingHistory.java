package com.example.mohnish.railwayticket;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.mohnish.railwayticket.SupportFiles.MyAdapter;
import com.example.mohnish.railwayticket.SupportFiles.ticket;

import java.util.List;

/**
 * Created by Administrator on 10/02/2018.
 */

public class BookingHistory extends android.app.Fragment {

    List<ticket> ticketList;
ListView lv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookinghistory,
                container, false);
        ticketList.add(new ticket("1","det1"));
        ticketList.add(new ticket("2","det1"));
        ticketList.add(new ticket("3","det1"));
    lv=view.findViewById(R.id.listview);
    lv.setAdapter(new MyAdapter(this.getActivity(),ticketList));

        return view;
    }


}
