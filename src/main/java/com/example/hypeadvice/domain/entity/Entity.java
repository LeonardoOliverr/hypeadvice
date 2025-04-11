package com.example.hypeadvice.domain.entity;

import com.example.hypeadvice.domain.utils.Utils;
import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;
import java.util.Objects;

@MappedSuperclass
public abstract class Entity implements Serializable {

    public abstract Long getId();

    @Override
    public String toString() {
        return Utils.getGson().toJson(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity)) return false;
        Entity that = (Entity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
