package com.jp.carpool.Data;

/**
 * Created by dkhairnar on 2/3/2017.
 */

public class postData {

    private String userId;
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

    public void postData(){

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

}
