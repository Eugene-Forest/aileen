package org.aileen.datasourcesettest.controller;

import org.aileen.datasourcesettest.client.MssqlTestService;
import org.aileen.datasourcesettest.model.MssqlTest;
import org.aileen.datasourcesettest.vo.MssqlTestVo;
import org.aileen.mod.datasource.exceptions.DataSourceModExceptionFactory;
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
    public MssqlTestVo getMssqlTest(Long id) {
        DataSourceLoader.setDataSource("TuTor_Ali");
        MssqlTest mssqlTest = mssqlTestService.getMssqlTest(id);
        MssqlTestVo mssqlTestVo = new MssqlTestVo();
        mssqlTestVo.setId(mssqlTest.getId());
        mssqlTestVo.setName(mssqlTest.getName());
        mssqlTestVo.setAge(mssqlTest.getAge());
        return mssqlTestVo;
    }

    @PostMapping("/insert")
    public MssqlTest insertMssqlTest(@RequestBody MssqlTestVo mssqlTest) {
        DataSourceLoader.setDataSource("TuTor_Ali");
        MssqlTest mssqlTest1 = new MssqlTest();
        mssqlTest1.setName(mssqlTest.getName());
        mssqlTest1.setAge(mssqlTest.getAge());
        mssqlTest1.setId(mssqlTest.getId());
        if(mssqlTest.getName() == null || mssqlTest.getAge() == null){
            DataSourceModExceptionFactory.raiseException("内容为空");
        }
        return mssqlTestService.insertMssqlTest(mssqlTest1);
    }

    @PostMapping("/update")
    public MssqlTest updateMssqlTest(@RequestBody MssqlTestVo mssqlTest) {
        DataSourceLoader.setDataSource("TuTor_Ali");
        MssqlTest mssqlTest1 = new MssqlTest();
        mssqlTest1.setName(mssqlTest.getName());
        mssqlTest1.setAge(mssqlTest.getAge());
        mssqlTest1.setId(mssqlTest.getId());
        if(mssqlTest.getId() == null){
            DataSourceModExceptionFactory.raiseException("id为空");
        }
        return mssqlTestService.updateMssqlTest(mssqlTest1);
    }

    @GetMapping("/delete")
    public void deleteMssqlTest(Long id) {
        DataSourceLoader.setDataSource("TuTor_Ali");
        mssqlTestService.deleteMssqlTest(id);
    }

}
