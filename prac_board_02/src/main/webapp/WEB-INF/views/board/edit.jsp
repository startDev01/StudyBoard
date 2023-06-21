<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<title>게시물 수정</title>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 0;
	padding: 20px;
}

.container {
	max-width: 600px;
	margin: 0 auto;
	padding: 20px;
	background-color: #f9f9f9;
	border: 1px solid #ccc;
}

.container h2 {
	font-size: 24px;
	margin-bottom: 20px;
}

.container table {
	width: 100%;
	border-collapse: collapse;
	margin-bottom: 20px;
}

.container table th, .container table td {
	padding: 10px;
	border: 1px solid #ccc;
}

.container table th {
	width: 30%;
	text-align: center;
	font-weight: bold;
}

.container table td input[type="text"], .container table td textarea {
	width: 100%;
	padding: 5px;
	border: 1px solid #ccc;
	box-sizing: border-box;
}

.container table td input[type="submit"] {
	padding: 10px 20px;
	background-color: #333;
	color: #fff;
	border: none;
	cursor: pointer;
}

.container table td input[type="submit"]:hover {
	background-color: #555;
}

.container table td .button-container {
        text-align: center;
}

.container table td .button-container input[type="submit"] {
        margin-left: 10px;
}
</style>
</head>
<body>
	<div class="container">
		<h2>게시물 수정</h2>
		<form action="edit.do" method="post">
			<table>
				<tr>
					<th>글번호</th>
					<td>${freeBoard.bNo }<input type="hidden" name="bNo"
						value="${freeBoard.bNo }"></td>
				</tr>
				<tr>
					<th>제목</th>
					<td><input type="text" name="bTitle"
						value="${freeBoard.bTitle }" required="required"></td>
				</tr>
				<tr>
					<th>작성자</th>
					<td>${freeBoard.bWriter }<input type="hidden" name="bWriter"
						value="${freeBoard.bWriter }"></td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td><input type="password" name="bPass" value=""
						required="required"></td>
				</tr>
				<tr>
					<th>분류</th>
					<td>${freeBoard.bCategory }<input type="hidden"
						name="bCategory" value="${freeBoard.bCategory }"></td>
				</tr>
				<tr>
					<th>내용</th>
					<td><textarea rows="10" name="bContent">${freeBoard.bContent }</textarea></td>
				</tr>
				<tr>
					<th>최근 등록일자</th>
					<td>${freeBoard.bModDate == null ? freeBoard.bRegDate : freeBoard.bModDate }</td>
				</tr>
				<tr>
					<td colspan="2">
						<div class="button-container">
							<input type="submit" value="수정"> 
							<input type="submit"value="삭제" formaction="delete.do">
							<input type="submit"value="취소" formaction="view.do">
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
