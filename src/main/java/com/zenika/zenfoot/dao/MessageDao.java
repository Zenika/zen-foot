package com.zenika.zenfoot.dao;

import com.zenika.zenfoot.model.Message;
import java.util.List;

public interface MessageDao extends BaseDao<Message> {

    public List<Message> findLast30();

    public Message findLastOne();
}
