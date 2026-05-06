package org.aileen.mod.datasource.loader;

import lombok.extern.slf4j.Slf4j;
import org.aileen.mod.datasource.model.AccountSet;
import org.aileen.mod.datasource.model.DataSourceData;
import org.aileen.mod.datasource.model.DataSourceSet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 账套资源载入
 */
@Slf4j
public class AccountSetDataLoader {

    private Map<String, Map<String, DataSourceData>> allDataSourceData;

    private DataSourceSet dataSourceSet;

    public AccountSetDataLoader(DataSourceSet dataSourceSet) {
        this.dataSourceSet = dataSourceSet;
        allDataSourceData = new HashMap<>();
    }

    /** 获取账套配置 */
    public List<AccountSet> getAccountSets() {
        return dataSourceSet.getAccountSets();
    }

    public String getDefaultAccountSetName(){
        for(AccountSet accountSet : getAccountSets()){
            if(accountSet.getIsDefault()){
                return accountSet.getAccountSetName();
            }
        }
        return null;
    }

    public Map<String, DataSourceData> getDataSourceDataMap(String dbId){
        if(allDataSourceData.containsKey(dbId)){
            return allDataSourceData.get(dbId);
        }
        Map<String, DataSourceData> dataSourceDataMap = new HashMap<>();
        for(AccountSet accountSet : getAccountSets()){
            for (DataSourceData data : accountSet.getData()){
                if(data.getDBId().equals(dbId)){
                    dataSourceDataMap.put(accountSet.getAccountSetName(), data);
                    break;
                }
            }
        }
        allDataSourceData.put(dbId, dataSourceDataMap);
        return dataSourceDataMap;
    }
}



