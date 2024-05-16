package cn.bravedawn.dao;

import cn.bravedawn.entity.Item;
import org.apache.ibatis.annotations.Param;

public interface ItemMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Item record);

    int insertSelective(Item record);

    Item selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Item record);

    int updateByPrimaryKey(Item record);

    int decreaseStock(@Param("itemId") Integer itemId, @Param("amount") Integer amount);

    int increaseSale(@Param("itemId") Integer itemId, @Param("amount") Integer amount);
}