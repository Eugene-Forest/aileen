import org.aileen.mod.datasource.DataSourceApp;
import org.aileen.mod.datasource.loader.AccountSetDataLoader;
import org.aileen.mod.datasource.model.AccountSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = DataSourceApp.class)
public class DataSourceTest {

    @Autowired
    AccountSetDataLoader accountSetDataLoader;

    @Test
    public void test() {
        List<AccountSet> accountSets = accountSetDataLoader.getAccountSets();

        System.out.println(accountSets);
    }
}
