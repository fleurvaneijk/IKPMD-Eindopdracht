package com.example.ikpmd_eindopdracht.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ikpmd_eindopdracht.R;
import com.firebase.ui.auth.AuthUI;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsFragment extends Fragment {

    private static String TAG = "statistics";

    private static HashMap<String, Integer> playedTracks = new HashMap<String, Integer>();
    private HorizontalBarChart barChart;
    private View view;
    public static int currentEcts = 0;

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static String userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private static DatabaseReference myRef = database.getReference("statistics/" + userUID);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_statistics, container, false);

        this.makeChart();

        return view;
    }

    private void makeChart(){
        HorizontalBarChart chart = (HorizontalBarChart) view.findViewById(R.id.chart);
        this.barChart = chart;
    }

    public static void addTrackToUserStatistics(final String trackTitle) {
        myRef.child(trackTitle).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Log.d(TAG, "onDataChange: " + dataSnapshot);
                    int value = dataSnapshot.getValue(Integer.class);
                    myRef.child(trackTitle).setValue(value + 1);
                }
                else if(!dataSnapshot.exists()){
                    Log.d(TAG, "onDataChange: hij komt in de else!");
                    myRef.child(trackTitle).setValue(1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void setData(int aantal) {
        List<BarEntry> yValues = new ArrayList<>();
        List<BarEntry> xValues = new ArrayList<>();


        for (Map.Entry<String, Integer> entry : playedTracks.entrySet()) {
            String key = entry.getKey();
            int value = (Integer)entry.getValue();

//            yValues.add(new BarEntry(key, value));
        }

        yValues.add(new BarEntry(aantal, 0));
        xValues.add(new BarEntry(aantal, 60));

        yValues.add(new BarEntry(60, 1));
        xValues.add(new BarEntry(60, 60));

        //  http://www.materialui.co/colors
        ArrayList<Integer> colors = new ArrayList<>();
        if (currentEcts <10) {
            colors.add(Color.MAGENTA);
        } else if (currentEcts < 40){
            colors.add(Color.RED);
        } else if  (currentEcts < 50) {
            colors.add(Color.YELLOW);
        } else {
            colors.add(Color.GREEN);
        }
        colors.add(Color.GRAY);

        BarDataSet dataSet = new BarDataSet(yValues, "ECTS");
        dataSet.setColors(colors);//colors);


        BarData data = new BarData(dataSet);
        barChart.setData(data); // bind dataset aan chart.
        barChart.invalidate();  // Aanroepen van een redraw
        Log.d("aantal =", ""+currentEcts);
    }


}
