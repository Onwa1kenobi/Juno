package io.julius.juno.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Memory implements Parcelable {

    public static final Parcelable.Creator<Memory> CREATOR = new Parcelable.Creator<Memory>() {
        public Memory createFromParcel(Parcel source) {
            return new Memory(source);
        }

        public Memory[] newArray(int size) {
            return new Memory[size];
        }
    };

    private String title;
    private String description;
    private String id;
    @ServerTimestamp
    private Date createdAt;

    public Memory() {
    }

    public Memory(String title, String description, Date createdAt) {
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
    }

    public Memory(Parcel in) {
        title = in.readString();
        description = in.readString();
        id = in.readString();
        createdAt = new Date(in.readLong());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(id);
        dest.writeLong(createdAt.getTime());
    }
}
