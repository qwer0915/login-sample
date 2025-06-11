package com.framework.register;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.register.mapper.RegisterMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RegisterService {
	RegisterMapper registerMapper;

	public RegisterService(RegisterMapper r) {
		this.registerMapper = r;
	}

	@Transactional(readOnly = false)
	public Map<String, Object> requestRegister(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			resultMap.put("REPL_CD", "0000");
			resultMap.put("REPL_MSG", "GOOD");
			resultMap.put("REPL_PAGE_MSG", "REGISTER SERVICE.");
			log.info("CHECK PARAMS {}", params);
			String userId = (String) params.get("userId");
			if (userId == null || userId.isEmpty()) {
				throw new RegisterException("2001", "userId", userId);
			}
			int duplCnt = registerMapper.selectMemberDuplicateCount(params);
			if (duplCnt > 0) {
				throw new RegisterException("2002", "duplCnt", "" + duplCnt);
			}
			if (registerMapper.insertMember(params) < 0) {
				throw new RegisterException("2003", "insertMember", "insertMember");
			}
			Map<String, Object> memberInfo = registerMapper.selectMemberInfo(params);
			resultMap.put("MEMBER_INFO", memberInfo);
		} catch (RegisterException rex) {
			resultMap.put("REPL_CD", rex.getReplCd());
			resultMap.put("REPL_MSG", rex.getReplMsg());
			resultMap.put("REPL_PAGE_MSG", rex.getReplPageMsg());
		} catch (Exception ex) {
			resultMap.put("REPL_CD", "9999");
			resultMap.put("REPL_MSG", ex);
			resultMap.put("REPL_PAGE_MSG", "에러가 발생했습니다.");
		}
		return resultMap;
	}

	@Transactional(readOnly = true)
	public Map<String, Object> getMember(String userId) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			resultMap.put("REPL_CD", "0000");
			resultMap.put("REPL_MSG", "GOOD");
			resultMap.put("REPL_PAGE_MSG", "REGISTER SERVICE.");

			Map<String, Object> paramMap = new HashMap<String,Object>();
			paramMap.put("userId", userId);
			Map<String, Object> memberInfo = registerMapper.selectMemberInfo(paramMap);
			resultMap.put("MEMBER_INFO", memberInfo);

		} catch (RegisterException rex) {
			resultMap.put("REPL_CD", rex.getReplCd());
			resultMap.put("REPL_MSG", rex.getReplMsg());
			resultMap.put("REPL_PAGE_MSG", rex.getReplPageMsg());
		} catch (Exception ex) {
			resultMap.put("REPL_CD", "9999");
			resultMap.put("REPL_MSG", ex);
			resultMap.put("REPL_PAGE_MSG", "에러가 발생했습니다.");
		}
		return resultMap;
	}

	@Transactional(readOnly = false)
	public Map<String, Object> requestModify(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			resultMap.put("REPL_CD", "0000");
			resultMap.put("REPL_MSG", "GOOD");
			resultMap.put("REPL_PAGE_MSG", "REGISTER SERVICE.");
			log.info("CHECK PARAMS {}", params);

			if (registerMapper.updateMember(params) < 0) {
				throw new RegisterException("2003", "updateMember", "updateMember");
			}

			// Check
			Map<String, Object> memberInfo = registerMapper.selectMemberInfo(params);
			resultMap.put("MEMBER_INFO", memberInfo);
		} catch (RegisterException rex) {
			resultMap.put("REPL_CD", rex.getReplCd());
			resultMap.put("REPL_MSG", rex.getReplMsg());
			resultMap.put("REPL_PAGE_MSG", rex.getReplPageMsg());
		} catch (Exception ex) {
			resultMap.put("REPL_CD", "9999");
			resultMap.put("REPL_MSG", ex);
			resultMap.put("REPL_PAGE_MSG", "에러가 발생했습니다.");
		}
		return resultMap;
	}

	@Transactional(readOnly = false)
	public Map<String, Object> requestRemove(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			resultMap.put("REPL_CD", "0000");
			resultMap.put("REPL_MSG", "GOOD");
			resultMap.put("REPL_PAGE_MSG", "REGISTER SERVICE.");
			log.info("CHECK PARAMS {}", params);
			// 회원가입 처리
			if (registerMapper.removeMember(params) < 0) {
				throw new RegisterException("2003", "registerMapper", " Happe");
			}
			// -원래 불필요- 정상 등록 확인하기 위해 db 조회 설정
			Map<String, Object> memberInfo = registerMapper.selectMemberInfo(params);
			resultMap.put("MEMBER_INFO", memberInfo);
		} catch (RegisterException rex) {
			resultMap.put("REPL_CD", rex.getReplCd());
			resultMap.put("REPL_MSG", rex.getReplMsg());
			resultMap.put("REPL_PAGE_MSG", rex.getReplPageMsg());
		} catch (Exception ex) {
			resultMap.put("REPL_CD", "9999");
			resultMap.put("REPL_MSG", ex);
			resultMap.put("REPL_PAGE_MSG", "에러가 발생했습니다.");
		}
		return resultMap;
	}
	
	@Transactional(readOnly = true)
	public Map<String, Object> requestMemberList(UserForm userForm){
		Map<String, Object> resultMap = new HashMap<>();
		try {
			resultMap.put("REPL_CD", "0000");
			resultMap.put("REPL_MSG", "GOOD");
			resultMap.put("REPL_PAGE_MSG", "REGISTER SERVICE.");
			// 페이지 수와 검색어 확인
			Integer pageNum =userForm.getPageNum() != null && userForm.getPageNum() > 0 ? userForm.getPageNum() : 1;
			String searchUserName =userForm.getSearchUserName() != null ? userForm.getSearchUserName().trim() : "";
			// 페이지 설정
			int pageSize =5;
			int offset =(pageNum-1)*pageSize;
			// 리스트 집어넣기
			Map<String, Object> paramsMap = new HashMap<>();
			paramsMap.put("pageNum", pageNum);
			paramsMap.put("searchUserName", searchUserName);
			paramsMap.put("pageSize", pageSize);
			paramsMap.put("offset", offset);
			log.info("검색어: {}", searchUserName); 
			// 총합 확인하기
			int totalCount = registerMapper.selectUserTotalCount(paramsMap);
			resultMap.put("TOTAL_COUNT", totalCount);
			// 목록 조회
			List<Map<String, Object>> user_List =registerMapper.selectUserList(paramsMap);
			resultMap.put("USER_LIST", user_List);
			
			log.info("",user_List);
			// page 지정하기
			int firstPageNum =1;
			int lastPageNum=(int) Math.ceil((double) totalCount / pageSize);
			int startBlockPage =1;
			int endBlockPage=lastPageNum;
			
			List<Integer> pageBlockList =new ArrayList<>();
			for(int i=startBlockPage;i<endBlockPage;i++) {
				pageBlockList.add(i);
			}
			Map<String, Object> pagingMap = new HashMap<>();
			pagingMap.put("PAGE_BLOCK_LIST", pageBlockList);
			pagingMap.put("FIRST_PAGE_NUM", firstPageNum);
			pagingMap.put("LAST_PAGE_NUM", lastPageNum);
			pagingMap.put("PAGE_BLOCK_LIST", pageBlockList);
			pagingMap.put("PAGE_NUM", pageNum); 
			pagingMap.put("PAGE_SIZE", pageSize);
			pagingMap.put("PAGE_OFFSET", offset);

			resultMap.put("pagingMap", pagingMap);
			resultMap.put("paramsMap", paramsMap);
			log.info("paramsMap: {}", paramsMap);
			log.info("resultMap: {}", resultMap);
			
		} catch (RegisterException rex) {
			resultMap.put("REPL_CD", rex.getReplCd());
			resultMap.put("REPL_MSG", rex.getReplMsg());
			resultMap.put("REPL_PAGE_MSG", rex.getReplPageMsg());
		} catch (Exception ex) {
			resultMap.put("REPL_CD", "9999");
			resultMap.put("REPL_MSG", ex);
			resultMap.put("REPL_PAGE_MSG", "에러가 발생했습니다.");
		}
		return resultMap;
	}
}
