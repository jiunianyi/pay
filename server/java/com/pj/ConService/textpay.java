package com.pj.ConService;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.diagnosis.DiagnosisUtils;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class textpay {
        public String pay() throws AlipayApiException {
            AlipayClient alipayClient = new DefaultAlipayClient(getAlipayConfig());

            // 构造请求参数以调用接口
            AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
            AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();

            // 设置商户订单号
            model.setOutTradeNo("2015033320011001");

            // 设置订单总金额
            model.setTotalAmount("19888.88");

            // 设置订单标题
            model.setSubject("小米19 1T,64G");


            // 设置卖家支付宝用户ID


            request.setBizModel(model);
            // 第三方代调用模式下请设置app_auth_token
            // request.putOtherTextParam("app_auth_token", "<-- 请填写应用授权令牌 -->");

            AlipayTradePrecreateResponse response = alipayClient.execute(request);
            String body  = response.getBody();
            JSONObject jsonObject = JSONObject.parseObject(body);
            String qurl = jsonObject.getJSONObject("alipay_trade_precreate_response").get("qr_code").toString();
            System.out.println(response.getBody());
            if (response.isSuccess()) {
                System.out.println("调用成功");
                return qurl;
            } else {
                System.out.println("调用失败");
                // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
                String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
                System.out.println(diagnosisUrl);
            }
            return "s";
        }
    private static AlipayConfig getAlipayConfig() {
        String privateKey  = //使用自己的密钥
        String alipayPublicKey = 
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl("https://openapi-sandbox.dl.alipaydev.com/gateway.do");
        alipayConfig.setAppId("9021000143680943");
        alipayConfig.setPrivateKey(privateKey);
        alipayConfig.setFormat("json");
        alipayConfig.setAlipayPublicKey(alipayPublicKey);
        alipayConfig.setCharset("UTF-8");
        alipayConfig.setSignType("RSA2");
        // alipayConfig.notify();
        return alipayConfig;
    }
}
