package com.arakelyan.hovsep.androidserviceusingaidl;

import android.os.Parcel;
import android.os.Parcelable;

public class FileInfo implements Parcelable {

    private int id;
    private String name;
    private String path;
    private String clickedCount;
    private String createdDate;


    public FileInfo(int id, String name, String path, String clickedCount, String createdDate) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.createdDate = createdDate;
        this.clickedCount = clickedCount;
    }

    public FileInfo(String name, String path, String clickedCount, String createdDate) {
        this.name = name;
        this.createdDate = createdDate;
        this.path = path;
        this.clickedCount = clickedCount;
    }


    // Getter and setter methods


    // Parcelling part
    public FileInfo(Parcel in){
        String[] data = new String[5];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.id = Integer.parseInt(data[0]);
        this.name = data[1];
        this.path = data[2];
        this.createdDate = data[3];
        this.clickedCount = data[4];
    }


    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                String.valueOf(this.id),
                this.name,
                this.path,
                this.createdDate,
                this.clickedCount,

        });
    }


    public static final Creator<FileInfo> CREATOR
            = new Creator<FileInfo>() {
        public FileInfo createFromParcel(Parcel in) {
            return new FileInfo(in);
        }

        public FileInfo[] newArray(int size) {
            return new FileInfo[size];
        }
    };





    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClickedCount() {
        return clickedCount;
    }

    public void setClickedCount(String clickedCount) {
        this.clickedCount = clickedCount;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}