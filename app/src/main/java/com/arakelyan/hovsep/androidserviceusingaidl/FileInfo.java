package com.arakelyan.hovsep.androidserviceusingaidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Aidan Follestad (afollestad)
 */
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


    private FileInfo(Parcel in) {
        id= in.readInt();
        name = in.readString();
        createdDate = in.readString();
        clickedCount = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(name);
        out.writeString(createdDate);
        out.writeString(clickedCount);
    }

    public static final Parcelable.Creator<FileInfo> CREATOR
            = new Parcelable.Creator<FileInfo>() {
        public FileInfo createFromParcel(Parcel in) {
            return new FileInfo(in);
        }

        public FileInfo[] newArray(int size) {
            return new FileInfo[size];
        }
    };



    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                "createdDate='" + createdDate + '\'' +
                ", clickedCount=" + clickedCount +
                '}';
    }




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
