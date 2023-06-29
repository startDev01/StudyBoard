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

import com.study.board.dao.FreeBoardDAO;
import com.study.board.service.FreeBoardService;
import com.study.board.vo.FreeBoardVO;
import com.study.board.vo.PagingVO;

@Controller
public class FreeController {

	@Autowired
	private FreeBoardDAO freeBoardDAO;

	@Autowired
	private FreeBoardService freeBoardService;

	// 게시물 목록 출력
	@RequestMapping("list.do")
	public String boardList(Model model, @ModelAttribute("paging") PagingVO paging) {
	    int totalRowCount = freeBoardDAO.getTotalRowCount(paging);
	    paging.setTotalRowCount(totalRowCount);
	    paging.pageSetting();
	    List<FreeBoardVO> freeBoardList = freeBoardDAO.getBoardList(paging);
	    List<FreeBoardVO> noticeList = new ArrayList<>();
	    List<FreeBoardVO> normalList = new ArrayList<>();

	    for (FreeBoardVO freeBoard : freeBoardList) {
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
	public String boardEdit(Model model, int bNo) {
		FreeBoardVO freeBoard = freeBoardDAO.getBoard(bNo);
		model.addAttribute("freeBoard", freeBoard);
		return "board/edit";
	}

	// 게시물 삭제
	@RequestMapping("delete.do")
	public String boardDelete(@RequestParam("bNo") int bNo) {
	    freeBoardDAO.deleteBoard(bNo);
	    System.out.println("삭제되는 게시물의 번호는 : "+ bNo);
	    return "redirect:/list.do";
	}

	// 게시물 등록
	@RequestMapping("regist.do")
	public String boardRegist(@ModelAttribute("freeBoard") FreeBoardVO freeBoard) throws Exception {
	    if (freeBoard.getParentNo() != 0 && freeBoard.getParentNo() > 0) {
	        FreeBoardVO parentBoard = freeBoardService.getBoard(freeBoard.getParentNo());
	        int parentDepth = parentBoard.getDepth();
	        freeBoard.setDepth(parentDepth + 1); // 수정된 부분: 답변글의 depth 설정
	        freeBoardService.insertReplyBoard(freeBoard);
	    } else {
	        if ("공지".equals(freeBoard.getbCategory())) {
	            freeBoard.setbNoticeYn("Y");
	        } else {
	            freeBoard.setbNoticeYn("N");
	        }
	        int maxDepth = freeBoardService.getMaxDepth(); // 최대 depth 가져오기
	        freeBoard.setDepth(maxDepth + 1); // 현재 글의 depth는 최대 depth + 1로 설정

	        freeBoardService.insertBoard(freeBoard); // 일반 글 작성
	    }
	    return "redirect:/list.do";
	}

	@RequestMapping("replyForm.do")
	public String replyForm(@RequestParam(value = "parentNo", required = false) int parentNo, Model model)
	        throws Exception {
	    FreeBoardVO parentBoard = freeBoardService.getBoard(parentNo); // 부모 게시물 가져오기
	    int parentDepth = parentBoard.getDepth();
	    model.addAttribute("parentBoard", parentBoard); // 부모 게시물 정보 전달
	    model.addAttribute("parentDepth", parentDepth + 1); // 수정된 부분: 새로운 depth 정보 전달

	    return "board/replyForm";
	}

	// 게시물 수정본 등록
	@RequestMapping("form.do")
	public String boardForm(@ModelAttribute("freeBoard") FreeBoardVO freeBoard) {
		return "board/form";
	}

}
