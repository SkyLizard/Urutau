<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome - Sign in</title>
</head>
<body>
	<h1>Sign in</h1>
	<form action="register" method="POST">
		Login: <input name="user.login" type="text"/>
		Password: <input name="user.password" type="password"/>
		<input type="submit"/>
	</form>
</body>
</html>