package com.kck.suljido.mapper;

import com.kck.suljido.model.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {
    List<Member> selectAll();
    Member selectById(@Param("id")Long id);
}
