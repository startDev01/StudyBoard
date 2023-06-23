drop table free_board;

CREATE SEQUENCE seq_free_board;

CREATE TABLE free_board (
   b_no     NUMBER NOT NULL, 
   b_title  VARCHAR2(250) NOT NULL,
   b_category  CHAR(10) , 
   b_writer VARCHAR2(100) NOT NULL,
   b_pass   varchar2(60)  not null, 
   b_content CLOB,
   b_hit     NUMBER,   
   b_reg_date DATE DEFAULT SYSDATE,
   b_mod_date DATE ,
   b_del_yn  CHAR(1) DEFAULT 'N',
   CONSTRAINT pk_free_board PRIMARY KEY(b_no)
);

COMMENT ON TABLE free_board  IS '자유 게시판';
COMMENT ON COLUMN free_board.b_no IS '글 번호';
COMMENT ON COLUMN free_board.b_title IS '글 제목';
COMMENT ON COLUMN free_board.b_category IS '글 분류 코드';
COMMENT ON COLUMN free_board.b_writer IS '작성자명';
COMMENT ON COLUMN free_board.b_pass IS '비밀번호';
COMMENT ON COLUMN free_board.b_content IS '글 내용';
COMMENT ON COLUMN free_board.b_hit IS '조회수';
COMMENT ON COLUMN free_board.b_reg_date IS '등록 일자';
COMMENT ON COLUMN free_board.b_mod_date IS '수정 일자';
COMMENT ON COLUMN free_board.b_del_yn IS '삭제 여부';
COMMENT ON COLUMN free_board.b_notice_yn IS '공지글 여부';

commit;

select * from free_board;

alter table free_board add b_notice_yn char(1) default 'n' ;
ALTER TABLE free_board ADD parent_no NUMBER;
ALTER TABLE free_board ADD depth NUMBER;


