package com.my.myshop.bean;

/**
 * Created by Ivan on 15/10/2.
 *图片轮播的bean
 */
public class Banner extends BaseBean {

    private  String name;
    private  String imgUrl;
    private  String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
