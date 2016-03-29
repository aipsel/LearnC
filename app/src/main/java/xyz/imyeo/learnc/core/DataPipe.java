package xyz.imyeo.learnc.core;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

import java.util.HashMap;

public class DataPipe implements Parcelable {

    public static final String ARG_KEY = "DataPipe.ARGKey";

    private static SparseArray<HashMap<String, Object>> sDataPool = new SparseArray<>();
    private static final Object sLock = new Object();
    private static int sId = 0;

    public static final Creator<DataPipe> CREATOR = new Creator<DataPipe>() {
        @Override
        public DataPipe createFromParcel(Parcel source) {
            return new DataPipe(source);
        }

        @Override
        public DataPipe[] newArray(int size) {
            return new DataPipe[size];
        }
    };

    private HashMap<String, Object> mData = new HashMap<>();

    private DataPipe(Parcel source) {
        int id = source.readInt();
        mData = sDataPool.get(id);
        sDataPool.remove(id);
    }

    public DataPipe() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        int id;
        synchronized (sLock) {
            id = sId++;
            sDataPool.put(id, mData);
        }
        dest.writeLong(id);
    }

    public DataPipe add(String key, Object value) {
        mData.put(key, value);
        return this;
    }

    public Object get(String key) {
        return mData.get(key);
    }
}
