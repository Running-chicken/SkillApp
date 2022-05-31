package com.cc.skillapp.test.binder;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class MyBinder extends Binder implements MyInterface{

    public static final String DESCRIPTOR = "com.cc.skill.addbinder";
    public static final int ADD = IBinder.FIRST_CALL_TRANSACTION;

    public MyBinder(){
        attachInterface(this,DESCRIPTOR);
    }

    public static MyInterface asInterface(IBinder iBinder){
        IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
        if(null!=iInterface && iInterface instanceof MyInterface){
            return (MyInterface) iInterface;
        }
        return new MyProxy(iBinder);
    }


    @Override
    protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {

        switch (code){
            case ADD:
                int arg0=data.readInt();
                int arg1=data.readInt();
                int result = this.add(arg0,arg1);
                reply.writeInt(result);
                return true;

        }

        return super.onTransact(code, data, reply, flags);
    }



    @Override
    public IBinder asBinder() {
        return this;
    }

}
