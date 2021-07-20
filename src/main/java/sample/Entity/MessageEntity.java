package sample.Entity;

import java.util.Date;
import java.util.Objects;

public class MessageEntity {
    private int id;
    private int fromUserId;
    private int toRoomId;
    private String text;
    private Date date;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }


    public int getToRoomId() {
        return toRoomId;
    }

    public void setToRoomId(int toRoomId) {
        this.toRoomId = toRoomId;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageEntity that = (MessageEntity) o;
        return id == that.id && fromUserId == that.fromUserId && toRoomId == that.toRoomId && text == that.text && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fromUserId, toRoomId, text, date);
    }
}
