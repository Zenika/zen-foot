package com.zenika.zenfoot.dao;

import java.util.List;

public interface BaseDao<T> {
    public T get();

    public List<T> find();

    public T save(T model);
}
