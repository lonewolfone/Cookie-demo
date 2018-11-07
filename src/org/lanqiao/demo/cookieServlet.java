package org.lanqiao.demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
            /*//获取每个Cookie的名称
            String cookName = cookie1.getName();
            //判断获取到的cookie的名称是否是我们所关心的cookie，如果是则获取cookie的值
            if(cookName.equals("name")){
                out.print(cookie1.getValue());
            }*/
        //获取所有cookie的名字和值
            out.print(cookie1.getName() +"----"+cookie1.getValue());

        }
    }
}
