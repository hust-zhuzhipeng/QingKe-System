<%@ page language="java" contentType="text/html; charset=UTF-8"

    pageEncoding="UTF-8"%>

<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>登录成功</title>

</head>

<body>

 

 

欢迎你,<shiro:principal/>!<br>

 

 

<shiro:hasRole name="admin">

	具备admin角色才能看到这句话<br>

</shiro:hasRole>

 

 

<shiro:hasRole name="guest">

	具备teacher角色才能看到这句话<br>

</shiro:hasRole>

 

 

<shiro:hasPermission name="user:create">

	具备user:create权限才能看到这句话<br>

</shiro:hasPermission>

 

 

<shiro:hasPermission name="student:update">

	具备student:update权限才能看到这句话<br>

</shiro:hasPermission>

<br>

 

 

<shiro:hasPermission name="{student:update,user:*}">

	具备student:update,user:*权限才能看到这句话<br>

</shiro:hasPermission>

 

 

<a href="teacher">需要user:create权限才能访问</a><br>

<a href="logout">安全退出</a>

</body>

</html>
