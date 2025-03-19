package com.solovev.dto;

/**
 * Interface for working with DB entities to get and set id
 */
public interface DaoEntity {
    long getId();

    void setId(long id);
}
