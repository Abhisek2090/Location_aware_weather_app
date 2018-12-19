package com.weather.com.location.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weather.com.location.R;
import com.weather.com.location.model.ForecastDayResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class WeatherDataAdapter extends RecyclerView.Adapter<WeatherDataAdapter.MyHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<ForecastDayResponse> data;
    private String dayOfTheWeek;
   // List<ForecastDayResponse> filterList;
    ProgressDialog progress;

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");



    // create constructor to innitilize context and data sent from MainActivity
    public WeatherDataAdapter(Context context, List<ForecastDayResponse> data) {
            this.context = context;
            inflater = LayoutInflater.from(context);
            this.data = data;


    }

    // Inflate the layout when viewholder created
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.avg_temp_container,parent,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        final MyHolder myHolder= (MyHolder) holder;
        ForecastDayResponse current = data.get(position);
        try {
            Date date = format.parse(current.getDate());
            dayOfTheWeek = (String) DateFormat.format("EEEE", date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        myHolder.dayTextView.setText(dayOfTheWeek);
         myHolder.tempTextView.setText(String.valueOf(current.getDay().getAvgtemp_c()));
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {



      TextView dayTextView, tempTextView;

        public MyHolder(View itemView) {
            super(itemView);

            dayTextView = (TextView)itemView.findViewById(R.id.dayTextView);
            tempTextView = (TextView)itemView.findViewById(R.id.tempTextView);

        }


        @Override
        public void onClick(View view) {


        }
    }

    public void updateWithNewList(List<ForecastDayResponse> forecastTempData) {
        data = forecastTempData;
        notifyDataSetChanged();
    }


    public void showProgressBar() {
        progress = new ProgressDialog(context);
        progress.setMessage("Loading..Please Wait..");
        progress.setCancelable(true);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();

    }


}
