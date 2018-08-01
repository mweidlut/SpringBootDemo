package org.test.web.test;


import org.test.web.util.HttpClientUtils;

public class ParseHtml {

    public static void main(String[] args) {
        parseZi();
    }

    private static void parseZi(){
        String url = "http://finance.sina.com.cn/futures/quotes/ZN1810.shtml";

        try {
            String htmlStr = HttpClientUtils.doGet_GBK(url);

            System.out.println(htmlStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
