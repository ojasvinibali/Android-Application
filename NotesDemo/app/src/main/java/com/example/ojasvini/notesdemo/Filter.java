package com.example.ojasvini.notesdemo;

/**
 * Created by Ojasvini on 3/17/2017.
 */

public class Filter {
    private long _id;

    @Override
    public String toString() {
        return "Filter{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", price=" + price +
                '}';
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public Filter(String name, String url, double price) {

        this.name = name;
        this.url = url;
        this.price = price;
    }

    private String name , url;


    public Filter(){}

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    private double price;
}
