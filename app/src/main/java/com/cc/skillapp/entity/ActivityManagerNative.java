package com.cc.skillapp.entity;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class ActivityManagerNative extends Binder implements IActivityManager {

    public ActivityManagerNative(){
        attachInterface(this,DESCRIPTOR);
    }

    public static IActivityManager asInterface(IBinder iBinder){
        IActivityManager iInterface = (IActivityManager) iBinder.queryLocalInterface(DESCRIPTOR);
        if(iInterface!=null ){
            return iInterface;
        }

        return new ActivityManagerProxy(iBinder);
    }


    @Override
    public IBinder asBinder() {
        return this;
    }

    @Override
    protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
        switch (code){
            case INTERFACE_TRANSACTION:
                reply.writeString(DESCRIPTOR);
                return true;
            case 6:
                data.enforceInterface(DESCRIPTOR);
                Intent intent = Intent.CREATOR.createFromParcel(data);
                int result = this.startActivity(intent);
//                reply.writeNoException();
                reply.writeInt(result);
                return true;
        }


        return super.onTransact(code, data, reply, flags);
    }
}
