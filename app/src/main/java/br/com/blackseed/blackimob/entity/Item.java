package br.com.blackseed.blackimob.entity;

public abstract class Item {

    public static final long DEFAULT_ID = -1;

    private long _id = DEFAULT_ID;

    public long getId() {
        return _id;
    }

    public void setId(long id) {
        _id = id;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer
                .append("\nID: ")
                .append(_id)
                .append("\n");
        return stringBuffer.toString();
    }
}
