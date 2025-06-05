package com.framework.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.rest.RestLoginException;
import com.framework.rest.mapper.RestLoginMapper;
import com.framework.util.EncryptUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RestLoginService {
	RestLoginMapper mapper;
	EncryptUtil encryptUtil;
	public RestLoginService(RestLoginMapper mapper,EncryptUtil encryptUtil) {
		this.mapper=mapper;
		this.encryptUtil=encryptUtil;
	}
	
	@Transactional
	public Map<String,Object> requestLogin(Map<String,Object> param){
		Map<String,Object> result = new HashMap<>();
		try {
			result.put("REPL_CD", "0000");
			result.put("REPL_MSG", "GOOD");
			result.put("REPL_PAGE_MSG", "LOGIN.");
			// Controller 에서 값 받기
			String userId = (String) param.get("userId");
			String userPw = (String) param.get("userPw");
			// Null 확인
			if (userId ==null || userId.isEmpty()) {throw new RestLoginException("1001","NULL ID","ENTER ID");}
			if (userPw ==null || userPw.isEmpty()) {throw new RestLoginException("1002","NULL PW","ENTER PW");}
			// Mapper 에서 조회후 NULL 체크
			Map<String,Object> info = mapper.selectMemberInfo(param);
			if (info ==null) {throw new RestLoginException("1003", "No Data", "i dont have your data");}
			// Password 확인
			String dbPw =(String) info.get("PW");
			String encPw =(String) encryptUtil.encryptSha256(userPw);
			if(!dbPw.equals(encPw)) {throw new RestLoginException("1004", "Wrong Data", "wrong your pw");}
			//결과 전달
			result.put("memberinfo", info);
		}catch  (RestLoginException rex) {
			result.put("REPL_CD", rex.getReplCd());
			result.put("REPL_MSG", rex.getReplMsg());
			result.put("REPL_PAGE_MSG", rex.getReplPageMsg());
		}
		return result;
	}
}
