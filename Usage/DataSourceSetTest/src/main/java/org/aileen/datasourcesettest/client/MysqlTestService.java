package org.aileen.datasourcesettest.client;

import org.aileen.datasourcesettest.model.MysqlTest;

import java.util.List;

public interface MysqlTestService {
    MysqlTest getMysqlTest(Long id);
    MysqlTest insertMysqlTest(MysqlTest mysqlTest);
    MysqlTest updateMysqlTest(MysqlTest mysqlTest);
    void deleteMysqlTest(Long id);
    List<MysqlTest> getMysqlTestList();
}
