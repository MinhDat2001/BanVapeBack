package com.vape.gmailAPI;

public class MailForm {
    public static String mailForm(String name, int code){
        String res="<meta charset=\"UTF-8\">";
        res+="<h2> Xin chào "+name+"</h2>";
        res+="<h4>Chúng tôi rất vui khi bạn đến với trang web của chúng tôi</h4>";
        res+="<h4>Mã số xác thực là <b style=\"color:red\">"+code+"</b></h4>";
        res+="<h4>Xin cảm ơn!</h4>" +
                "<br/>" +
                "<h4>Liên hệ</h4>" +
                "<p>Đặng Minh Đạt</p>" +
                "<p>Phone: <a href=\"tel:0963632312\">" +"0963632312</a></p>" +
                "<p>Email: <a href=\"mailto:dangminhdat@minhdat.dev\">dangminhdat@minhdat.dev</a></p>";
        return res;
    }
}
