package com.example.myapplication;

import android.content.Context;
import android.icu.util.Output;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WeatherRVAdapter extends RecyclerView.Adapter<WeatherRVAdapter.ViewHolder> {
    private ArrayList<WeatherRVModel> weatherRVModelArrayList;
    private Context context;

    public WeatherRVAdapter(Context context, ArrayList<WeatherRVModel> weatherRVModelArrayList) {
        this.context = context;
        this.weatherRVModelArrayList = weatherRVModelArrayList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<WeatherRVModel> getWeatherRVModelArrayList() {
        return weatherRVModelArrayList;
    }

    public void setWeatherRVModelArrayList(ArrayList<WeatherRVModel> weatherRVModelArrayList) {
        this.weatherRVModelArrayList = weatherRVModelArrayList;
    }

    @NonNull
    @Override
    public WeatherRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.weather_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherRVAdapter.ViewHolder holder, int position) {
        WeatherRVModel modal = weatherRVModelArrayList.get(position);
        holder.temperatureTV.setText(modal.getTemperature() + "°F");
        holder.windTV.setText(modal.getWindSpeed() + "mph");
        holder.humidityTV.setText(modal.getHumidity() + "%");


        Picasso.get().load("http:".concat(modal.getIcon())).into(holder.conditionIV);
        //holder.windTV.setText(modal.getWindSpeed() + "Km/h");
        //SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        //SimpleDateFormat output = new SimpleDateFormat("hh:mm aa");
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat output = new SimpleDateFormat("dd HH:mm");
        try{
            Date t = input.parse(modal.getTime());
            holder.timeTV.setText(output.format(t));
        }catch(ParseException e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return weatherRVModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView windTV, temperatureTV, timeTV, humidityTV;
        private ImageView conditionIV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            humidityTV = itemView.findViewById(R.id.idTVHumidity);
            windTV = itemView.findViewById(R.id.idTVWindSpeed);
            temperatureTV = itemView.findViewById(R.id.idTVTemperature);
            timeTV = itemView.findViewById(R.id.idTVTime);
            conditionIV = itemView.findViewById(R.id.idIVCondition);
        }
    }
}
