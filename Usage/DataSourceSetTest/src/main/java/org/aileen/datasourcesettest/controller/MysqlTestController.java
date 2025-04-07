package org.aileen.datasourcesettest.controller;

import org.aileen.datasourcesettest.client.MysqlTestService;
import org.aileen.datasourcesettest.model.MysqlTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mysql")
public class MysqlTestController {

    @Autowired
    private MysqlTestService mysqlTestService;

    public MysqlTest getMysqlTest(Long id){
        return mysqlTestService.getMysqlTest(id);
    }

    public MysqlTest insertMysqlTest(MysqlTest mysqlTest){
        return mysqlTestService.insertMysqlTest(mysqlTest);
    }

    public MysqlTest updateMysqlTest(MysqlTest mysqlTest){
        return mysqlTestService.updateMysqlTest(mysqlTest);
    }

    public void deleteMysqlTest(Long id){
        mysqlTestService.deleteMysqlTest(id);
    }
}
