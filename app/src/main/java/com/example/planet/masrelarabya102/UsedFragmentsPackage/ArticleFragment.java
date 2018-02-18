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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */


public class ArticleFragment extends Fragment {


    FirebaseRemoteConfig mfirebaseRemoteConfig;

    RequestQueue rq;
    JsonObjectRequest requestArticles,requestRelatedItemDate;
    RecyclerView aRV;
    Long myInt;

    public ArticleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


       //Calligrapher calligrapher = new Calligrapher(getContext());
       //calligrapher.setFont(getActivity(),"SST-Arabic-Bold.ttf",true);

        View view = inflater.inflate(R.layout.fragment_article, container, false);

        rq= Volley.newRequestQueue(getContext());

        mfirebaseRemoteConfig= FirebaseRemoteConfig.getInstance();
        mfirebaseRemoteConfig.fetch().addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    mfirebaseRemoteConfig.activateFetched();
                }
                else
                {

                }
               // Toast.makeText(getContext(),mfirebaseRemoteConfig.getString("ip"),Toast.LENGTH_LONG).show();
                /*String s =mfirebaseRemoteConfig.getString("");
                Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();*/
            }
        });

        aRV= (RecyclerView) view.findViewById(R.id.FragmentArticle2recyclerView);

        Bundle bundle = this.getArguments();if (bundle != null) {
             myInt = bundle.getLong("ArticleId");
        }


       //Long articleID = getIntent().getLongExtra("ArticleId",000);

        articlesPreview(getContext(),myInt);
        // Inflate the layout for this fragment

        return view;
    }

    public void articlesPreview(final Context context, Long id)
    {

        String  ArticleURL = mfirebaseRemoteConfig.getString("ip")+"json/article/"+id;
        final ArrayList<NewsPreviewElements> PreviewsToShow=new ArrayList<>();
        requestArticles = new JsonObjectRequest(Request.Method.GET, ArticleURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    JSONObject item = response.getJSONObject("items");
                    //show.setText(item.toString());
                    String imgURL;
                    imgURL ="http://misralarabiya.net/"+item.getString("images");
                    String content =item.getString("content");
                    content= content.replace("&quot;", "'");
                    content= content.replace("&laquo;", "'");
                    content= content.replace("&raquo;", "'");
                    content= content.replaceAll("&nbsp","");
                    content= content.replaceAll("&ndash","-");
                    //content= content.replaceAll("\n","");
                    content= content.replaceAll(";","");

                    String date =item.getString("created");
                    date = date.substring(0,date.indexOf(" "));

                    PreviewsToShow.add(new NewsPreviewElements(NewsPreviewElements.HEADER_TYPE,item.getString("title"),content,imgURL,item.getInt("id"),date));

                    //JSONObject relateditem = new JSONObject();
                    JSONArray relatedNews = response.getJSONArray("related");
                    for (int i =0 ; i< relatedNews.length();i++ )
                    {
                        JSONObject relateditem = relatedNews.getJSONObject(i);

                        String alias =relateditem.getString("alias");
                        alias = alias.replace("-", " ");
                        imgURL ="http://misralarabiya.net/"+relateditem.getString("images");

                        String thedate ="";

                        PreviewsToShow.add(new NewsPreviewElements(NewsPreviewElements.SUBITEM_TYPE,relateditem.getString("title"),alias,imgURL,relateditem.getInt("id"),thedate));
                    }


                    LinearLayoutManager llm = new LinearLayoutManager(context, OrientationHelper.VERTICAL,false);
                    aRV.setLayoutManager(llm);
                    RecyclerViewAdapter adapter =new RecyclerViewAdapter(PreviewsToShow,context);
                    aRV.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        rq.add(requestArticles);
    }

}
