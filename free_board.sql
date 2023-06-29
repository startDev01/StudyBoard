drop table free_board;

DROP SEQUENCE seq_free_board;
CREATE SEQUENCE seq_free_board
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE TABLE free_board (
   b_no         NUMBER NOT NULL, 
   b_title      VARCHAR2(250) NOT NULL,
   b_category   CHAR(30), 
   b_writer     VARCHAR2(100) NOT NULL,
   b_pass       VARCHAR2(60) NOT NULL, 
   b_content    CLOB,
   b_hit        NUMBER,   
   b_reg_date   DATE DEFAULT SYSDATE,
   b_mod_date   DATE DEFAULT SYSDATE,
   b_del_yn     CHAR(1) DEFAULT 'N',
   b_notice_yn  CHAR(1) DEFAULT 'N',
   parent_no    NUMBER,
   depth        NUMBER,
   CONSTRAINT pk_free_board PRIMARY KEY(b_no)
);

COMMENT ON TABLE free_board IS '자유 게시판';
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
COMMENT ON COLUMN free_board.parent_no IS '답변글 번호';
COMMENT ON COLUMN free_board.depth IS '답변글 들여쓰기';

COMMIT;

SELECT * FROM free_board;

BEGIN
   FOR i IN 1..200 LOOP
      INSERT INTO free_board (
         b_no, b_title, b_category, b_writer, b_pass, b_content, b_hit, b_reg_date, b_mod_date, b_del_yn, b_notice_yn, parent_no, depth
      ) VALUES (
         seq_free_board.nextval, '제목' || i, '카테고리' || i, '작성자' || i, '비밀번호' || i, '내용' || i, 0, SYSDATE, SYSDATE, 'N', 'N', NULL, 0
      );
   END LOOP;
   COMMIT;
END;
/
