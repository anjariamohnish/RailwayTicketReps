package com.example.mohnish.railwayticket;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Administrator on 11/02/2018.
 */

public class Home extends Fragment {

    ImageView qrimg;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,
                container, false);

        qrimg = (ImageView) view.findViewById(R.id.imageView3);

        qrimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Home.this.getActivity(), QRScanner.class), 2);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 3) {
            Snackbar snackbar = Snackbar
                    .make(Home.this.getView(), "Scan Cancelled...", Snackbar.LENGTH_LONG);
            snackbar.show();
        }


        if (resultCode == 2) {
            HashMap<String, String> hm = (HashMap<String, String>) data.getSerializableExtra("hashmap");
            try {
                StoreLocally(hm);
                Snackbar snackbar = Snackbar
                        .make(Home.this.getView(), "Ticket Saved...", Snackbar.LENGTH_LONG);
                snackbar.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void StoreLocally(HashMap map) throws IOException {

        String FILENAME = map.get("from").toString().replace("from=", "") + "-" + map.get("to").toString().replace("to=", "") + " " + map.get("dateTime").toString().replace("dateTime=", "").replace("/", "").replace(".", "").replace(":", "").trim();
        String string = map.get("from") + ";" + map.get("to") + ";" + map.get("class") + ";" + map.get("returnStatus") + ";" + map.get("counterEmployee") + ";" + map.get("dateTime") + ";" + map.get("expiry") + ";" + map.get("flag") + ";" + map.get("passengerId");

        FileOutputStream out = getActivity().openFileOutput(FILENAME, getActivity().MODE_PRIVATE);
        out.write(string.getBytes());
        out.close();

        DatabaseHandler db = new DatabaseHandler(Home.this.getActivity());
        db.AddFile(FILENAME,string);


    }


    /*private  void ReadLocally(String file)
    {
        try {
            // Open stream to read file.
            FileInputStream in = this.openFileInput(file);

            BufferedReader br= new BufferedReader(new InputStreamReader(in));

            StringBuilder sb= new StringBuilder();
            String s= null;
            while((s= br.readLine())!= null)  {
                sb.append(s).append("\n");
            }
          Toast.makeText(this,sb.toString(),Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(this,"Error:"+ e.getMessage(),Toast.LENGTH_SHORT).show();
        }

        DatabaseHandler db = new DatabaseHandler(this);
        List<String> files=db.GetFile();
        for (String d:files){
            Log.d("TAG",d);
        }




    }
*/
}
