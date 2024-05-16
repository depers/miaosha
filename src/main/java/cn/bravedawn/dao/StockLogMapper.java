package cn.bravedawn.dao;

import cn.bravedawn.entity.StockLog;

public interface StockLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StockLog record);

    int insertSelective(StockLog record);

    StockLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StockLog record);

    int updateByPrimaryKey(StockLog record);
}