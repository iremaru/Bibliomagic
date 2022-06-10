package com.biblio.dao;

import java.util.List;

public interface iDAOble<T> {

    public T Get(int id);
    public void Update(int id);
    public void Delete(int id);
}
