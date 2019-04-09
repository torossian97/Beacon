package com.mistbeacon.beacon;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import io.opencensus.trace.Span;

import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;

import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link monthlyChart.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link monthlyChart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class monthlyChart extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View rootView;

    private OnFragmentInteractionListener mListener;

    public monthlyChart() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment monthlyChart.
     */
    // TODO: Rename and change types and number of parameters
    public static monthlyChart newInstance(String param1, String param2) {
        monthlyChart fragment = new monthlyChart();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    //class to change the dates decoration
    class lowDeco implements DayViewDecorator {

        @Override
        public boolean shouldDecorate(CalendarDay day) {

            if(day.today().getMonth() == day.getMonth()){
                return ((day.getDay()%4 == 0) && (day.getDay() <= day.today().getDay()));
            }

            return (day.getDay()%3 == (int) Math.floor(Math.random()*30)%3 && (day.getMonth() < day.today().getMonth() || (day.getDay() <= day.today().getDay() && day.getMonth() == day.today().getMonth())));
        }

        @Override
        public void decorate(DayViewFacade view) {
            ColorDrawable cd = new ColorDrawable(0xFF838CA8);;
            view.setBackgroundDrawable(cd);

            view.addSpan(new ForegroundColorSpan(Color.WHITE));
        }
    }

    class midDeco implements DayViewDecorator {

        @Override
        public boolean shouldDecorate(CalendarDay day) {

            if(day.today().getMonth() == day.getMonth()){
                return ((day.getDay()%4 == 1) && (day.getDay() <= day.today().getDay()));
            }

            return (day.getDay()%3 == (int) Math.floor(Math.random()*30)%3 && (day.getMonth() < day.today().getMonth() || (day.getDay() <= day.today().getDay() && day.getMonth() == day.today().getMonth())));
        }

        @Override
        public void decorate(DayViewFacade view) {
            ColorDrawable cd = new ColorDrawable(0xFF5B678E);;
            view.setBackgroundDrawable(cd);
            view.addSpan(new ForegroundColorSpan(Color.WHITE));
        }
    }

    class hiDeco implements DayViewDecorator {

        @Override
        public boolean shouldDecorate(CalendarDay day) {

            if(day.today().getMonth() == day.getMonth()){
                return ((day.getDay()%4 == 2) && (day.getDay() <= day.today().getDay()));
            }

            return (day.getDay()%3 == (int) Math.floor(Math.random()*30)%3 && (day.getMonth() < day.today().getMonth() || (day.getDay() <= day.today().getDay() && day.getMonth() == day.today().getMonth())));
        }

        @Override
        public void decorate(DayViewFacade view) {
            ColorDrawable cd = new ColorDrawable(0xFF223368);;
            view.setBackgroundDrawable(cd);
            view.addSpan(new ForegroundColorSpan(Color.WHITE));
        }
    }

    class critDeco implements DayViewDecorator {

        @Override
        public boolean shouldDecorate(CalendarDay day) {

            if(day.today().getMonth() == day.getMonth()){
                return ((day.getDay()%4 == 3) && (day.getDay() <= day.today().getDay()));
            }

            return (day.getDay()%10 == (int) Math.floor(Math.random()*30)%10 && (day.getMonth() < day.today().getMonth() || (day.getDay() <= day.today().getDay() && day.getMonth() == day.today().getMonth())));
        }

        @Override
        public void decorate(DayViewFacade view) {
            ColorDrawable cd = new ColorDrawable(0xFFffb300);;
            view.setBackgroundDrawable(cd);
            view.addSpan(new ForegroundColorSpan(Color.WHITE));
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_monthly_chart, container, false);

        MaterialCalendarView calendar = (MaterialCalendarView) rootView.findViewById(R.id.calendarView);
        //calendar.setBackgroundResource(R.drawable.bg5);
        calendar.setSelectionColor(Color.parseColor("#223368"));
        calendar.addDecorators(new lowDeco(), new midDeco(), new hiDeco(), new critDeco());

        calendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay, boolean b) {
                TextView mostStress = (TextView) getActivity().findViewById(R.id.mostStress);
                View view = (View) getActivity().findViewById(R.id.view);
                TextView locationText = (TextView) getActivity().findViewById(R.id.locationText);
                TextView heartText = (TextView) getActivity().findViewById(R.id.heartText);
                TextView screenText = (TextView) getActivity().findViewById(R.id.screenText);
                TextView walkingText = (TextView) getActivity().findViewById(R.id.walkingText);

                String color = "#838CA8";
                int loc = 0;
                int hea = 0;
                String scr = "Very low";
                double wal = 0;

                switch(calendarDay.getDay()%4){
                    case 0: color = "#838CA8"; loc = 8; hea = 3; scr = "Very low"; wal = 8;
                        break;
                    case 1: color = "#5B678E"; loc = 8; hea = 3; scr = "lLow"; wal = 5.3;
                        break;
                    case 2: color = "#223368"; loc = 2; hea = 8; scr = "Moderate"; wal = 2.1;
                        break;
                    case 3: color = "#ffb300"; loc = 1; hea = 12; scr = "Very high"; wal = 1;
                        break;
                }

                mostStress.setTextColor(Color.parseColor(color));
                view.setBackgroundColor(Color.parseColor(color));
                locationText.setTextColor(Color.parseColor(color));
                heartText.setTextColor(Color.parseColor(color));
                screenText.setTextColor(Color.parseColor(color));
                walkingText.setTextColor(Color.parseColor(color));

                locationText.setText(loc + " locations");
                heartText.setText(hea + " spikes");
                screenText.setText(scr);
                walkingText.setText(wal + " km");
            }
        });

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
