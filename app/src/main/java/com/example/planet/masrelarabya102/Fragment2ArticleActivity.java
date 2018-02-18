package com.example.planet.masrelarabya102;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.planet.masrelarabya102.CardsViewPackage.NewsPreviewElements;
import com.example.planet.masrelarabya102.CardsViewPackage.RecyclerViewAdapter;
import com.github.clans.fab.FloatingActionMenu;
//import com.melnykov.fab.FloatingActionButton;
//import com.scalified.fab.ActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment2ArticleActivity extends AppCompatActivity {
    RequestQueue rq;
    JsonObjectRequest requestArticles,requestRelated;
    RecyclerView aRV;
    TextView show;
   // ActionButton actionButton;
    com.github.clans.fab.FloatingActionButton aB;
    Intent newIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment2_article);
        rq= Volley.newRequestQueue(this);
        show = (TextView)findViewById(R.id.show);
        aRV= (RecyclerView) findViewById(R.id.Article2recyclerView);
        aB =(com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab);
        aB.hide(true);


      /*  actionButton = (ActionButton) findViewById(R.id.action_button);
        actionButton.setButtonColor(getResources().getColor(R.color.Blue));
        actionButton.setButtonColorPressed(getResources().getColor(R.color.DarkBlue));
        actionButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_home));
        actionButton.hide();
        final boolean hidden = actionButton.isHidden();*/
         newIntent = new Intent(this,MainActivity.class);
        aRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            aB.show(true);
                            aB.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   // Intent newIntent = new Intent(this,MainActivity.class);
                                    newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(newIntent);
                                }
                            });
                           /* actionButton.playShowAnimation();
                            actionButton.show();*/



                    }
                    else{
                        aB.hide(true);
                    }

                }
            }
        });

        Long articleID = getIntent().getLongExtra("ArticleId",000);

        articlesPreview(this,articleID);
    }

   public void articlesPreview(final Context context,Long id)
    {

        String  ArticleURL = "http://misralarabiya.net/json/article/"+id;
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
                   // content= content.replaceAll("\n","");

                    String date =item.getString("created");

                    PreviewsToShow.add(new NewsPreviewElements(NewsPreviewElements.HEADER_TYPE,item.getString("title"),content,imgURL,item.getInt("id"),date));
                    //JSONObject relateditem = new JSONObject();
                    JSONArray relatedNews = response.getJSONArray("related");


                    for (int i =0 ; i< relatedNews.length();i++ )
                    {
                        //related item inside certain cell
                        JSONObject relateditem = relatedNews.getJSONObject(i);

                        //request item details , get the date then send it to constructor

                        String alias =item.getString("alias");
                        alias = alias.replace("-", "'");
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
