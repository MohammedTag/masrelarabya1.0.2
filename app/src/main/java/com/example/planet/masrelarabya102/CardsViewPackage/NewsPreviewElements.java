package com.example.planet.masrelarabya102.CardsViewPackage;

/**
 * Created by Planet on 1/16/2018.
 */

public class NewsPreviewElements {

    public static final int HEADER_TYPE=1;
    public static final int SUBITEM_TYPE=2;


    String imgurl;
    String title;
    String preveiew;
    String date;
    long id;

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPreveiew() {
        return preveiew;
    }

    public void setPreveiew(String preveiew) {
        this.preveiew = preveiew;
    }

    public String getImage() {
        return date;
    }

    public void setImage(String date) {
        this.date = date;
    }
    int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    //no preview for the constructor and might be added(should be added)
    public NewsPreviewElements(int type, String title,String preveiew, String imgurl,long id,String date) {

        this.type = type;
        this.title = title;
        this.preveiew = preveiew;
        this.imgurl=imgurl;
        this.date = date;
        this.id =id;
    }

    /*private void initializeData(){
        ArrayList newsMock = new ArrayList<>();
        newsMock.add(new NewsPreviewElements("Emma Wilson", "23 years old", R.drawable.economy));
        newsMock.add(new NewsPreviewElements("Lavery Maiss", "25 years old", R.drawable.sports));
        newsMock.add(new NewsPreviewElements("Lillie Watts", "35 years old", R.drawable.news));
    }*/


}
