package com.cc.skillapp.entity;

import android.content.Intent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

public class ActivityManagerProxy implements IActivityManager{

    IBinder iBinder;

    public ActivityManagerProxy(IBinder iBinder){
        this.iBinder = iBinder;
    }

    @Override
    public int startActivity(Intent intent) {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        int result = 0;
        data.writeInterfaceToken(DESCRIPTOR);
        intent.writeToParcel(data,0);
        try {
            iBinder.transact(666,data,reply,0);
            result = reply.readInt();
        } catch (RemoteException e) {
            e.printStackTrace();
        }finally {
            data.recycle();
            reply.recycle();
        }

        return result;
    }

    @Override
    public IBinder asBinder() {
        return iBinder;
    }
}
