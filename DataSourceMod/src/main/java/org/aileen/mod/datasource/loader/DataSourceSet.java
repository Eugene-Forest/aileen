package org.aileen.mod.datasource.loader;

import lombok.Data;
import org.aileen.mod.datasource.model.AccountSet;

import java.util.List;

@Data
class DataSourceSet {
    private List<AccountSet> accountSets;
}
