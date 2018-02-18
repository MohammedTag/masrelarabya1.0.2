package com.example.planet.masrelarabya102.CardsViewPackage;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.planet.masrelarabya102.UsedFragmentsPackage.ArticleFragment;
import com.example.planet.masrelarabya102.R;

import java.util.ArrayList;

/**
 * Created by Planet on 1/16/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter {

    private ArrayList<NewsPreviewElements> dataSet;
    Context mContext;
    Activity activity;
    int total_types;

   // Typeface myfont = Typeface.createFromAsset(mContext.getAssets(),"fonts/SST-Arabic-Bold.ttf");



    public RecyclerViewAdapter(ArrayList<NewsPreviewElements> dataSet, Context mContext) {
        this.dataSet = dataSet;
        this.mContext = mContext;

    }
    //CardView imagecv,cv1,cv2;
    //TextView title,title1,title2;
    //TextView preview,preview1,preview2;
    //ImageView imageCard,image1,image2;

    public static class HeaderHolder extends RecyclerView.ViewHolder
    {
        CardView imagecv;
        TextView title;
        TextView preview;
        ImageView imageCard;

        TextView headerDate;
        HeaderHolder(View itemView) {
            super(itemView);
            imagecv = (CardView)itemView.findViewById(R.id.cardviewimage);
            title = (TextView)itemView.findViewById(R.id.title);
            preview = (TextView)itemView.findViewById(R.id.previewtext);
            imageCard = (ImageView)itemView.findViewById(R.id.imagecard);
            headerDate = (TextView)itemView.findViewById(R.id.dateText);

              /*  cv1 = (CardView)itemView.findViewById(R.id.cardview);
                title1 = (TextView)itemView.findViewById(R.id.title1);
                preview1 = (TextView)itemView.findViewById(R.id.previewtext1);
                image1 = (ImageView)itemView.findViewById(R.id.newscardimage1);*/

           /* cv2 = (CardView)itemView.findViewById(R.id.cardview2);
            title2 = (TextView)itemView.findViewById(R.id.title2);
            preview2 = (TextView)itemView.findViewById(R.id.previewtext2);
            image2 = (ImageView)itemView.findViewById(R.id.newscardimage2);*/
        }

    }

    public static class PreviewViewHolder extends RecyclerView.ViewHolder
    {
        CardView cv1;
        TextView title1;
        TextView preview1;
        ImageView image1;

        TextView previewDate;

        PreviewViewHolder(View itemView) {
            super(itemView);
               /* imagecv = (CardView)itemView.findViewById(R.id.cardviewimage);
                title = (TextView)itemView.findViewById(R.id.title);
                preview = (TextView)itemView.findViewById(R.id.previewtext);
                imageCard = (ImageView)itemView.findViewById(R.id.imagecard);*/

            cv1 = (CardView)itemView.findViewById(R.id.cardview);
            title1 = (TextView)itemView.findViewById(R.id.title1);
            preview1 = (TextView)itemView.findViewById(R.id.previewtext1);
            image1 = (ImageView)itemView.findViewById(R.id.newscardimage1);
            previewDate = (TextView)itemView.findViewById(R.id.dateText);

           /* cv2 = (CardView)itemView.findViewById(R.id.cardview2);
            title2 = (TextView)itemView.findViewById(R.id.title2);
            preview2 = (TextView)itemView.findViewById(R.id.previewtext2);
            image2 = (ImageView)itemView.findViewById(R.id.newscardimage2);*/
        }

    }

    public void RecyclerViewAdapter(ArrayList<NewsPreviewElements>data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        switch (viewType)
        {

            case NewsPreviewElements.HEADER_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header, parent, false);
                return new RecyclerViewAdapter.HeaderHolder(view);
            case NewsPreviewElements.SUBITEM_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
                return new RecyclerViewAdapter.PreviewViewHolder(view);

        }
        return null;
        //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);

        //NewsViewHolder nvh = new NewsViewHolder(v);
        //return nvh;
    }

    @Override
    public int getItemViewType(int position) {
        switch (dataSet.get(position).type)
        {
            case 1:
                return NewsPreviewElements.HEADER_TYPE;
            case 2:
                return NewsPreviewElements.SUBITEM_TYPE;
            default:
                return -1;
        }
    }



    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        final NewsPreviewElements object = dataSet.get(position);


        if (object != null) {
            switch (object.type) {
                case NewsPreviewElements.HEADER_TYPE:

                    //Typeface myfont = Typeface.createFromAsset(mContext.getAssets(),"fonts/SST-Arabic-Bold.ttf");
                   // ((HeaderHolder) holder).title.setTypeface(myfont);
                    ((HeaderHolder) holder).title.setText(object.title);

                   // ((HeaderHolder) holder).preview.setTypeface(myfont);
                    ((HeaderHolder) holder).preview.setText(object.preveiew);

                    ((HeaderHolder) holder).headerDate.setText(object.date);
                    //using glide to load image to the image view (hopefullyworks)
                    //Glide.with(mContext).load(object.imgurl).into(((HeaderHolder) holder).imageCard);
                    Glide.with(mContext)
                            .load(object.imgurl)
                            .apply(requestOptions)
                            .into(((HeaderHolder) holder).imageCard);

                    ((HeaderHolder)holder).imagecv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                          /*  Intent intent = new Intent(mContext, Fragment2ArticleActivity.class);
                            intent.putExtra("ArticleId",object.getId());
                            mContext.startActivity(intent);*/

                          ArticleFragment article = new ArticleFragment();
                            Bundle bdl=new Bundle();
                            bdl.putLong("ArticleId",object.getId());
                            article.setArguments(bdl);

                            AppCompatActivity activity = (AppCompatActivity) view.getContext();

                            //Create a bundle to pass data, add data, set the bundle to your fragment and:
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_content, article).addToBackStack(null).commit();
                            //activity.getFragmentManager().beginTransaction().add(R.layout.fragment_article,article).addToBackStack(null).commit();

                         /*  // Note that we need the API version check here because the actual transition classes (e.g. Fade)
                            // are not in the support library and are only available in API 21+. The methods we are calling on the Fragment
                            // ARE available in the support library (though they don't do anything on API < 21)
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                details.setSharedElementEnterTransition(new DetailsTransition());
                                details.setEnterTransition(new Fade());
                                setExitTransition(new Fade());
                                details.setSharedElementReturnTransition(new DetailsTransition());
                            }

                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .addSharedElement(holder.image, "sharedImage")
                                    .replace(R.id.container, details)
                                    .addToBackStack(null)
                                    .commit();*/
                        }
                    });
                    //((HeaderHolder) holder).imageCard.setBackgroundResource(object.image);



                    break;
                case NewsPreviewElements.SUBITEM_TYPE:
                    //((PreviewViewHolder) holder).title1.setTypeface(myfont);
                    ((PreviewViewHolder) holder).title1.setText(object.title);

                    //((PreviewViewHolder) holder).preview1.setTypeface(myfont);
                    ((PreviewViewHolder) holder).preview1.setText(object.preveiew);

                    ((PreviewViewHolder) holder).previewDate.setText(object.date);
                    //using glide to load image to the image view (hopefullyworks)

                    Glide.with(mContext).load(object.imgurl).into(((PreviewViewHolder) holder).image1);

                    ((PreviewViewHolder)holder).cv1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                          /*  Intent intent = new Intent(mContext, Fragment2ArticleActivity.class);
                            intent.putExtra("ArticleId",object.getId());
                            mContext.startActivity(intent);*/

                            ArticleFragment article = new ArticleFragment();
                            Bundle bdl=new Bundle();
                            bdl.putLong("ArticleId",object.getId());
                            article.setArguments(bdl);
                            AppCompatActivity activity = (AppCompatActivity) view.getContext();

                            //Create a bundle to pass data, add data, set the bundle to your fragment and:
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_content, article).addToBackStack(null).commit();



                        }
                    });
                    //((PreviewViewHolder) holder).image1.setImageResource(object.image);
                    break;

            }
        }




          /* holder.title.setText(news.get(0).title);
            holder.preview.setText(news.get(0).preveiew);
            //holder.imageCard.setImageResource(news.get(0).image);
            holder.imageCard.setBackgroundResource(news.get(0).getImage());
        if (position>0) {
            holder.title1.setText(news.get(position).title);
            holder.preview1.setText(news.get(position).preveiew);
            //holder.image1.setImageResource(news.get(1).image);
            holder.image1.setImageResource(news.get(position).image);
        }
        else {

        }
         /*   holder.title2.setText(news.get(2).title);
            holder.preview2.setText(news.get(2).preveiew);
            //holder.image2.setImageResource(news.get(2).image);
            holder.image2.setImageResource(news.get(2).image);*/


    }



    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    /*List<NewsPreviewElements> news;

    RecyclerViewAdapter(List<NewsPreviewElements> news){
        this.news = news;
    }*/




/*
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }*/
}
