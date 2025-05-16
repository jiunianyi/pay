package com.pj.cases.use;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class sendyzm {
    @Bean
    public static com.aliyun.dysmsapi20170525.Client createClient() throws Exception {
        try {
            String accessKeyId = System.getenv("ALIBABA_CLOUD_ACCESS_KEY_ID");
            String accessKeySecret = System.getenv("ALIBABA_CLOUD_ACCESS_KEY_SECRET");
            com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                    .setAccessKeyId(accessKeyId)
                    .setAccessKeySecret(accessKeySecret);
            config.endpoint = "dysmsapi.aliyuncs.com";
            //System.out.println("已经初始化");
            return new com.aliyun.dysmsapi20170525.Client(config);
        } catch (Exception e) {
            e.printStackTrace();  // 打印详细的错误信息
            throw e; // 重新抛出异常，便于调用者处理
        }
    }
}
