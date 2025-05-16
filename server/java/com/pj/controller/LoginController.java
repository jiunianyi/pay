package com.pj.controller;
import com.alipay.api.AlipayApiException;
import com.pj.ConService.pjj.userService;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pj.ConService.RedisService;
import com.pj.uitl.User;
import com.pj.uitl.newuser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pj.ConService.textpay;
import java.util.Random;

/**
 * 登录测试
 */
@RestController
@RequestMapping("/acc/")
public class LoginController {
    @Autowired
    private RedisService redisService;
    @Autowired
    private userService userService;
    @Autowired
    private textpay textpay;
    //唯一性
    public boolean whetherreg(String phone)
    {
        return true;
    }

    @PostMapping("getyzm")
    public String getyzm(@RequestBody newuser us) throws Exception {
        if(whetherreg(us.getphone()))
        {
            Random random = new Random();
            int randomNumber = 100000 + random.nextInt(900000);
            redisService.setValueWithExpiration(us.getphone(),String.valueOf(randomNumber));
            // 设置AccessKeyId、AccessKeySecret等信息
            String accessKeyId = System.getenv("ALIBABA_CLOUD_ACCESS_KEY_ID");
            String accessKeySecret = System.getenv("ALIBABA_CLOUD_ACCESS_KEY_SECRET");

            // 创建DefaultAcsClient实例并初始化
            DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            IAcsClient client = new DefaultAcsClient(profile);

            // 创建并设置请求
            SendSmsRequest request = new SendSmsRequest();
            request.setPhoneNumbers(us.getphone()); // 必填: 要发送到的手机号码
            request.setSignName("聊天室"); // 必填: 已经在阿里云上注册的短信签名
            request.setTemplateCode("SMS_478495608"); // 必填: 已经在阿里云上注册的短信模板CODE
            request.setTemplateParam("{\"code\":\""+randomNumber+"\"}"); // 必填: 短信模板的变量参数

            try {
                // 发送短信并打印结果
                SendSmsResponse response = client.getAcsResponse(request);
                System.out.println(response.getCode());
                System.out.println(response.getMessage());
                System.out.println(response.getRequestId());
                ObjectMapper objectMapper = new ObjectMapper();
                int yzm = randomNumber; // 示例值
                // 创建一个 ObjectNode 来表示 JSON 对象
                ObjectNode jsonNode = objectMapper.createObjectNode();
                jsonNode.put("yzm", yzm);
                jsonNode.put("fh",1002);
                return objectMapper.writeValueAsString(jsonNode);
            } catch (ClientException e) {
                System.err.println("短信发送异常:");
                System.err.println("错误信息: " + e.getMessage());
                System.err.println("错误码: " + e.getErrCode());
                System.err.println("请求 ID: " + e.getRequestId());
                e.printStackTrace();
            }
        }
        else{
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode jsonNode = objectMapper.createObjectNode();
            jsonNode.put("fh",1004);
            return objectMapper.writeValueAsString(jsonNode);
        }
        return "success";
    }


    @PostMapping("pay")
    public String pay() throws JsonProcessingException, AlipayApiException {
        System.out.println("接到付款请求");
        String str = textpay.pay();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonNode = objectMapper.createObjectNode();
        jsonNode.put("fh",1005);
        jsonNode.put("ma",str);
        return objectMapper.writeValueAsString(jsonNode);
    }
    @PostMapping("register")
    public  String doregister(@RequestBody newuser us) throws JsonProcessingException {
        String name = us.getusername();
        String pwd = us.getpwd();
        String phone = us.getphone();
        int ok = userService.register(name,pwd,phone);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonNode = objectMapper.createObjectNode();
        jsonNode.put("fh",1003);
        return objectMapper.writeValueAsString(jsonNode);
    }
    // 测试登录  ---- http://localhost:8081/acc/doLogin?name=zhang&pwd=123456
    @PostMapping("doLogin")
    public String doLogin(@RequestBody User user) {
        String name = user.getZh();//手机号
        String pwd = user.getMm();
      newuser loguser = userService.findname(name);
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        if(pwd.equals(loguser.getpwd())) {
            StpUtil.login(user.getZh(),"PC");
           //return SaResult.ok("登录成功");
            ObjectMapper objectMapper = new ObjectMapper();
            // 假设 fh 是一个变量
            int fh = 1001; // 示例值
            // 创建一个 ObjectNode 来表示 JSON 对象
            ObjectNode jsonNode = objectMapper.createObjectNode();
            jsonNode.put("fh", fh);

            try {
                //System.out.println(jsonNode);
                return objectMapper.writeValueAsString(jsonNode);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        }
        String str = "登录失败";
        return str;
    }


    // 测试注销  ---- http://localhost:8081/acc/logout
    @RequestMapping("logout")
    public SaResult logout() {
        StpUtil.logout();

        return SaResult.ok();
    }

}
