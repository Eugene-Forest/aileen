package org.aileen.mod.datasource.starter;

import org.aileen.mod.datasource.loader.DataSourceSetDataLoader;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSourceDataInitRunner implements ApplicationRunner {

//TODO: 需要组织一下所有的组件的创建顺序，这个组件应当是·最后
    @Override
    public void run(ApplicationArguments args) throws Exception {
        DataSourceSetDataLoader.load();
    }
}
