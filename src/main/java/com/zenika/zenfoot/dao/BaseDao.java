package com.zenika.zenfoot.dao;

import com.zenika.zenfoot.model.AbstractModel;
import java.util.List;

public interface BaseDao<T extends AbstractModel> {

    public List<T> findAll();

    public void save(T model);

    public void delete(T model);

    public T load(long id);
}
