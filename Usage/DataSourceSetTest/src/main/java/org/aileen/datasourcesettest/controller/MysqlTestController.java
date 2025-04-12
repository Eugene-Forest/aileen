package org.aileen.datasourcesettest.controller;

import org.aileen.datasourcesettest.client.MysqlTestService;
import org.aileen.datasourcesettest.model.MysqlTest;
import org.aileen.mod.datasource.loader.DataSourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mysql")
public class MysqlTestController {

    @Autowired
    private MysqlTestService mysqlTestService;

    @GetMapping("/list")
    public List<MysqlTest> getMysqlTestList(){
        DataSourceLoader.setDataSource("TuTor_Ali");
        return mysqlTestService.getMysqlTestList();
    }

    @GetMapping("/get")
    public MysqlTest getMysqlTest(Long id){
        DataSourceLoader.setDataSource("TuTor_Ali");
        return mysqlTestService.getMysqlTest(id);
    }

    @PostMapping("/insert")
    public MysqlTest insertMysqlTest(@RequestBody MysqlTest mysqlTest){
        DataSourceLoader.setDataSource("TuTor_Ali");
        return mysqlTestService.insertMysqlTest(mysqlTest);
    }

    @PostMapping("/update")
    public MysqlTest updateMysqlTest(@RequestBody MysqlTest mysqlTest){
        DataSourceLoader.setDataSource("TuTor_Ali");
        return mysqlTestService.updateMysqlTest(mysqlTest);
    }

    @GetMapping("/delete")
    public void deleteMysqlTest(Long id){
        DataSourceLoader.setDataSource("TuTor_Ali");
        mysqlTestService.deleteMysqlTest(id);
    }
}
