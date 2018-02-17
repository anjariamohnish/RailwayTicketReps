package com.example.mohnish.railwayticket.SupportFiles;

/**
 * Created by mohnish on 17-02-2018.
 */

public class ticket {
    private String ticketName;
    private String ticketDetails;

    public ticket(String ticketName, String ticketDetails) {
        this.ticketName = ticketName;
        this.ticketDetails = ticketDetails;
    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public String getTicketDetails() {
        return ticketDetails;
    }

    public void setTicketDetails(String ticketDetails) {
        this.ticketDetails = ticketDetails;
    }
}
