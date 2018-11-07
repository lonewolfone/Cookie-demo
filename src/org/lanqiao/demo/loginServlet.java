package org.lanqiao.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

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
