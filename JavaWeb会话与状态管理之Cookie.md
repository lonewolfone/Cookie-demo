## JavaWeb会话与状态管理之Cookie

### 前提

![](C:\Users\听音乐的酒\Desktop\笔记\学习笔记\imgs1\16.png)

![](C:\Users\听音乐的酒\Desktop\笔记\学习笔记\imgs1\17.png)

![](C:\Users\听音乐的酒\Desktop\笔记\学习笔记\imgs1\18.png)

### Cookie

#### Cookie机制

![](C:\Users\听音乐的酒\Desktop\笔记\学习笔记\imgs1\19.png)

![](C:\Users\听音乐的酒\Desktop\笔记\学习笔记\imgs1\20.png)

####Cookie的传送过程示意图

![](C:\Users\听音乐的酒\Desktop\笔记\学习笔记\imgs1\21.png)

####在Servlet程序中使用Cookie

![](C:\Users\听音乐的酒\Desktop\笔记\学习笔记\imgs1\22.png)

![](C:\Users\听音乐的酒\Desktop\笔记\学习笔记\imgs1\23.png)

![](C:\Users\听音乐的酒\Desktop\笔记\学习笔记\imgs1\25.png)

![](C:\Users\听音乐的酒\Desktop\笔记\学习笔记\imgs1\24.png)

cookieServlet

```java
@WebServlet("/cookieServlet")
public class cookieServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        //创建Cookie
        Cookie cookie = new Cookie("name","二狗");
        //Cookie最大有效期
        cookie.setMaxAge(60*60*24);
        //将Cookie传到客户端
        response.addCookie(cookie);
        //获取cookie
        Cookie[] cookies = request.getCookies();
        //对获取到的cookie数组进行遍历
        for(Cookie cookie1:cookies){
            //获取每个Cookie的名称
            String cookName = cookie1.getName();
            //判断获取到的cookie的名称是否是我们所关心的cookie，如果是则获取cookie的值
            if(cookName.equals("name")){
                out.print(cookie1.getValue());
            }

        }
    }
}

```

index.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
          <a href="/cookieServlet">Cookie</a>
  </body>
</html>
```

####Cookie的发送

![](C:\Users\听音乐的酒\Desktop\笔记\学习笔记\imgs1\26.png)

![](C:\Users\听音乐的酒\Desktop\笔记\学习笔记\imgs1\27.png)

####会话cookie和持久cookie的区别

![](C:\Users\听音乐的酒\Desktop\笔记\学习笔记\imgs1\28.png)

####Cookie的读取

![](C:\Users\听音乐的酒\Desktop\笔记\学习笔记\imgs1\29.png)

####Cookie的应用案例

#####自动登陆

1、Cookie由服务器产生，保存在客户端

2、建一个网站，使用Cookie实现自动登陆



User类

```java
public class User {
    private String username;
    private String password;
    public User(){

    }
	。。。
    。。。
}
```

loginServlet

```java
@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
            1、用户从客户端输入了用户名和密码
                验证用户名和密码
                如果用户名和密码正确，将用户的登陆信息保存在cookie中，直接跳转到success.jsp
                 否则，重新跳转到登陆页面
            2、用户从客户端没有输入用户名和密码
                则需要从cookie去检索用户名和密码，如果正确，直接跳转到success.jsp
        */

        //根据属性名称获取属性名
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User  user = null;
        if (username!=null&&!username.trim().equals("")){
            if (username.equals("aaaa") && password.equals("123321")){
                 //将用户信息保存到cookie中
                //因cookie中只能存放一个信息，所以需将用户信息进行封装
                 user = new User(username,password);
                Cookie cookie = new Cookie("user",username);
                cookie.setMaxAge(60*60*24);
                response.addCookie(cookie);
            }
        }else {

            Cookie[] cookies = request.getCookies();
            for(Cookie cook:cookies){
                String cookieName = cook.getName();
                if (cookieName.equals("user")){
                    user = new User(cook.getValue(),"123321");
                }
            }
        }

        if (user!= null){
            request.setAttribute("user",user);
            request.getRequestDispatcher("/success.jsp").forward(request,response);
        }else{
            request.getRequestDispatcher("/index.jsp").forward(request,response);
        }
    }
}
```

index.jsp

```jsp
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

```

success.jsp

```jsp
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
```

#####跟踪用户上次访问站点

####Cookie中存取中文

loginServlet

```java
@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
            1、用户从客户端输入了用户名和密码
                验证用户名和密码
                如果用户名和密码正确，将用户的登陆信息保存在cookie中，直接跳转到success.jsp
                 否则，重新跳转到登陆页面
            2、用户从客户端没有输入用户名和密码
                则需要从cookie去检索用户名和密码，如果正确，直接跳转到success.jsp
        */
        /*
            Cookie无法保存对象
            在cookie保存对象的实现方式：
            可以采用JSON  将该对象转换为一个JSON字符串   需要对转化之后的json字符串实行编码 保存在cookie中
            当需要该信息的时候   可以从cookie中获取这个json字符串（需要解码）  然后 把他重新转换为我们的对象  这样就可已使用了
        */
        //根据属性名称获取属性名
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User  user = null;
        if (username!=null&&!username.trim().equals("")){
            if (username.equals("abcd") && password.equals("123321")){
                 //将用户信息保存到cookie中
                //因cookie中只能存放一个信息，所以需将用户信息进行封装
                 user = new User(username,password);
                 //将User对象转换为JSON字符串,并对该对象进行编码
                String userStr = JSON.toJSONString(user);
                //将其保存到Cookie中
                Cookie cookie = new Cookie("user", URLEncoder.encode(userStr,"utf-8"));
                 /*
                在cookie保存中文时  也同样需要采用URLEncoder对其进行编码   在获取的时候  同样采用URLDecode进行解码
                 */
                Cookie cookie1 = new Cookie("nameone","张三");
                cookie.setMaxAge(60*60*24);
                response.addCookie(cookie);
                response.addCookie(cookie1);
            }
        }else {

            Cookie[] cookies = request.getCookies();
            for(Cookie cook:cookies){
                String cookieName = cook.getName();
                if (cookieName.equals("user")){
                   // user = new User(cook.getValue(),"123321");
                    //从Cookie中获取字符串，并转为对象并对该对象进行解码
                    JSONObject jsonObject = JSON.parseObject(URLDecoder.decode(cook.getValue(),"utf-8"));
                    //从JSONObject中获取User对象的信息
                    String userName = jsonObject.getString("username");
                    String passWord = jsonObject.getString("password");
                    //构建User对象
                     user = new User(userName,passWord);
                }

                if (cookieName.equals("nameone")){
                    System.out.println(cook.getValue());
                }
            }
        }

        if (user!= null){
            request.setAttribute("user",user);
            request.getRequestDispatcher("/success.jsp").forward(request,response);
        }else{
            request.getRequestDispatcher("/index.jsp").forward(request,response);
        }
    }
}
```



