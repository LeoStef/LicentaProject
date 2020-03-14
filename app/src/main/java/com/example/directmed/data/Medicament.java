package com.example.directmed.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Medicament implements Parcelable {
    String name;
    String type;
    double price;
    String farmacie;

    public Medicament(String name, String type, double price,String farmacie) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.farmacie = farmacie;
    }


    protected Medicament(Parcel in) {
        name = in.readString();
        type = in.readString();
        price = in.readDouble();
        farmacie = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(type);
        dest.writeDouble(price);
        dest.writeString(farmacie);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Medicament> CREATOR = new Creator<Medicament>() {
        @Override
        public Medicament createFromParcel(Parcel in) {
            return new Medicament(in);
        }

        @Override
        public Medicament[] newArray(int size) {
            return new Medicament[size];
        }
    };

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public String getFarmacie() {
        return farmacie;
    }

    public void setFarmacie(String farmacie) {
        this.farmacie = farmacie;
    }

    @Override
    public String toString() {
        return "Medicament{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                '}';
    }
}