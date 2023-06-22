package com.study.board.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.study.board.dao.FreeBoardDAO;
import com.study.board.service.FreeBoardService;
import com.study.board.vo.FreeBoardVO;

@Controller
public class FreeController {

    @Inject
    private FreeBoardDAO freeBoardDAO;

    @Autowired
    private FreeBoardService freeBoardService;
    
//  게시물 목록 출력  
    @RequestMapping("/test/list.do")
    public String boardList(Model model) throws Exception {
        List<FreeBoardVO> freeboardList = freeBoardDAO.getBoardList();
        List<FreeBoardVO> noticeList = new ArrayList<>();
        List<FreeBoardVO> normalList = new ArrayList<>();

        for (FreeBoardVO freeBoard : freeboardList) {
            if ("Y".equals(freeBoard.getbNoticeYn())) {
                noticeList.add(freeBoard);
            } else {
                normalList.add(freeBoard);
            }
        }
        model.addAttribute("noticeList", noticeList);
        model.addAttribute("normalList", normalList);
        return "board/boardList";
    }

//  게시물 상세 페이지 출력
    @RequestMapping("/test/view.do")
    public String boardView(@RequestParam("bNo") int bNo, HttpSession session, Model model) throws Exception {
        Set<Integer> visitedBoardSet = (Set<Integer>) session.getAttribute("visitedBoardSet");
        if (visitedBoardSet == null) {
            visitedBoardSet = new HashSet<>();
            session.setAttribute("visitedBoardSet", visitedBoardSet);
        }

        if (!visitedBoardSet.contains(bNo)) {
            freeBoardService.updateViewCnt(bNo);
            visitedBoardSet.add(bNo);
        }

        FreeBoardVO freeBoard = freeBoardService.getNotice(bNo);
        if ("Y".equals(freeBoard.getbNoticeYn())) {
            freeBoard.setbCategory("공지");
        }

        model.addAttribute("freeBoard", freeBoard);
        return "board/view";
    }
    
//  게시물 수정 
    @RequestMapping("/test/edit.do")
    public String boardEdit(Model model, int bNo) {
        FreeBoardVO freeBoard = freeBoardDAO.getBoard(bNo);
        model.addAttribute("freeBoard", freeBoard);
        return "board/edit";
    }
    
//	게시물 삭제
    @RequestMapping("/test/delete.do")
    public String boardDelete(@ModelAttribute("freeBoard") FreeBoardVO freeBoard) {
        freeBoardDAO.deleteBoard(freeBoard);
        return "redirect:/test/list.do";
    }
    
//	게시물 등록
    @RequestMapping("/test/regist.do")
    public String boardRegist(@ModelAttribute("freeBoard") FreeBoardVO freeBoard) {
        if ("공지".equals(freeBoard.getbCategory())) {
            freeBoard.setbNoticeYn("Y");
        } else {
            freeBoard.setbNoticeYn("N");
        }
        freeBoardDAO.insertBoard(freeBoard);
        return "redirect:/test/list.do";
    }

//  답변 게시물 등록
    @RequestMapping("/test/replyForm.do")
    public String replyForm(@RequestParam("parentNo") int parentNo, Model model) {
    	
    	return ("redirect:/test/list.do");
    }
    
//  게시물 수정본 등록
    @RequestMapping("/test/form.do")
    public String boardForm(@ModelAttribute("freeBoard") FreeBoardVO freeBoard) {
        return "board/form";
    }

}
