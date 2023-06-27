package com.study.board.service;

import java.util.ArrayList;
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
    
    public List<FreeBoardVO> getBoardList() throws Exception {
        List<FreeBoardVO> boardList = freeBoardDAO.getBoardList();
        for (FreeBoardVO board : boardList) {
            if ("Y".equals(board.getbNoticeYn())) {
                board.setbCategory("공지");
            }
            int depth = board.getDepth();
            StringBuilder depthString = new StringBuilder();
            for (int i = 0; i < depth; i++) {
                depthString.append("&nbsp;&nbsp;&nbsp;&nbsp;");
            }
            board.setDepthString(depthString.toString());
            
            // 답변글이 있는 경우, 답변글의 계층 구조를 반영하여 depthString을 생성합니다.
            List<FreeBoardVO> replyList = board.getReplyList();
            if (replyList != null) {
                for (FreeBoardVO reply : replyList) {
                    int replyDepth = reply.getDepth();
                    StringBuilder replyDepthString = new StringBuilder();
                    for (int i = 0; i < replyDepth; i++) {
                        replyDepthString.append("&nbsp;&nbsp;&nbsp;&nbsp;");
                    }
                    reply.setDepthString(replyDepthString.toString());
                }
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

    public void insertBoard(FreeBoardVO freeBoard) {
        if (freeBoard.getParentNo() == 0) {
            freeBoard.setParentNo(0); // 새로운 게시물의 부모 번호는 0으로 설정
            freeBoard.setDepth(0); // 새로운 게시물의 depth는 0으로 설정
        } else {
            FreeBoardVO parentBoard = freeBoardDAO.getBoard(freeBoard.getParentNo());
            int parentDepth = parentBoard.getDepth();
            int depth = parentDepth + 1;
            freeBoard.setDepth(depth);
        }
        freeBoardDAO.insertBoard(freeBoard);
    }

    public void insertReplyBoard(FreeBoardVO freeBoard) throws Exception {
        FreeBoardVO parentBoard = freeBoardDAO.getBoard(freeBoard.getParentNo()); // 부모 게시물 가져오기
        int parentDepth = parentBoard.getDepth();
        int depth = parentDepth + 1;
        freeBoard.setDepth(depth);
        
        // 새로운 답변글을 추가할 때, 기존의 계층 구조를 유지하기 위해 부모 글의 replyList에 새로운 답변글을 추가합니다.
        List<FreeBoardVO> replyList = parentBoard.getReplyList();
        if (replyList == null) {
            replyList = new ArrayList<>();
        }
        replyList.add(freeBoard);
        parentBoard.setReplyList(replyList);
        
        freeBoardDAO.insertReplyBoard(freeBoard);
    }

    public List<FreeBoardVO> getNoticeList() throws Exception {
        return freeBoardDAO.getNoticeList();
    }

    public int updateNoticeYn(int bNo, String bNoticeYn) throws Exception {
        return freeBoardDAO.updateNoticeYn(bNo, bNoticeYn);
    }

    public void updateViewCnt(int bNo) throws Exception {
        sqlSessionTemplate.update("com.study.board.dao.FreeBoardDAO.updateViewCnt", bNo);
    }

    
    public FreeBoardVO getBoard(int bNo) throws Exception {
        FreeBoardVO board = freeBoardDAO.getBoard(bNo);
        if (board != null && "Y".equals(board.getbNoticeYn())) {
            board.setbCategory("공지");
        }
        return board;
    }

    public int getMaxDepth() {
        return freeBoardDAO.getMaxDepth();
    }
    
    void deleteBoard(int bNo) {
        freeBoardDAO.deleteBoard(bNo);
    }

}
