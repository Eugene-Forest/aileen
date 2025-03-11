package org.aileen.mod.datasource;

import org.aileen.mod.datasource.loader.AccountSetDataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/example")
public class IndexController {


    @Autowired
    private AccountSetDataLoader accountSetDataLoader;

    @GetMapping("/datasource")
    public Object getDataSourceSet() {
        return accountSetDataLoader.getAccountSets();
    }

}
