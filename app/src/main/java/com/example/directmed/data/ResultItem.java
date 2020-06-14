package com.example.directmed.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ResultItem implements Parcelable {

    private String address;
    private String lat;
    private String lng;
    private String distance;

    public ResultItem(String address, String lat, String lng) {
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        distance = "0 km";
    }

    public ResultItem(String address, String lat, String lng, String distance) {
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.distance = distance;
    }


    protected ResultItem(Parcel in) {
        address = in.readString();
        lat = in.readString();
        lng = in.readString();
        distance = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeString(lat);
        dest.writeString(lng);
        dest.writeString(distance);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ResultItem> CREATOR = new Creator<ResultItem>() {
        @Override
        public ResultItem createFromParcel(Parcel in) {
            return new ResultItem(in);
        }

        @Override
        public ResultItem[] newArray(int size) {
            return new ResultItem[size];
        }
    };

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }




    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "ResultItem{" +
                "address='" + address + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", distance='" + distance +'\'' +
                "'}";
    }


}
