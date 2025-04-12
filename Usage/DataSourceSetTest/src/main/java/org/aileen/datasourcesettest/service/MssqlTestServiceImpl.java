package org.aileen.datasourcesettest.service;

import org.aileen.datasourcesettest.client.MssqlTestService;
import org.aileen.datasourcesettest.mapper.dbms.MssqlTestMapper;
import org.aileen.datasourcesettest.model.MssqlTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class MssqlTestServiceImpl implements MssqlTestService {

    @Autowired
    private MssqlTestMapper mssqlTestMapper;

    @Override
    public MssqlTest getMssqlTest(Long id) {
        return mssqlTestMapper.selectByPrimaryKey(id);
    }

    @Override
    public MssqlTest insertMssqlTest(MssqlTest mssqlTest) {
        mssqlTestMapper.insert(mssqlTest);
        return mssqlTest;
    }

    @Override
    public MssqlTest updateMssqlTest(MssqlTest mssqlTest) {
        mssqlTestMapper.updateByPrimaryKeySelective(mssqlTest);
        return null;
    }

    @Override
    public void deleteMssqlTest(Long id) {
        mssqlTestMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<MssqlTest> getMssqlTestList() {
        return mssqlTestMapper.getMssqlTestList();
    }
}
