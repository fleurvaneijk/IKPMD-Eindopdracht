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
import android.widget.Button;

import com.example.ikpmd_eindopdracht.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class StatisticsFragment extends Fragment {

    private BarChart barChart;
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
        BarChart chart = (BarChart) view.findViewById(R.id.chart);
        this.barChart = chart;
        Description d = new Description();
        d.setText("Piechart die weergeeft hoeveel EC's in een jaar goed zijn.");
        barChart.setDescription(d);
        barChart.setTouchEnabled(false);
        barChart.getLegend().setEnabled(true);
        barChart.animateY(1400, Easing.EaseInOutQuad);

        setData(0);

//        Button plus = (Button) view.findViewById(R.id.plusTweeTest);
//        plus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (currentEcts < MAX_ECTS) {
//                    setData(currentEcts += 2);
//                } else {
//                    setData(currentEcts = 0);
//                }
//            }
//        });
//
//        Button min = (Button) view.findViewById(R.id.minTweeTest);
//        min.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (currentEcts < MAX_ECTS) {
//                    setData(currentEcts -= 2);
//                } else {
//                    setData(currentEcts = 0);
//                }
//            }
//        });
    }

    private void setData(int aantal) {
        currentEcts = aantal;
        List<BarEntry> yValues = new ArrayList<>();
        List<BarEntry> xValues = new ArrayList<>();

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

        // PieDataSet set = new PieDataSet(xValues, "Election Results");

        BarData data = new BarData(dataSet);
        barChart.setData(data); // bind dataset aan chart.
        barChart.invalidate();  // Aanroepen van een redraw
        Log.d("aantal =", ""+currentEcts);
    }
}
