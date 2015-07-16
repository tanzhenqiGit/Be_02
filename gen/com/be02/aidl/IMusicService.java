/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\Android\\workspace\\MusicPlayer\\src\\com\\be02\\aidl\\IMusicService.aidl
 */
package com.be02.aidl;
public interface IMusicService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.be02.aidl.IMusicService
{
private static final java.lang.String DESCRIPTOR = "com.be02.aidl.IMusicService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.be02.aidl.IMusicService interface,
 * generating a proxy if needed.
 */
public static com.be02.aidl.IMusicService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.be02.aidl.IMusicService))) {
return ((com.be02.aidl.IMusicService)iin);
}
return new com.be02.aidl.IMusicService.Stub.Proxy(obj);
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
case TRANSACTION_play:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.play();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_pause:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.pause();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_next:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.next();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_previous:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.previous();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_seek:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.seek(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setCurMusicList:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<com.be02.aidl.MusicItem> _arg0;
_arg0 = new java.util.ArrayList<com.be02.aidl.MusicItem>();
int _result = this.setCurMusicList(_arg0);
reply.writeNoException();
reply.writeInt(_result);
reply.writeTypedList(_arg0);
return true;
}
case TRANSACTION_setCurPlayIndex:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.setCurPlayIndex(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setFavoriteStsChange:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.setFavoriteStsChange();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_registerListener:
{
data.enforceInterface(DESCRIPTOR);
com.be02.aidl.IMusicListener _arg0;
_arg0 = com.be02.aidl.IMusicListener.Stub.asInterface(data.readStrongBinder());
int _result = this.registerListener(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_removeListener:
{
data.enforceInterface(DESCRIPTOR);
com.be02.aidl.IMusicListener _arg0;
_arg0 = com.be02.aidl.IMusicListener.Stub.asInterface(data.readStrongBinder());
int _result = this.removeListener(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getCurPlayMode:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getCurPlayMode();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getCurPlayStatus:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getCurPlayStatus();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_addLocalList:
{
data.enforceInterface(DESCRIPTOR);
com.be02.aidl.MusicListItem _arg0;
_arg0 = new com.be02.aidl.MusicListItem();
int _result = this.addLocalList(_arg0);
reply.writeNoException();
reply.writeInt(_result);
if ((_arg0!=null)) {
reply.writeInt(1);
_arg0.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_deleteLocalList:
{
data.enforceInterface(DESCRIPTOR);
com.be02.aidl.MusicListItem _arg0;
_arg0 = new com.be02.aidl.MusicListItem();
int _result = this.deleteLocalList(_arg0);
reply.writeNoException();
reply.writeInt(_result);
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
private static class Proxy implements com.be02.aidl.IMusicService
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
@Override public int play() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_play, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int pause() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_pause, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int next() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_next, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int previous() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_previous, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int seek(int position) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(position);
mRemote.transact(Stub.TRANSACTION_seek, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int setCurMusicList(java.util.List<com.be02.aidl.MusicItem> list) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_setCurMusicList, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
_reply.readTypedList(list, com.be02.aidl.MusicItem.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int setCurPlayIndex(int index) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(index);
mRemote.transact(Stub.TRANSACTION_setCurPlayIndex, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int setFavoriteStsChange() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_setFavoriteStsChange, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int registerListener(com.be02.aidl.IMusicListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registerListener, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int removeListener(com.be02.aidl.IMusicListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_removeListener, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getCurPlayMode() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getCurPlayMode, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getCurPlayStatus() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getCurPlayStatus, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int addLocalList(com.be02.aidl.MusicListItem item) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_addLocalList, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
if ((0!=_reply.readInt())) {
item.readFromParcel(_reply);
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int deleteLocalList(com.be02.aidl.MusicListItem item) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_deleteLocalList, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
if ((0!=_reply.readInt())) {
item.readFromParcel(_reply);
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_play = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_pause = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_next = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_previous = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_seek = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_setCurMusicList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_setCurPlayIndex = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_setFavoriteStsChange = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_registerListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_removeListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_getCurPlayMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_getCurPlayStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_addLocalList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_deleteLocalList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
}
public int play() throws android.os.RemoteException;
public int pause() throws android.os.RemoteException;
public int next() throws android.os.RemoteException;
public int previous() throws android.os.RemoteException;
public int seek(int position) throws android.os.RemoteException;
public int setCurMusicList(java.util.List<com.be02.aidl.MusicItem> list) throws android.os.RemoteException;
public int setCurPlayIndex(int index) throws android.os.RemoteException;
public int setFavoriteStsChange() throws android.os.RemoteException;
public int registerListener(com.be02.aidl.IMusicListener listener) throws android.os.RemoteException;
public int removeListener(com.be02.aidl.IMusicListener listener) throws android.os.RemoteException;
public int getCurPlayMode() throws android.os.RemoteException;
public int getCurPlayStatus() throws android.os.RemoteException;
public int addLocalList(com.be02.aidl.MusicListItem item) throws android.os.RemoteException;
public int deleteLocalList(com.be02.aidl.MusicListItem item) throws android.os.RemoteException;
}
