package com.study.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.study.board.vo.FreeBoardVO;

@Mapper
public interface FreeBoardDAO {
    public List<FreeBoardVO> getBoardList();
    public FreeBoardVO getBoard(int bNo);
    public int updateBoard(FreeBoardVO freeBoard);
    public int deleteBoard(FreeBoardVO freeBoard);
    public int insertBoard(FreeBoardVO freeBoard);
    public int boardReform(FreeBoardVO freeBoard);
    public void updateViewCnt(int bNo);
    public List<FreeBoardVO> getNoticeList();
    public int updateNoticeYn(int bNo, String bNoticeYn);
    
    // 추가된 메소드
    public boolean hasReplies(int bNo);
    public int getParentDepthNo(int bNo);
    public int getMaxBgno(int bNo);
    public int getFkSeq(int bNo);
}
