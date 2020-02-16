package com.example.khoslalabs;

public class ShowData {

    int tempM6,tempN12,tempE6;
    String day;
    ShowData(String d,int m,int n, int e){
        day = d;
        tempM6 =m;
        tempN12 =n;
        tempE6 = e;
    }

    ShowData(){
    }

    public int getTempE6() {
        return tempE6;
    }

    public int getTempN12() {
        return tempN12;
    }

    public int getTempM6() {
        return tempM6;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setTempM6(int tempM6) {
        this.tempM6 = tempM6;
    }

    public void setTempN12(int tempN12) {
        this.tempN12 = tempN12;
    }

    public void setTempE6(int tempE6) {
        this.tempE6 = tempE6;
    }
}
