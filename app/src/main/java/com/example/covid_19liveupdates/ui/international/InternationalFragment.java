package com.example.covid_19liveupdates.ui.international;

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

public class InternationalFragment extends Fragment {

    private TextView tvTotalIConfirmed, tvTotalIDeaths, tvTotalIRecovered;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_international, container, false);

        //call view
        tvTotalIConfirmed=root.findViewById(R.id.tvTotalIConfirmed);
        tvTotalIDeaths=root.findViewById(R.id.tvTotalIDeaths);
        tvTotalIRecovered=root.findViewById(R.id.tvTotalIRecovered);

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

                    tvTotalIConfirmed.setText(datajsonObject.getString("global_total_cases"));
                    tvTotalIDeaths.setText(datajsonObject.getString("global_deaths"));
                    tvTotalIRecovered.setText(datajsonObject.getString("global_recovered"));
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
