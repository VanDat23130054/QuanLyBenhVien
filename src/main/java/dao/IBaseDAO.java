package dao;

import java.util.List;

public interface IBaseDAO<T> {
    T getById(int id);
    List<T> getAll();
    boolean insert(T obj);
    boolean update(T obj);
    boolean delete(int id);
}
