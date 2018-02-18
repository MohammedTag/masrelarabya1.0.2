package com.example.planet.masrelarabya102;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.planet.masrelarabya102.UsedFragmentsPackage.SecondTab;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    FirebaseRemoteConfig mfirebaseRemoteConfig;

    private static final String TAG = "Main Activity";
    private SectionPageAdapter mSectionaPageAdapter;

    private ViewPager mViewPager;

    HashMap<Long,String> tabsids;

    String ip;

    RequestQueue rq,q1;
    JsonObjectRequest requestimages,requestmenuItems,requestMainNews,requestPreviews,requestIds;

    String sliderimagesurl,tapsCategoriesURL,drawercatsURL;

    TextView show;


    JSONArray items=new JSONArray();
    JSONArray result=new JSONArray();

    RecyclerView rv;
    android.support.v7.widget.Toolbar toolbar;

    Drawer d1;

     SectionPageAdapter adapter;

     TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mfirebaseRemoteConfig= FirebaseRemoteConfig.getInstance();
        long cacheExpiration = 0;
        mfirebaseRemoteConfig.fetch(cacheExpiration).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    mfirebaseRemoteConfig.activateFetched();
                }
                else
                {

                }
               //Toast.makeText(MainActivity.this,mfirebaseRemoteConfig.getString("ip"),Toast.LENGTH_LONG).show();
                ip =mfirebaseRemoteConfig.getString("ip");
                /*String s =mfirebaseRemoteConfig.getString("");
                Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();*/
            }
        });

        ImageView logo =(ImageView) findViewById(R.id.logoimageview);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);



        tabsids = new HashMap<Long,String>();

        TextView show = (TextView)findViewById(R.id.show);
        rq = Volley.newRequestQueue(this);
        mSectionaPageAdapter =new SectionPageAdapter(getSupportFragmentManager());
        //setting up the viewpager for tabs activity
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        //final TabLayout

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentManager fm = getSupportFragmentManager();
                for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }

                //mFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                FragmentManager fm = getSupportFragmentManager();
                for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }
            }
        });
   // Calligrapher calligrapher = new Calligrapher(this);
   // calligrapher.setFont(this,"GE_SS_Two_Medium.ttf",true);

        toolbar = (Toolbar)findViewById(R.id.toolbar);

        //toolbar.setBackgroundColor(getResources().getColor(R.color.LightGrey));

        //create the ItemAdapter holding your Items


    /**    SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(1).withName("اخبار مصر");
        SecondaryDrawerItem item3 = new SecondaryDrawerItem().withIdentifier(2).withName("رياضة مصرية");
        SecondaryDrawerItem item4 = new SecondaryDrawerItem().withIdentifier(3).withName("حوادث");
        SecondaryDrawerItem item5 = new SecondaryDrawerItem().withIdentifier(4).withName("اقتصاد");
        SecondaryDrawerItem item6 = new SecondaryDrawerItem().withIdentifier(5).withName("فن وثقافة");
        SecondaryDrawerItem item7 = new SecondaryDrawerItem().withIdentifier(6).withName("منوعات");*/


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    /*    Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(false)
                .withActionBarDrawerToggle(true)
                .withToolbar(toolbar)
                .addDrawerItems(
                        //item1,
                        new DividerDrawerItem(),item2,item3,item4,item5,item6,item7
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D

                        switch (position) {
                            case 1:break;
                            case 2:break;

                        }

                        return true;}
                })
                .build();*/


        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.masr_03).build();
    d1 = new DrawerBuilder().withActivity(this).withTranslucentStatusBar(false)
            .withActionBarDrawerToggle(true).withAccountHeader(headerResult).withSliderBackgroundColor(Color.parseColor("#1e485a"))
            .withToolbar(toolbar).build();
    final ArrayList<String> drawerItemsNames = new ArrayList<>();



        drawercatsURL= mfirebaseRemoteConfig.getString("ip")+"cats.json";

        requestIds = new JsonObjectRequest(Request.Method.GET, drawercatsURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray drawerresults = response.getJSONArray("results");
                   // ArrayList<SecondaryDrawerItem> dITEMS = new ArrayList<>(drawerresults.length());
                    for (int i = 0 ;i<drawerresults.length();i++)
                    {
                        JSONObject drawerItem = drawerresults.getJSONObject(i);
                        //dITEMS.add(i,new SecondaryDrawerItem().withIdentifier(drawerItem.getInt("id")).withName(drawerItem.getString("title")));
                        drawerItemsNames.add(i,drawerItem.getString("title"));
                        d1.addItem(new SecondaryDrawerItem().withIcon(R.drawable.button).withIdentifier(drawerItem.getInt("id")).withName(drawerItem.getString("title")).withTextColor(Color.parseColor("#ffffff")));
                        d1.addItem(new DividerDrawerItem());

                    }

                  //  d1.addItem(new SecondaryDrawerItem().withIcon(R.drawable.button).withIdentifier(50505050).withName("مقالات").withTextColor(Color.parseColor("#ffffff")));


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        rq.add(requestIds);

        d1.setOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                //int id = (int)drawerItem.getIdentifier();
               // String tapName= drawerItemsNames.get(position);
               // Fragment frag = adapter.getItem(position);
               /* if (drawerItem.getIdentifier() == 50505050)
                {
                    Intent intent = new Intent(getApplicationContext(), ArticleActivity.class);
                    startActivity(intent);
                }
                else*/

                long id = drawerItem.getIdentifier();
                ArrayList<String> titles = new ArrayList<>();
                ArrayList<Long> titlesplaces = new ArrayList<>();
               /* for (int i = 0 ; i<tabLayout.getTabCount();i++)
                {
                    titles.add(adapter.getPageTitle(i).toString());
                    titles.add(adapter.get);
                }*/

               for (int i = 0 ; i < adapter.getCount();i++)
               {
                   if (adapter.getPageTitle(i).equals(tabsids.get(id)))
                   {
                       mViewPager.setCurrentItem(i);
                   }
               }

                //adapter.getPageTitle()
                //long temp = tabsids.get(id);

              /*  for (int i = 0 ; i<titles.size();i++)
                {
                    if ( titles.get(i).equals(tabsids.get(id)))
                    {
                        //string retuned == title
                        if ((adapter.getPageTitle(i).equals(String))){
                            mViewPager.setCurrentItem(i);
                        }
                    }

                }*/



               // mViewPager.setCurrentItem(position);




                return false;
            }
        });



    //    rq = Volley.newRequestQueue(this);

        // settingNavigationButtons();



        //formSliderImagesData();


        //ArrayList<String> categoriesNames =new ArrayList<>();
        // categoriesNames = drawerMenuFormating(this);
        // drawerMenuFormating(this);

        //drawerMenuFormating();

     /* ArrayList<NewsPreviewElements> newsMock = new ArrayList<>();
        newsMock.add(new NewsPreviewElements(NewsPreviewElements.HEADER_TYPE,"Emma Wilson", "23 years old", R.drawable.economy));
        newsMock.add(new NewsPreviewElements(NewsPreviewElements.SUBITEM_TYPE,"Lavery Maiss", "25 years old", R.drawable.sports));
        newsMock.add(new NewsPreviewElements(NewsPreviewElements.SUBITEM_TYPE,"Lillie Watts", "35 years old", R.drawable.news));


        LinearLayoutManager llm = new LinearLayoutManager(this, OrientationHelper.VERTICAL,false);
        rv.setLayoutManager(llm);
        RecyclerViewAdapter adapter =new RecyclerViewAdapter(newsMock,this);
        rv.setAdapter(adapter);*/


        //
        // .formattinglatestnews(this);
        //letstry();


    }
    

    private void setupViewPager(final ViewPager viewpager)
    {

       adapter = new SectionPageAdapter(getSupportFragmentManager());

        tapsCategoriesURL = mfirebaseRemoteConfig.getString("ip") +"cats.json";



       requestPreviews = new JsonObjectRequest(Request.Method.GET, tapsCategoriesURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray results = response.getJSONArray("results");
                    //show.setText(results.toString());

       /* ArrayList<String> cats = new ArrayList<>();
        ArrayList<Integer> catsid = new ArrayList<>();
        cats.add("اخبار");
        cats.add("اقتصاد");
        cats.add("منوعات");
        catsid.add(8);
        catsid.add(25);
        catsid.add(8);*/

                    for (int i =0 ; i <results.length(); i++)
                    {
                       JSONObject item = results.getJSONObject(i);
                        String title = item.getString("title");
                        int id = item.getInt("id");
                        long catIdCompare = (long) id;
                        tabsids.put(catIdCompare,title);


                        Bundle data = new Bundle();//create bundle instance
                        data.putInt("CategoryNum",id);//put string to pass with int value
                        Fragment fragment = new SecondTab();
                        fragment.setArguments(data);
                        adapter.addFragment(fragment,title);
                    }
                    viewpager.setAdapter(adapter);

              } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        rq.add(requestPreviews);

     /*   Bundle data = new Bundle();//create bundle instance
        data.putInt("CategoryNum",8);//put string to pass with int value
        Fragment fragment = new SecondTab();
        fragment.setArguments(data);
        adapter.addFragment(fragment, "hi1");*/



       /* adapter.addFragment(new Main(),"اخبار مصر");
        //else

        adapter.addFragment(new SecondTab(),"رياضة مصرية");
        adapter.addFragment(new ThirdTab(),"حوادث");
        adapter.addFragment(new FourthTab(),"اقتصاد");
        adapter.addFragment(new FifthTab(),"فن وثقافة");
        adapter.addFragment(new sixthTab(),"منوعات");*/

    }


  /*  public void setCustomFont() {

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();

        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);

            int tabChildsCount = vgTab.getChildCount();

            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    //Put your font in assests folder
                    //assign name of the font here (Must be case sensitive)
                    ((TextView) tabViewChild).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/SST-Arabic-Bold.ttf"));
                }
            }
        }
    }*/
}
