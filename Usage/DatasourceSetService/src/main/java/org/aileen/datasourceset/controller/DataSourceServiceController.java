package org.aileen.datasourceset.controller;

import org.aileen.datasourceset.dto.Account;
import org.aileen.datasourceset.dto.DataSourceDto;
import org.aileen.datasourceset.service.DataSourceService;
import org.aileen.mod.auth.anno.EncryptRequest;
import org.aileen.mod.auth.entity.WebResult;
import org.aileen.mod.auth.enums.RequestEncryptType;
import org.aileen.mod.auth.units.CryptoUnits;
import org.aileen.mod.auth.units.SignKeyUnits;
import org.aileen.mod.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/11/23
 */
@RequestMapping("/datasource")
@RestController
@EncryptRequest
public class DataSourceServiceController {

    @Autowired
    private DataSourceService dataSourceService;

    @Autowired
    private RedisUtil redisUtil;

    @EncryptRequest(encryptType = RequestEncryptType.RSA)
    @PostMapping("/login")
    public WebResult login(@RequestBody Account account) {
        if (account.getPassword() == null || account.getPassword().isEmpty()) {
            return WebResult.error();
        }
        if (account.getName() == null || account.getName().isEmpty()) {
            return WebResult.error();
        }
        if (account.getPassword().equals("admin") && account.getName().equals("admin")) {
            String password = CryptoUnits.generatePassword();
            //how to Save password
            redisUtil.set("password", password);
            redisUtil.expire("password", 1, TimeUnit.MINUTES);

            String enCodePassword = SignKeyUnits.defaultEncryptMessage(password);
            return WebResult.success(enCodePassword);
        }
        return WebResult.error();
    }

    @GetMapping("/getDataSourceSets")
    public List<DataSourceDto> getDataSourceSets() {
        return dataSourceService.getDataSources();
    }
}
