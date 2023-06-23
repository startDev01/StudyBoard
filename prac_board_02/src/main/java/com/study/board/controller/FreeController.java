package com.study.board.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.study.board.service.FreeBoardService;
import com.study.board.vo.FreeBoardVO;

@Controller
public class FreeController {

    private final FreeBoardService freeBoardService;

    @Autowired
    public FreeController(FreeBoardService freeBoardService) {
        this.freeBoardService = freeBoardService;
    }

    // 게시물 목록 출력
    @RequestMapping("list.do")
    public String boardList(Model model) throws Exception {
        List<FreeBoardVO> freeboardList = freeBoardService.getBoardList();
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

    // 게시물 상세 페이지 출력
    @RequestMapping("view.do")
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

    // 게시물 수정
    @RequestMapping("edit.do")
    public String boardEdit(Model model, @RequestParam("bNo") int bNo) throws Exception {
        FreeBoardVO freeBoard = freeBoardService.getBoard(bNo);
        model.addAttribute("freeBoard", freeBoard);
        return "board/edit";
    }

    // 게시물 삭제
    @RequestMapping("delete.do")
    public String boardDelete(@RequestParam("bNo") int bNo) throws Exception {
        FreeBoardVO freeBoard = freeBoardService.getBoard(bNo);
        if (freeBoard != null) {
            freeBoardService.deleteBoard(freeBoard);
        }
        return "redirect:/list.do";
    }

    // 게시물 등록
    @RequestMapping("regist.do")
    public String boardRegist(@ModelAttribute("freeBoard") FreeBoardVO freeBoard) {
        if ("공지".equals(freeBoard.getbCategory())) {
            freeBoard.setbNoticeYn("Y");
        } else {
            freeBoard.setbNoticeYn("N");
        }
        freeBoardService.insertBoard(freeBoard);
        return "redirect:/list.do";
    }

    @RequestMapping("replyForm.do")
    public String replyForm(@RequestParam(value = "parentNo", required = false) int parentNo, Model model) throws Exception {
        FreeBoardVO parentBoard = freeBoardService.getBoard(parentNo); // 부모 게시물 가져오기
        model.addAttribute("parentBoard", parentBoard); // 부모 게시물 정보 전달

        return "board/replyForm"; // replyForm JSP로 이동
    }

    @RequestMapping("submitReply.do")
    public String submitReply(@ModelAttribute("replyBoard") FreeBoardVO replyBoard, Model model) throws Exception {
        // replyBoard에 양식 데이터가 자동으로 바인딩되어 전달됨

        // 답변 게시물 등록
        freeBoardService.insertReply(replyBoard);

        // 활성화된 게시판 목록에 내가 생성한 답변글이 표시되도록 수정
        List<FreeBoardVO> boardList = freeBoardService.getBoardList(); // 현재 활성화된 게시판 목록 가져오기
        model.addAttribute("boardList", boardList); // 게시판 목록 전달

        return "board/boardList"; // 게시판 목록 JSP로 이동
    }
    
    // 게시물 수정본 등록
    @RequestMapping("form.do")
    public String boardForm(@ModelAttribute("freeBoard") FreeBoardVO freeBoard) {
        return "board/form";
    }
}
