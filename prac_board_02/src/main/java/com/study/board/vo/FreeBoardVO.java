package com.study.board.vo;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class FreeBoardVO {

	private int bNo;
	private String bTitle;
	private String bCategory;
	private String bWriter;
	private String bPass;
	private String bContent;
	private int bHit;
	private String bRegDate;
	private String bModDate;
	private String bDelYn;
	private String bNoticeYn;
	
	//답글 구현에 쓰이는 변수
	private int parentNo;	
	private int depth;		
	private List<FreeBoardVO> replyList;
	private String depthString;
	
	
	public String getDepthString() {
		return depthString;
	}

	public void setDepthString(String depthString) {
		this.depthString = depthString;
	}

	public List<FreeBoardVO> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<FreeBoardVO> replyList) {
		this.replyList = replyList;
	}

	public int getParentNo() {
		return parentNo;
	}

	public void setParentNo(int parentNo) {
		this.parentNo = parentNo;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getbNo() {
		return bNo;
	}

	public void setbNo(int bNo) {
		this.bNo = bNo;
	}

	public String getbTitle() {
		return bTitle;
	}

	public void setbTitle(String bTitle) {
		this.bTitle = bTitle;
	}

	public String getbCategory() {
		return bCategory;
	}

	public void setbCategory(String bCategory) {
		this.bCategory = bCategory;
	}

	public String getbWriter() {
		return bWriter;
	}

	public void setbWriter(String bWriter) {
		this.bWriter = bWriter;
	}

	public String getbPass() {
		return bPass;
	}

	public void setbPass(String bPass) {
		this.bPass = bPass;
	}

	public String getbContent() {
		return bContent;
	}

	public void setbContent(String bContent) {
		this.bContent = bContent;
	}

	public int getbHit() {
		return bHit;
	}

	public void setbHit(int bHit) {
		this.bHit = bHit;
	}

	public String getbRegDate() {
		return bRegDate;
	}

	public void setbRegDate(String bRegDate) {
		this.bRegDate = bRegDate;
	}

	public String getbModDate() {
		return bModDate;
	}

	public void setbModDate(String bModDate) {
		this.bModDate = bModDate;
	}

	public String getbDelYn() {
		return bDelYn;
	}

	public void setbDelYn(String bDelYn) {
		this.bDelYn = bDelYn;
	}

	public String getbNoticeYn() {
		return bNoticeYn;
	}

	public void setbNoticeYn(String bNoticeYn) {
		this.bNoticeYn = bNoticeYn;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
