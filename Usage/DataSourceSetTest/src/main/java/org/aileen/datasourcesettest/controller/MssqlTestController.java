package org.aileen.datasourcesettest.controller;

import org.aileen.datasourcesettest.client.MssqlTestService;
import org.aileen.datasourcesettest.model.MssqlTest;
import org.aileen.mod.datasource.loader.DataSourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mssql")
public class MssqlTestController {

    @Autowired
    private MssqlTestService mssqlTestService;

    @GetMapping("/list")
    public List<MssqlTest> getMssqlTestList() {
        DataSourceLoader.setDataSource("TuTor_Ali");
        return mssqlTestService.getMssqlTestList();
    }

    @GetMapping("/get")
    public MssqlTest getMssqlTest(Long id) {
        DataSourceLoader.setDataSource("TuTor_Ali");
        return mssqlTestService.getMssqlTest(id);
    }

    @PostMapping("/insert")
    public MssqlTest insertMssqlTest(@RequestBody MssqlTest mssqlTest) {
        DataSourceLoader.setDataSource("TuTor_Ali");
        return mssqlTestService.insertMssqlTest(mssqlTest);
    }

    @PostMapping("/update")
    public MssqlTest updateMssqlTest(@RequestBody MssqlTest mssqlTest) {
        DataSourceLoader.setDataSource("TuTor_Ali");
        return mssqlTestService.updateMssqlTest(mssqlTest);
    }

    @GetMapping("/delete")
    public void deleteMssqlTest(Long id) {
        DataSourceLoader.setDataSource("TuTor_Ali");
        mssqlTestService.deleteMssqlTest(id);
    }

}
