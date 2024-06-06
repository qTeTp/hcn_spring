package com.hcn.hcn.model;

import jakarta.persistence.*;

@Entity
@Table(name = "header")
public class ChatHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String from_id;
    private String to_id;
    private String last_message;
    private String last_id;
    private String time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromId() {
        return from_id;
    }

    public void setFromId(String fromId) {
        this.from_id = fromId;
    }

    public String getToId() {
        return to_id;
    }

    public void setToId(String toId) {
        this.to_id = toId;
    }

    public String getLastMessage() {
        return last_message;
    }

    public void setLastMessage(String lastMessage) {
        this.last_message = lastMessage;
    }

    public String getLastId() {
        return last_id;
    }

    public void setLastId(String lastId) {
        this.last_id = lastId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
