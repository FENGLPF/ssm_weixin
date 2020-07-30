package com.ssm_weixin.controller;

import com.ssm_weixin.model.WeChatMsg;
import com.ssm_weixin.util.JsonUtils;
import com.ssm_weixin.util.SHA1Util;
import com.ssm_weixin.util.WeChatUtil;
import com.ssm_weixin.util.XmlUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;

@RestController
@RequestMapping(value="/weixin/wechat")
public class WeChatController extends HttpServlet {

    @RequestMapping(value="/security",method = RequestMethod.GET)
    public void security(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        if(WeChatUtil.checkSignature(signature,timestamp,nonce)){
            System.out.println("校验成功:"+echostr);
            out.print(echostr);
        }
        out.close();
        out=null;

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        String token = "lpfToken";
        String jiami = "";
        String openid=request.getParameter("openid");
        String body=request.getParameter("body");
        System.out.println(body);
        Date date=new Date();
        PrintWriter out = response.getWriter();
        if(WeChatUtil.checkSignature(signature,timestamp,nonce)){
            System.out.println("校验成功:"+echostr);
            out.print(echostr);
        }
        out.close();
        out=null;
    }

    @RequestMapping(value = "/security", method = RequestMethod.POST)
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        //获取服务器发送过来的信息，因为不是参数，得用输入流读取
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try{
            reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
            String line = null;
            while ((line = reader.readLine()) != null){
                sb.append(line);
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try{
                if (null != reader){ reader.close();}
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        String backMsg="";
        try {
            WeChatMsg msg = XmlUtil.xmlToBean(sb.toString(), WeChatMsg.class);
            System.out.println(JsonUtils.objectToJsonStr(msg));
            switch (msg.getMsgType()){
                case "text":
                    backMsg = "文本";
                    break;
                case "event":
                    backMsg = "未知事件";
                    switch (msg.getEvent()){
                        case "subscribe":
                            backMsg = "订阅";
                            break;
                        case "unsubscribe":
                            backMsg = "取消订阅";
                            break;
                        default:
                            break;
                    }
                    break;
                case "image":
                    backMsg = "图片";
                    break;
                case "video":
                    backMsg = "视频";
                    break;
                default:
                    backMsg = "未知操作";
                    break;
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        System.out.println("用户发送过来信息："+sb);//将用户发送得消息打印出来

        String openid=request.getParameter("openid");//获取参数中的openid
        PrintWriter out=response.getWriter();
        String replyMsg = "<xml>"
                + "<ToUserName><![CDATA["+openid+"]]></ToUserName>"//回复用户时，这里是用户的openid；但用户发送过来消息这里是微信公众号的原始id
                + "<FromUserName><![CDATA["+"gh_d2ac00f7dc53"+"]]></FromUserName>"//这里填写微信公众号 的原始id；用户发送过来时这里是用户的openid
                + "<CreateTime>1531553112194</CreateTime>"//这里可以填创建信息的时间，目前测试随便填也可以
                + "<MsgType><![CDATA[text]]></MsgType>"//文本类型，text，可以不改
                + "<Content><![CDATA["+backMsg+"]]></Content>"//文本内容，我喜欢你
                + "<MsgId>1234567890123456</MsgId> "//消息id，随便填，但位数要够
                + " </xml>";
        System.out.println("回复信息:"+replyMsg);//打印出来
        out.println(replyMsg);//回复
    }
}
