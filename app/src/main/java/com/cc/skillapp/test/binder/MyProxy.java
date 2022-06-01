package com.cc.skillapp.test.binder;

import static com.cc.skillapp.test.binder.MyBinder.ADD;
import static com.cc.skillapp.test.binder.MyBinder.DESCRIPTOR;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import com.cc.library.base.util.Utils;

public class MyProxy implements MyInterface{
    private IBinder iBinder;

    public MyProxy(IBinder iBinder){
        this.iBinder = iBinder;
    }

    @Override
    public int add(int arg0, int arg1) {

        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        int result = 0;

        data.writeInt(1);
        data.writeInt(2);
        data.writeInterfaceToken(DESCRIPTOR);

        try {
            iBinder.transact(ADD,data,reply,0);

            reply.readException();
            result = reply.readInt();
            Utils.log("输出结果是："+result);
        } catch (RemoteException e) {
            e.printStackTrace();
        } finally {
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
