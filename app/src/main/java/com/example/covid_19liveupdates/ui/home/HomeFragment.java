package com.example.covid_19liveupdates.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid_19liveupdates.R;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {

    private TextView tvTotalConfirmed, tvTotalDeaths, tvTotalRecovered;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //call view
        tvTotalConfirmed=root.findViewById(R.id.tvTotalConfirmed);
        tvTotalDeaths=root.findViewById(R.id.tvTotalDeaths);
        tvTotalRecovered=root.findViewById(R.id.tvTotalRecovered);

        getData();

        return root;
    }

    private void getData() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        String url = "https://hpb.health.gov.lk/api/get-current-statistical";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject datajsonObject = new JSONObject(jsonObject.getString("data"));

                    tvTotalConfirmed.setText(datajsonObject.getString("local_total_cases"));
                    tvTotalDeaths.setText(datajsonObject.getString("local_deaths"));
                    tvTotalRecovered.setText(datajsonObject.getString("local_recovered"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error Response", error.toString());
            }

    });

        queue.add(stringRequest);

    }
}