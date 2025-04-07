package org.aileen.datasourcesettest.service;

import org.aileen.datasourcesettest.client.MysqlTestService;
import org.aileen.datasourcesettest.mapper.dbmy.MysqlTestMapper;
import org.aileen.datasourcesettest.model.MysqlTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MysqlTestServiceImpl implements MysqlTestService {

    @Autowired
    private MysqlTestMapper mysqlTestMapper;

    @Override
    public MysqlTest getMysqlTest(Long id) {
        return mysqlTestMapper.selectByPrimaryKey(id);
    }

    @Override
    public MysqlTest insertMysqlTest(MysqlTest mysqlTest) {
        mysqlTestMapper.insert(mysqlTest);
        return mysqlTest;
    }

    @Override
    public MysqlTest updateMysqlTest(MysqlTest mysqlTest) {
        mysqlTestMapper.updateByPrimaryKey(mysqlTest);
        return null;
    }

    @Override
    public void deleteMysqlTest(Long id) {
        mysqlTestMapper.deleteByPrimaryKey(id);
    }
}
