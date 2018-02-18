package com.example.planet.masrelarabya102.UsedFragmentsPackage;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.planet.masrelarabya102.CardsViewPackage.NewsPreviewElements;
import com.example.planet.masrelarabya102.CardsViewPackage.RecyclerViewAdapter;
import com.example.planet.masrelarabya102.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
//import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SecondTab extends Fragment {

    RequestQueue rq;
    JsonObjectRequest requestSports;

    FirebaseRemoteConfig mfirebaseRemoteConfig;

    LinearLayoutManager llm;

    public void setCategoryNumber(int categoryNumber) {
        this.categoryNumber = categoryNumber;
    }

    int categoryNumber;
    String ip;

    RecyclerView rv2;
    RecyclerViewAdapter adapter;
    ArrayList<NewsPreviewElements> sportsPreviewsToShow ;

    //AVLoadingIndicatorView avi;


    // we need this variable to lock and unlock loading more
    // e.g we should not load more when volley is already loading,
    // loading will be activated when volley completes loading
    private boolean itShouldLoadMore = true;


    int pagenum = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second_tab, container, false);
        rq = Volley.newRequestQueue(getContext());

       //Calligrapher calligrapher = new Calligrapher(getContext());
        //calligrapher.setFont(getActivity(),"GE_SS_Two_Medium.ttf",true);

        mfirebaseRemoteConfig= FirebaseRemoteConfig.getInstance();
        long cacheExpiration = 0;
        mfirebaseRemoteConfig.fetch(cacheExpiration).addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    mfirebaseRemoteConfig.activateFetched();
                }
                else
                {

                }
                //Toast.makeText(getActivity(),mfirebaseRemoteConfig.getString("ip"),Toast.LENGTH_LONG).show();
                ip =mfirebaseRemoteConfig.getString("ip");
                /*String s =mfirebaseRemoteConfig.getString("");
                Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();*/
            }
        });

        sportsPreviewsToShow = new ArrayList<>();
        adapter = new RecyclerViewAdapter(sportsPreviewsToShow,getContext());

        rv2 = (RecyclerView) view.findViewById(R.id.recyclerView2);
        llm = new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false);
        rv2.setLayoutManager(llm);
        rv2.setHasFixedSize(true);

        rv2.setAdapter(adapter);

        //loadingbar
      //  avi = new AVLoadingIndicatorView(getContext());

        //get categ number
        categoryNumber = getArguments().getInt("CategoryNum");


        SecondTab(getContext(), categoryNumber);



        rv2.addOnScrollListener(new RecyclerView.OnScrollListener() {

            // for this tutorial, this is the ONLY method that we need, ignore the rest
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    // Recycle view scrolling downwards...
                    // this if statement detects when user reaches the end of recyclerView, this is only time we should load more
                    if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                        // remember "!" is the same as "== false"
                        // here we are now allowed to load more, but we need to be careful
                        // we must check if itShouldLoadMore variable is true [unlocked]
                        if (itShouldLoadMore) {
                            //avi .show();
                            pagenum=pagenum+18;
                            loadNextDataFromApi(pagenum,getContext(),categoryNumber);
                        }
                    }

                }
            }
        });




        return view;
    }

   public void SecondTab(final Context context, int categoryNumber) {
        // Required empty public constructor
        String sportsURL = mfirebaseRemoteConfig.getString("ip")+"json/category/" + categoryNumber + "/1";


           //avi.show();
           // or avi.smoothToShow();

        //final ArrayList<NewsPreviewElements> sportsPreviewsToShow = new ArrayList<>();
        requestSports = new JsonObjectRequest(Request.Method.GET, sportsURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // show.setText(response.toString());
                try {
                    JSONArray responceArray = response.getJSONArray("items");

                    //avi.hide();

                    JSONObject item = new JSONObject();
                    String imgURL;
                    for (int i = 0; i < responceArray.length(); i++) {
                        item = responceArray.getJSONObject(i);
                        //if first item set as header card
                        if (i == 0) {
                            imgURL = "http://misralarabiya.net/" + item.getString("images");
                            String intro = item.getString("intro");
                            intro = intro.replace("&quot;", "'");
                            intro = intro.replace("&laquo;", "'");
                            intro = intro.replace("&raquo;", "'");
                            intro = intro.replaceAll("&nbsp", "");
                            intro= intro.replaceAll("&ndash","-");
                            intro = intro.replaceAll(";", " ");
                            intro = intro.replaceAll("\n", "");

                            String date =item.getString("created");
                            date = date.substring(0,date.indexOf(" "));

                            sportsPreviewsToShow.add(new NewsPreviewElements(NewsPreviewElements.HEADER_TYPE, item.getString("title"), intro, imgURL, item.getInt("id"),date));
                        }
                        //if not first item set as preview card
                        else {
                            imgURL = "http://misralarabiya.net/" + item.getString("images");
                            String intro = item.getString("intro");
                            intro = intro.replace("&quot;", "'");
                            intro = intro.replace("&laquo;", "'");
                            intro = intro.replace("&raquo;", "'");
                            intro = intro.replaceAll("&nbsp", "");
                            intro= intro.replaceAll("&ndash","-");
                            intro = intro.replaceAll("\n", "");

                            String date =item.getString("created");
                            date = date.substring(0,date.indexOf(" "));


                            sportsPreviewsToShow.add(new NewsPreviewElements(NewsPreviewElements.SUBITEM_TYPE, item.getString("title"), intro, imgURL, item.getInt("id"),date));
                        }

                    }

                    //llm = new LinearLayoutManager(context, OrientationHelper.VERTICAL, false);
                   // rv2.setLayoutManager(llm);
                   //RecyclerViewAdapter
                           //adapter = new RecyclerViewAdapter(sportsPreviewsToShow, context);

                    adapter.notifyDataSetChanged();
                    //rv2.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        rq.add(requestSports);

    }


    public void loadNextDataFromApi(int offset, final Context context , int categoryNumber) {

        String sportsURL = mfirebaseRemoteConfig.getString("ip")+"json/category/" + categoryNumber + "/"+offset;

        itShouldLoadMore = false; // lock this until volley completes processing

        //avi.show();

        requestSports = new JsonObjectRequest(Request.Method.GET, sportsURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // show.setText(response.toString());
                //avi.hide();
                itShouldLoadMore = true;


                try {
                    JSONArray responceArray = response.getJSONArray("items");
                    JSONObject item = new JSONObject();
                    String imgURL;
                    for (int i = 0; i < responceArray.length(); i++) {
                        item = responceArray.getJSONObject(i);
                        //if first item set as header card
                        if (i == 19) {
                            imgURL = "http://misralarabiya.net/" + item.getString("images");
                            String intro = item.getString("intro");
                            intro = intro.replace("&quot;", "'");
                            intro = intro.replace("&laquo;", "'");
                            intro = intro.replace("&raquo;", "'");
                            intro = intro.replaceAll("&nbsp", "");
                            intro= intro.replaceAll("&ndash","-");
                            intro = intro.replaceAll("\n", "");

                            String date =item.getString("created");
                            date = date.substring(0,date.indexOf(" "));
                            sportsPreviewsToShow.add(new NewsPreviewElements(NewsPreviewElements.HEADER_TYPE, item.getString("title"), intro, imgURL, item.getInt("id"),date));
                        }
                        //if not first item set as preview card
                        else {
                            imgURL = "http://misralarabiya.net/" + item.getString("images");
                            String intro = item.getString("intro");
                            intro = intro.replace("'", " ");
                            intro = intro.replace("&quot;", "'");
                            intro = intro.replace("&laquo;", "'");
                            intro = intro.replace("&raquo;", "'");
                            intro = intro.replaceAll("&nbsp", "");
                            intro= intro.replaceAll("&ndash","-");
                            intro = intro.replaceAll("\n", "");

                            String date =item.getString("created");
                            date = date.substring(0,date.indexOf(" "));


                            sportsPreviewsToShow.add(new NewsPreviewElements(NewsPreviewElements.SUBITEM_TYPE, item.getString("title"), intro, imgURL, item.getInt("id"),date));
                        }

                    }


                    //adapter = new RecyclerViewAdapter(sportsPreviewsToShow, context);
                    adapter.notifyDataSetChanged();
                    //rv2.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        rq.add(requestSports);
    }





}