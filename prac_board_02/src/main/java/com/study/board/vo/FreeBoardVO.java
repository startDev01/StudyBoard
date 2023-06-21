package com.study.board.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.stereotype.Component;

@Component
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

	private int bGNo;
	private int bFkSeq;
	private int bDepthNo;

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

	public int getbGNo() {
		return bGNo;
	}

	public void setbGNo(int bGNo) {
		this.bGNo = bGNo;
	}

	public int getbFkSeq() {
		return bFkSeq;
	}

	public void setbFkSeq(int bFkSeq) {
		this.bFkSeq = bFkSeq;
	}

	public int getbDepthNo() {
		return bDepthNo;
	}

	public void setbDepthNo(int bDepthNo) {
		this.bDepthNo = bDepthNo;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
