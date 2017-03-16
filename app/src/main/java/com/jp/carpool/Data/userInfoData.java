package com.jp.carpool.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dkhairnar on 27/2/2017.
 */

public class userInfoData implements Parcelable {


    private String email;
    private String password;
    private String FullName;
    private String MoNo;
    private String ITSID;
    private String LicenceNo;
    private String CarName;
    private String CarNo;
    private String NumberSeat;
    private boolean IsCar;

    public userInfoData(Parcel in) {
        email= in.readString();
        password= in.readString();
        FullName= in.readString();
        MoNo= in.readString();
        ITSID= in.readString();
        LicenceNo= in.readString();
        CarName= in.readString();
        CarNo= in.readString();
        NumberSeat= in.readString();
        IsCar = in.readInt()==1;
    }

    public static final Creator<userInfoData> CREATOR = new Creator<userInfoData>() {
        @Override
        public userInfoData createFromParcel(Parcel in) {
            return new userInfoData(in);
        }

        @Override
        public userInfoData[] newArray(int size) {
            return new userInfoData[size];
        }
    };

    public userInfoData() {

    }

    public boolean getIsCar() { return IsCar; }

    public void setIsCar(boolean car) {IsCar = car; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getMoNo() {
        return MoNo;
    }

    public void setMoNo(String moNo) {
        MoNo = moNo;
    }

    public String getITSID() {
        return ITSID;
    }

    public void setITSID(String ITSID) {
        this.ITSID = ITSID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLicenceNo() {
        return LicenceNo;
    }

    public void setLicenceNo(String licenceNo) {
        LicenceNo = licenceNo;
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

    public String getNumberSeat() {
        return NumberSeat;
    }

    public void setNumberSeat(String numberSeat) {
        NumberSeat = numberSeat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {

        parcel.writeString(this.email);
        parcel.writeString(this.password);
        parcel.writeString(this.FullName);
        parcel.writeString(this.MoNo);
        parcel.writeString(this.ITSID);
        parcel.writeString(this.LicenceNo);
        parcel.writeString(this.CarName);
        parcel.writeString(this.CarNo);
        parcel.writeString(this.NumberSeat);
        parcel.writeInt(this.IsCar?1:0);
    }
}