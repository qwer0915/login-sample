package com.framework.rest.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
	
@Mapper
public interface RestLoginMapper {
	Map<String, Object> selectMemberInfo(Map<String, Object> param);
}
