package cn.bravedawn.dao;

import cn.bravedawn.entity.Promo;

public interface PromoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Promo record);

    int insertSelective(Promo record);

    Promo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Promo record);

    int updateByPrimaryKey(Promo record);

    Promo selectByItemId(Integer itemId);
}