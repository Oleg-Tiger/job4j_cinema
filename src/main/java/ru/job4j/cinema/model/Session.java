package ru.job4j.cinema.model;

import java.util.Objects;

public class Session {
    private int id;
    private String name;
    private byte[] photo;

    public Session(int id, String name, byte[] photo) {
        this.id = id;
        this.name = name;
        this.photo = photo;
    }

    public Session() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Session session = (Session) o;
        return id == session.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
