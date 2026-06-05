package com.hospital.dao;

import java.util.List;

/**
 * Base DAO interface defining common CRUD operations
 * All DAO implementations should extend this interface
 */
public interface IBaseDAO<T> {
    /**
     * Get an entity by its ID
     */
    T getById(int id);

    /**
     * Get all entities
     */
    List<T> getAll();

    /**
     * Add/Create a new entity
     */
    boolean add(T entity);

    /**
     * Update an existing entity
     */
    boolean update(T entity);

    /**
     * Delete an entity by its ID
     */
    boolean delete(int id);
}
