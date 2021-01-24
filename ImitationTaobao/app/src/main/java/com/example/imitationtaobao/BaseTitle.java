package com.example.imitationtaobao;

import android.os.Parcel;
import android.os.Parcelable;

public class BaseTitle<T> implements Parcelable {
    private int id;
    private String title;
    private int campaignOne;
    private int campaignTwo;
    private int campaignThree;
    private T cpOne;
    private T cpTwo;
    private T cpThree;

    protected BaseTitle(Parcel in) {
        id = in.readInt();
        title = in.readString();
        campaignOne = in.readInt();
        campaignTwo = in.readInt();
        campaignThree = in.readInt();
    }

    public static final Creator<BaseTitle> CREATOR = new Creator<BaseTitle>() {
        @Override
        public BaseTitle createFromParcel(Parcel in) {
            return new BaseTitle(in);
        }

        @Override
        public BaseTitle[] newArray(int size) {
            return new BaseTitle[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCampaignOne() {
        return campaignOne;
    }

    public void setCampaignOne(int campaignOne) {
        this.campaignOne = campaignOne;
    }

    public int getCampaignTwo() {
        return campaignTwo;
    }

    public void setCampaignTwo(int campaignTwo) {
        this.campaignTwo = campaignTwo;
    }

    public int getCampaignThree() {
        return campaignThree;
    }

    public void setCampaignThree(int campaignThree) {
        this.campaignThree = campaignThree;
    }

    public T getCpOne() {
        return cpOne;
    }

    public void setCpOne(T cpOne) {
        this.cpOne = cpOne;
    }

    public T getCpTwo() {
        return cpTwo;
    }

    public void setCpTwo(T cpTwo) {
        this.cpTwo = cpTwo;
    }

    public T getCpThree() {
        return cpThree;
    }

    public void setCpThree(T cpThree) {
        this.cpThree = cpThree;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
