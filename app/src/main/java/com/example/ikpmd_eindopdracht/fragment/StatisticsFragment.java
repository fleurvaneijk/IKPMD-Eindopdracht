package com.example.ikpmd_eindopdracht.fragment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.HttpAuthHandler;

import com.example.ikpmd_eindopdracht.R;
import com.firebase.ui.auth.AuthUI;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsFragment extends Fragment {

    private static String TAG = "statistics";

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static String userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private static DatabaseReference myRef = database.getReference("statistics/" + userUID);

    private static HashMap<String, Integer> playedTracks = new HashMap<String, Integer>();
    private BarChart barChart;
    private View view;
    public static int currentEcts = 0;

    // TODO: 25/04/19  als je uitlogt en als iemand anders inlogt gaan de statistics door op het vorige account

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_statistics, container, false);

        makeChart();
        firebaseData();

        return view;
    }

    private void makeChart(){
        BarChart chart = (BarChart) view.findViewById(R.id.chart);
        this.barChart = chart;
        this.barChart.setBorderColor(Color.WHITE);
        this.barChart.setNoDataTextColor(Color.WHITE);
        this.barChart.setBackgroundColor(Color.BLACK);
        this.barChart.setGridBackgroundColor(Color.BLACK);
    }

    private void setData(ArrayList<HashMap<String, Integer>> data){

        ArrayList<BarEntry> yVals = new ArrayList<>();
        final ArrayList<String> xVals = new ArrayList<>();

//        final String[] xVals = new String[2];


        int index = 0;

        for(HashMap<String, Integer> hashmap : data){
            Collection values = hashmap.values();
            int value = Integer.parseInt(values.toArray()[0].toString());
            yVals.add(new BarEntry(index, value));

            Collection keys = hashmap.keySet();
            String key = keys.toArray()[0].toString();
            xVals.add(key);
//            xVals[index] = key;

            index++;
        }

        BarDataSet set = new BarDataSet(yVals, "Dataset");
        set.setColors(Color.rgb(255,64,129),
                Color.rgb(255,121,176),
                Color.rgb(198,0,85));
        set.setDrawValues(true);


        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setTextColor(Color.WHITE);

        YAxis yAxis2 = barChart.getAxisRight();
        yAxis2.setAxisMinimum(0f);
        yAxis2.setTextColor(Color.WHITE);

        Legend legend = barChart.getLegend();
        legend.setTextColor(Color.WHITE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);


        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setGridColor(Color.WHITE);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals) {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return super.getFormattedValue(value-5f, axis);
            }
        });



        BarData bardata = new BarData(set);
        Description d = new Description();
        d.setText("");
        this.barChart.setDescription(d);
        this.barChart.setData(bardata);
        this.barChart.invalidate();
        this.barChart.animateY(500);
        this.barChart.setFitBars(true);
    }


    public static void addTrackToUserStatistics(final String trackTitle) {
        myRef.child(trackTitle).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    int value = dataSnapshot.getValue(Integer.class);
                    myRef.child(trackTitle).setValue(value + 1);
                }
                else if(!dataSnapshot.exists()){
                    myRef.child(trackTitle).setValue(1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void firebaseData(){

        final ArrayList<HashMap<String, Integer>> data = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object values = dataSnapshot.getValue();

                String[] pairs = values.toString().split(", ");

                for(String pair : pairs){
                    String finalPair = pair.replace("{", "").replace("}", "");
                    String[] titleAmount = finalPair.split("=");
                    HashMap<String, Integer> hashmap = new HashMap<String, Integer>();
                    hashmap.put(titleAmount[0], Integer.parseInt(titleAmount[1]));
                    data.add(hashmap);
                }
                setData(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
