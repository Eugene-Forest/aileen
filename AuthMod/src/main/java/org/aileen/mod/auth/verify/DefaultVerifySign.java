package org.aileen.mod.auth.verify;

import org.aileen.mod.auth.common.HttpRequestHelper;
import org.aileen.mod.auth.entity.AccountSignature;
import org.aileen.mod.auth.entity.SimpleSignature;
import org.aileen.mod.crypto.CryptoUnits;
import org.aileen.mod.crypto.SignKeyUnits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;

;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * 网络请求校验类
 * @author Eugene-Forest
 * {@code @date} 2024/11/27
 */
public class DefaultVerifySign {

    private static final Logger log = LoggerFactory.getLogger(DefaultVerifySign.class);

    public static boolean verifySimple(String dateTime, String sign){

        return false;
    }

    private static SimpleSignature getSimpleSign(HttpServletRequest request){
        String sign = request.getHeader("authorization");
//        String dataTime = request.getParameter("dataTime");
        SimpleSignature signature = new SimpleSignature();
        signature.setSign(sign);
        signature.setDateTime((new Date()).toString());
        signature.setVerify(true);
        return signature;
    }

    public static boolean defaultVerify(HttpServletRequest request){
        try {
            InputStream inputStream= request.getInputStream();
            String encodeBodyString = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            String bodyString = CryptoUnits.defaultDecrypt(encodeBodyString);
            SimpleSignature signature = getSimpleSign(request);
            return SignKeyUnits.defaultVerifyMessage(bodyString, signature.getSign());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return false;
    }

    public static AccountSignature accountVerify(HttpServletRequest request) {
        String sign = request.getParameter("sign");
        String app = request.getParameter("app");
        String dataTime = request.getParameter("dataTime");
        String bodyString = HttpRequestHelper.getBodyString(request);
        String dbName = request.getParameter("dbName");
        String user = request.getParameter("user");
        if (dbName == null || dbName.isEmpty()) {
            //如果没有指定数据库
            dbName = "default";
        }
        if (app == null || app.isEmpty()) {
            app = "default";
        }
        if (user == null || user.isEmpty()) {
            user = "default";
        }
        if (sign == null || dataTime == null) {
            AccountSignature accountSignature = new AccountSignature();
            accountSignature.setVerify(false);
            return accountSignature;
        }
        //签名认证
        AccountSignature accountSignature = new AccountSignature();
        accountSignature.setVerify(true);
        accountSignature.setDbName(dbName);
        accountSignature.setSign(sign);
        accountSignature.setDateTime(dataTime);
        accountSignature.setBodyString(bodyString);
        accountSignature.setUser(user);

        return accountSignature;
    }
}
