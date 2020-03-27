package com.example.directmed.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Medicament implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "type")
    private String type;
    @ColumnInfo(name = "price")
    private double price;
    @ColumnInfo(name = "pharmacy")
    private String pharmacy;
    @ColumnInfo(name = "imageURL")
    private String imageURL;

    public Medicament(String name, String type, double price, String pharmacy, String imageURL) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.pharmacy = pharmacy;
        this.imageURL = imageURL;
    }


    protected Medicament(Parcel in) {
        name = in.readString();
        type = in.readString();
        price = in.readDouble();
        pharmacy = in.readString();
        imageURL = in.readString();
    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(type);
        dest.writeDouble(price);
        dest.writeString(pharmacy);
        dest.writeString(imageURL);
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

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getType() {return type;}

    public void setType(String type) {this.type = type;}

    public double getPrice() {return price;}

    public void setPrice(double price) {this.price = price;}

    public String getPharmacy() {return pharmacy;}

    public void setPharmacy(String pharmacy) {this.pharmacy = pharmacy;}

    public String getImageURL() {return imageURL;}

    public void setImageURL(String imageURL) {this.imageURL = imageURL;}



    @Override
    public String toString() {
        return "Medicament{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                '}';
    }
}