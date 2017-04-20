package com.arakelyan.hovsep.androidserviceusingaidl;
import  com.arakelyan.hovsep.androidserviceusingaidl.FileInfo;

interface IMainService {
    FileInfo[] listFiles();

    void setCountOfClick(int itemId, int newCount);
}
