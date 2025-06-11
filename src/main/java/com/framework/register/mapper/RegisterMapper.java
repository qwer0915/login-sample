package com.framework.register.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegisterMapper {
	int selectMemberDuplicateCount(Map<String, Object> params);

	int insertMember(Map<String, Object> params);

	Map<String, Object> selectMemberInfo(Map<String, Object> params);

	int updateMember(Map<String, Object> params);

	int removeMember(Map<String, Object> params);
	
	// ADD 목록 전체 갯수 조회
	int selectUserTotalCount(Map<String, Object> params);
	
	List<Map<String, Object>> selectUserList(Map<String, Object> params);
}
