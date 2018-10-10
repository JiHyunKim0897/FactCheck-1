<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>사실 확인을 해보아요</title>
	<link rel="stylesheet" type="text/css" href="Main.css"/>
</head>
<body>
	<p id="p_title"><span>C</span>heck <span>T</span>he <span>F</span>acts!</p>
	
	<div id="div_left">
		<div id="div_input">
			<p id="p_input">문장 입력</p>
			<div id="div_inputArea">
				<form action="inputProcess.jsp" method="post">
				<table>
					<tr>
						<td><textarea id="txt_input" name="txt_input"></textarea></td>
					</tr>
					<tr>
						<td><input type="submit" id="btn_input" value="입력"></td>
					</tr>
				</table>
				</form>
			</div>
		</div>
		<div id="div_result">
		</div>
	</div>
	<div id="div_right">
		<p id="p_relatedInfo">관련된 정보</p>
		<div id="div_relatedArea">
		</div>
	</div>
</body>
</html>