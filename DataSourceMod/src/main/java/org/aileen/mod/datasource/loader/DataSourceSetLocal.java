package org.aileen.mod.datasource.loader;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
class DataSourceSetLocal {
    @JsonProperty("datasource-set")
    private DataSourceSet dataSourceSet;
}
