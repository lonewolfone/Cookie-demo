<%@ page import="org.lanqiao.demo.User" %><%--
  Created by IntelliJ IDEA.
  User: 听音乐的酒
  Date: 2018/11/7
  Time: 11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登陆成功页面</title>
</head>
<body>
    <h2>登陆成功</h2>
    <%
        //从request中获取User对象
       User user = (User) request.getAttribute("user");
    %>
    <h3>恭喜您：<%=user.getUsername()%>登陆成功</h3>
</body>
</html>
