<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>用户注册页面</title>

</head>

<body>

<form action="/register" method="post">
	username:<input type="text" name="username"/><br/>
	password:<input type="password" name="password"><br/>
	phone:<input type="text" name="phone"><br/>
	email:<input type="text" name="email"><br/>
	<input type="submit" value="注册" />
</form>
</body>
</html>
