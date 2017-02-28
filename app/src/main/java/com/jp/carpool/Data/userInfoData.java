package com.jp.carpool.Data;

/**
 * Created by dkhairnar on 27/2/2017.
 */

public class userInfoData {


    private String email;
    private String password;
    private String FullName;
    private String MoNo;
    private String ITSID;
    private String Password;
    private String LicenceNo;
    private String CarName;
    private String CarNo;
    private String NumberSeat;

    public userInfoData() {
    }
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
}