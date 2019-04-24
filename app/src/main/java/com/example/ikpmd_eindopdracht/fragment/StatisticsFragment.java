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
import com.example.ikpmd_eindopdracht.model.Track;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsFragment extends Fragment {

    private static HashMap<String, Integer> favouritedTracks = new HashMap<String, Integer>();
    private HorizontalBarChart barChart;
    private View view;
    public static final int MAX_ECTS = 60;
    public static int currentEcts = 0;

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

    private void setData(int aantal) {
        List<BarEntry> yValues = new ArrayList<>();
        List<BarEntry> xValues = new ArrayList<>();


        for (Map.Entry<String, Integer> entry : favouritedTracks.entrySet()) {
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

    public static void addToFavouritedTracks (String trackTitle) {
        if(favouritedTracks.containsKey(trackTitle)){
            int value = (Integer)favouritedTracks.get(trackTitle);
            favouritedTracks.put(trackTitle, value + 1);
        }
        else {
            favouritedTracks.put(trackTitle, 1);
        }
    }
}
