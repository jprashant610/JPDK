package com.jp.carpool.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dkhairnar on 2/3/2017.
 */

public class postData implements Parcelable {

    private String userId;
    private String fullName;
    private String MoNo;
    //private String LicenceNo;
    private String CarName;
    private String CarNo;
    private String detail;
    private String NumberSeat;
    private String From;
    private String To;
    private String Date;
    private String Time;
    private String PostId;

    private postData(Parcel in) {
        userId = in.readString();
        fullName = in.readString();
        MoNo = in.readString();
        CarName = in.readString();
        CarNo = in.readString();
        detail = in.readString();
        NumberSeat = in.readString();
        From = in.readString();
        To = in.readString();
        Date = in.readString();
        Time = in.readString();
        PostId = in.readString();
    }

    public static final Creator<postData> CREATOR = new Creator<postData>() {
        @Override
        public postData createFromParcel(Parcel in) {
            return new postData(in);
        }

        @Override
        public postData[] newArray(int size) {
            return new postData[size];
        }
    };

    public postData(){

    }
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMoNo() {
        return MoNo;
    }

    public void setMoNo(String moNo) {
        MoNo = moNo;
    }

    public String getCarName() {
        return CarName;
    }

    public void setCarName(String carName) {
        CarName = carName;
    }

    public String getCarNo() {
        return CarNo;
    }

    public void setCarNo(String carNo) {
        CarNo = carNo;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getNumberSeat() {
        return NumberSeat;
    }

    public void setNumberSeat(String numberSeat) {

        NumberSeat = numberSeat;
    }

    public String getPostId() {
        return PostId;
    }

    public void setPostId(String postId) {
        PostId = postId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.userId);
        parcel.writeString(this.fullName);
        parcel.writeString(this.MoNo);
        //private String LicenceNo;
        parcel.writeString(this.CarName);
        parcel.writeString(this.CarNo);
        parcel.writeString(this.detail);
        parcel.writeString(this.NumberSeat);
        parcel.writeString(this.From);
        parcel.writeString(this.To);
        parcel.writeString(this.Date);
        parcel.writeString(this.Time);
        parcel.writeString(this.PostId);
    }
}
