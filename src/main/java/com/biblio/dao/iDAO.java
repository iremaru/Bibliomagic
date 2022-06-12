package com.biblio.dao;

import com.biblio.entity.Libros;

import java.util.List;

public interface iDAO<T> {

    public List<T> fetchAll();
    public boolean addData(T data);
    public boolean updateData(T data);
    public boolean deleteData(T data);
}
