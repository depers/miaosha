package cn.bravedawn.dao;

import cn.bravedawn.entity.Sequence;

public interface SequenceMapper {

    int updateSelective(Sequence record);

    Sequence getSequenceByName(String name);
}