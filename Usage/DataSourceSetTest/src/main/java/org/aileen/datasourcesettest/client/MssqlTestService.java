package org.aileen.datasourcesettest.client;

import org.aileen.datasourcesettest.model.MssqlTest;

public interface MssqlTestService {

    MssqlTest getMssqlTest(Long id);

    MssqlTest insertMssqlTest(MssqlTest mssqlTest);

    MssqlTest updateMssqlTest(MssqlTest mssqlTest);

    void deleteMssqlTest(Long id);
}
