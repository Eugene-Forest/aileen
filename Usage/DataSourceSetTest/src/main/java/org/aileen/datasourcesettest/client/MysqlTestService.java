package org.aileen.datasourcesettest.client;

import org.aileen.datasourcesettest.model.MysqlTest;

public interface MysqlTestService {
    MysqlTest getMysqlTest(Long id);
    MysqlTest insertMysqlTest(MysqlTest mysqlTest);
    MysqlTest updateMysqlTest(MysqlTest mysqlTest);
    void deleteMysqlTest(Long id);
}
