package org.aileen.datasourcesettest.controller;

import org.aileen.datasourcesettest.client.MssqlTestService;
import org.aileen.datasourcesettest.model.MssqlTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mssql")
public class MssqlTestController {

    @Autowired
    private MssqlTestService mssqlTestService;

    public MssqlTest getMssqlTest(Long id) {
        return mssqlTestService.getMssqlTest(id);
    }

    public MssqlTest insertMssqlTest(MssqlTest mssqlTest) {
        return mssqlTestService.insertMssqlTest(mssqlTest);
    }

    public MssqlTest updateMssqlTest(MssqlTest mssqlTest) {
        return mssqlTestService.updateMssqlTest(mssqlTest);
    }

    public void deleteMssqlTest(Long id) {
        mssqlTestService.deleteMssqlTest(id);
    }

}
