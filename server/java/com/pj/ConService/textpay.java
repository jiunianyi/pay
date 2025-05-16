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
        String privateKey  = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCSVuNOthSwNHeRk6yOpZUxVP07K0VBq/2w+U0iTUWkqgQ16ACqhvZjnQALhhJ6KyYTe4ywIjbJBYVhhQWt63Xz63vhzogrnFaATQBC9k47q+upXsMiAyB+SLR0fK0fy50r6z4VedEr4nOPmb4XqOom2iMudGpMT2mPem8aN2EJaGsAF8hqlLT9qEIAuSQBKy5RNDetIH67tINWkmHqtKwuJoWP6rELwOWDcs51s3fGis3c0EaFEfj/kRpANqMfYvEXxmmIhH/yBacCJBnLzevF7oNaXrin3wnuh87tl+QILRfe4jLkdjhtCHmUtQqh1ogl6t06NzMOgXkA6bVi6HJhAgMBAAECggEAP891ZKAO21q1fJwZFUZsjMtTdJaEp4M4pw++K6PoJ0ofdeYJTFJ3Y65VE7FNo0nnW313NmJk4zjuFTzs4g6XK1pWdXsac3HDYkSxRYGjUW/A3eS9T7pvU8GeNmEbGI8vpLP6KWEp7WncHBTdB/IW0AQ0bRBEPawvCmpHwfW98VyWHBzcxL3VrH4Eco++g9yrYPNIK/sGgXArOYkvUoBFOdL1EErQXBB1moh9qX1dGJZ/gy528CVxvzbqTkugK+GHJvDjC/uMNnuq/ZAWBLd8cRqJjx2UZQgxV8U5B2O55qdYZSplFUWGG/Td6TeJgSOfDsRJ3WFhcnz1xeWBTlyzRQKBgQDVQ2NHiID1q2sOrcHEhUS/8qwW9QoOid+ikAaSJbMLHXDStD7URapBoS2VyfaPi5R7+RqMP0bMtAZ8lnjn4T8WjAFolortqoJjFgFW4LFT8jRpn6rdeUIfqLCj0wMhznd44wPgh+IP0PoxylIukNKn0xjmxDfgt3nBvrScxpoA3wKBgQCvqj4MriG8hfnKSIvPgGL1KJaTjXZnVvmzEHKz8Gde2FMq99ZRlN+YPPsXD0XYwoWo9SbcPu2eU+cNKh9asfIyoyHfv0HDo5iAR/K/uNnzgm5VqcLiMN/Gsy4MHJub8YM/kupZtdw3JQA1TMvhNh59TfGbSWs7+zjpiJuahju0vwKBgCYa+lxHubMrw99FkL/KiDQ76h/X/MoIPJtRXLs0XT95LTWY2zkWkYtUf06qXZHcgNV99w5kKARfjmEoicX0607eCUpky8IXm2CVB/w7/bNgLZlJ3D/icLwzagx7oa2itJo3BfRSk0P8NRg8bF7BpcIy59oj6VAPuIOzzZXNrqrFAoGANsNdCrjyfYJK6BwLsgm1T/ZyUtekFP1RZNh42c3VfAT2kIuo/c/qkNxZ7sttG/EwmAatMbZQf65qdnkuQyj8d3rR4Z4PU31Z2owTil+HWtnLqdr9jWRtLtXHQjFc9ZWqNBSKCKOgXnSjhvpFCRXFFY1KB/4wdwePq4ZR9C9qx6MCgYEAw79TKV9voGRxNq5ZciKxgaDtOgTb6661R3K9SH24Nw+v2AHfc111nqYHVYsIYE/x4ODbUZaCFJVGg60wB201nGOVsN0TgW4w0M2DU+l6tFk65X34PjTwLenj4IgLY+07oxyCTm3GMyd7RiiItaWzUvHTFxyv2gUwwBxcb691neA=";
        String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmOuMQpr/LRHb2DuZH8hNu3ySRj2m3N9Z0Z2btXYp2MhataHBPSBj07YZV9IdICEyKx61cmkcMCks3jHJefObriNUWAVjL+Kn9n+WZlQ74zY6O06Jr7vYfR95ujzrQomx3CuWnCt1zmBNpIEJlYrrZuikG3LnktPaIeRe1NoTxL3ji1pgUWsO1Fre4dpGP6ligA24uzGfWntEpIKpFiWxJlGl1Gx/6gFyhrV3Jusqf9u+rpeLTkNw8bMpH8m1upInlzVyO7o3fVjQfKEeAU16+89OV48mJlUz3ucULlJhlGLv7bgCNoSFjT9yVrl7YL0w1/jco5oifKWVqNwfAIZexwIDAQAB";
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
