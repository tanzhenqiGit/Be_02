/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\Android\\workspace\\MusicPlayer\\src\\com\\be02\\aidl\\IMusicListener.aidl
 */
package com.be02.aidl;
public interface IMusicListener extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.be02.aidl.IMusicListener
{
private static final java.lang.String DESCRIPTOR = "com.be02.aidl.IMusicListener";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.be02.aidl.IMusicListener interface,
 * generating a proxy if needed.
 */
public static com.be02.aidl.IMusicListener asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.be02.aidl.IMusicListener))) {
return ((com.be02.aidl.IMusicListener)iin);
}
return new com.be02.aidl.IMusicListener.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_onTimeChanged:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.onTimeChanged(_arg0);
return true;
}
case TRANSACTION_onSongChanged:
{
data.enforceInterface(DESCRIPTOR);
com.be02.aidl.MusicItem _arg0;
_arg0 = new com.be02.aidl.MusicItem();
this.onSongChanged(_arg0);
if ((_arg0!=null)) {
reply.writeInt(1);
_arg0.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.be02.aidl.IMusicListener
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void onTimeChanged(int time) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(time);
mRemote.transact(Stub.TRANSACTION_onTimeChanged, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void onSongChanged(com.be02.aidl.MusicItem item) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_onSongChanged, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
}
static final int TRANSACTION_onTimeChanged = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_onSongChanged = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
public void onTimeChanged(int time) throws android.os.RemoteException;
public void onSongChanged(com.be02.aidl.MusicItem item) throws android.os.RemoteException;
}
