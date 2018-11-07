<%--
  Created by IntelliJ IDEA.
  User: 听音乐的酒
  Date: 2018/11/6
  Time: 22:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>用户登陆页面</title>
  </head>
  <body>
          <%--<a href="/cookieServlet">Cookie</a>--%>
          <h2>登陆页面</h2>
          <form action="/loginServlet" method="post">
            用户名：<input type="text" name="username" value=""><br><br>
            密  码：<input type="password" name="password" value="">
            <input type="submit" value="提交">
          </form>
  </body>
</html>
