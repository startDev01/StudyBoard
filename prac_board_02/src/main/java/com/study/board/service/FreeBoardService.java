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

    /**
     * 게시물 목록 조회 메소드
     * 
     * @return 게시물 목록
     * @throws Exception
     */
    public List<FreeBoardVO> getBoardList() throws Exception {
        List<FreeBoardVO> boardList = freeBoardDAO.getBoardList();
        for (FreeBoardVO board : boardList) {
            if ("Y".equals(board.getbNoticeYn())) {
                board.setbCategory("공지");
            }
        }
        return boardList;
    }
    
    /**
     * 공지사항 조회 메소드
     * 
     * @param bNo 게시물 번호
     * @return 공지사항
     * @throws Exception
     */
    public FreeBoardVO getNotice(int bNo) throws Exception {
        FreeBoardVO board = freeBoardDAO.getBoard(bNo);
        if (board != null && "Y".equals(board.getbNoticeYn())) {
            board.setbCategory("공지");
        }
        return board;
    }
    
    /**
     * 조회수 업데이트 메소드
     * 
     * @param bNo 게시물 번호
     * @throws Exception
     */
    public void updateViewCnt(int bNo) throws Exception {
        sqlSessionTemplate.update("com.study.board.dao.FreeBoardDAO.updateViewCnt", bNo);
    }
    
    /**
     * 공지사항 목록 조회 메소드
     * 
     * @return 공지사항 목록
     * @throws Exception
     */
    public List<FreeBoardVO> getNoticeList() throws Exception {
        return freeBoardDAO.getNoticeList();
    }
    
    /**
     * 공지사항 여부 업데이트 메소드
     * 
     * @param bNo         게시물 번호
     * @param bNoticeYn   공지사항 여부 (Y/N)
     * @return 업데이트된 행 수
     * @throws Exception
     */
    public int updateNoticeYn(int bNo, String bNoticeYn) throws Exception {
        return freeBoardDAO.updateNoticeYn(bNo, bNoticeYn);
    }
    
    /**
     * 게시물 수정 메소드
     * 
     * @param freeBoard 수정할 게시물 정보
     * @return 업데이트된 행 수
     */
    public int boardReform(FreeBoardVO freeBoard) {
        boolean hasReplies = freeBoardDAO.hasReplies(freeBoard.getbNo());

        if (hasReplies) {
            int parentDepthNo = freeBoardDAO.getParentDepthNo(freeBoard.getbNo());
            freeBoard.setbDepthNo(parentDepthNo + 1);
        }
        
        int maxBgno = freeBoardDAO.getMaxBgno(freeBoard.getbNo());
        freeBoard.setbGNo(maxBgno + 1);

        int parentFkSeq = freeBoardDAO.getFkSeq(freeBoard.getbNo());
        freeBoard.setbFkSeq(parentFkSeq);
        
        return freeBoardDAO.boardReform(freeBoard);
    }
}
