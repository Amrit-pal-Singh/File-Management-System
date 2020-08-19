package com.example.filemanagement;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CustomListAdapter extends ArrayAdapter {

    private Context mContext;
    private int id;
    private List<String> items ;
    private List<String> planToSendDate;
    public CustomListAdapter(Context context, int textViewResourceId , List<String> list, List<String> date)
    {
        super(context, textViewResourceId, list);
        mContext = context;
        id = textViewResourceId;
        items = list ;
        planToSendDate = date;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View v, ViewGroup parent)
    {
        View mView = v ;
        if(mView == null){
            LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = vi.inflate(id, null);
        }

        TextView text = (TextView) mView.findViewById(R.id.textView);

        if(items.get(position) != null )
        {
            if(position < planToSendDate.size() && planToSendDate.get(position).equals("None")) {
                text.setTextColor(Color.WHITE);
                text.setText(items.get(position));
                text.setBackgroundColor(Color.GREEN);
            }else if(position < planToSendDate.size()){
//            Log.i("CustomListAdapter", ""+planToSendDate.get(0)+" position : "+position);
                String date_plan = planToSendDate.get(position);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                String str1 = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                Date date = new Date();
                String str1 = sdf.format(date);
                long difference_In_Days=0;
//                System.out.println(str1);
                try {
                    Date d1 = sdf.parse(date_plan);
                    Date d2 = sdf.parse(str1);
                    long difference_In_Time = d2.getTime() - d1.getTime();
                    difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;

                    System.out.println(difference_In_Days);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(difference_In_Days <= 1) {
                    text.setTextColor(Color.WHITE);
                    text.setText(items.get(position));
                    text.setBackgroundColor(Color.GREEN);
                }else if(difference_In_Days <= 2) {
                    text.setTextColor(Color.WHITE);
                    text.setText(items.get(position));
                    text.setBackgroundColor(Color.YELLOW);
                }else {
                    text.setTextColor(Color.WHITE);
                    text.setText(items.get(position));
                    text.setBackgroundColor(Color.RED);
                }
            }else {
                text.setTextColor(Color.WHITE);
                text.setText(items.get(position));
                text.setBackgroundColor(Color.GREEN);
            }

        }

        return mView;
    }

}