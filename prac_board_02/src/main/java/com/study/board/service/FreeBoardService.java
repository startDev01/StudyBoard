package com.study.board.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.board.dao.FreeBoardDAO;
import com.study.board.vo.FreeBoardVO;

@Service
public class FreeBoardService {

    private final FreeBoardDAO freeBoardDAO;
    private final SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public FreeBoardService(FreeBoardDAO freeBoardDAO, SqlSessionTemplate sqlSessionTemplate) {
        this.freeBoardDAO = freeBoardDAO;
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public void updateViewCnt(int bNo) throws Exception {
        sqlSessionTemplate.update("com.study.board.dao.FreeBoardDAO.updateViewCnt", bNo);
    }
  
    public List<FreeBoardVO> getBoardList() throws Exception {
        List<FreeBoardVO> boardList = freeBoardDAO.getBoardList();
        for (FreeBoardVO board : boardList) {
            if ("Y".equals(board.getbNoticeYn())) {
                board.setbCategory("공지");
            }
        }
        return boardList;
    }
    
    public FreeBoardVO getNotice(int bNo) throws Exception {
        FreeBoardVO board = freeBoardDAO.getBoard(bNo);
        if (board != null && "Y".equals(board.getbNoticeYn())) {
            board.setbCategory("공지");
        }
        return board;
    }
    
    public List<FreeBoardVO> getNoticeList() throws Exception {
        return freeBoardDAO.getNoticeList();
    }
    
    public int updateNoticeYn(int bNo, String bNoticeYn) throws Exception {
        return freeBoardDAO.updateNoticeYn(bNo, bNoticeYn);
    }
    
    public int boardReform(FreeBoardVO freeBoard) {
        // 1. 부모 게시물에 답변글이 이미 달려있는지 확인합니다.
        boolean hasReplies = freeBoardDAO.hasReplies(freeBoard.getbNo());

        // 2. 이미 답변글이 달려있는 경우, 새로운 답변글의 계층 번호를 증가시킵니다.
        if (hasReplies) {
            int parentDepthNo = freeBoardDAO.getParentDepthNo(freeBoard.getbNo());
            freeBoard.setbDepthNo(parentDepthNo + 1);
        }
        
        // 3. 부모 게시물의 가장 큰 그룹 번호를 가져옵니다.
        int maxBgno = freeBoardDAO.getMaxBgno(freeBoard.getbNo());
        freeBoard.setbGNo(maxBgno + 1);
        
        // 4. 새로운 답변글의 fkseq(외래 키 순서)를 부모 게시물의 fkseq에 기반하여 설정합니다.
        int parentFkSeq = freeBoardDAO.getFkSeq(freeBoard.getbNo());
        freeBoard.setbFkSeq(parentFkSeq);
        
        // 5. DAO 메소드를 호출하여 답변글을 등록합니다.
        return freeBoardDAO.boardReform(freeBoard);
    }
}
