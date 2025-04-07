package org.aileen.datasourcesettest.controller;

import org.aileen.datasourcesettest.client.MssqlTestService;
import org.aileen.datasourcesettest.model.MssqlTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/mssql")
public class MssqlTestController {

    @Autowired
    private MssqlTestService mssqlTestService;

    @GetMapping("/get")
    public MssqlTest getMssqlTest(Long id) {
        return mssqlTestService.getMssqlTest(id);
    }

    @PostMapping("/insert")
    public MssqlTest insertMssqlTest(MssqlTest mssqlTest) {
        return mssqlTestService.insertMssqlTest(mssqlTest);
    }

    @PostMapping("/update")
    public MssqlTest updateMssqlTest(MssqlTest mssqlTest) {
        return mssqlTestService.updateMssqlTest(mssqlTest);
    }

    @GetMapping("/delete")
    public void deleteMssqlTest(Long id) {
        mssqlTestService.deleteMssqlTest(id);
    }

}
