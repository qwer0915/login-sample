package com.framework.login;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {
	Map<String, Object> selectMemberInfo(Map<String, Object> params);
}
