package org.aileen.mod.common.unit;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @author qaz22
 * {@code @date} 2024/11/8
 * {@code @project} tutor-selenium
 */
public class TutorTransactionManager {

    public static TransactionStatus getTransaction(DataSourceTransactionManager manager){
        // 定义一个事务属性对象
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionDefinition.setTimeout(10); // 设置超时时间为10秒
        transactionDefinition.setReadOnly(false); // 事务不是只读的
        return manager.getTransaction(transactionDefinition);
    }
    // 提交事务
    public static void commit(TransactionStatus status, DataSourceTransactionManager manager){
        manager.commit(status);
    }

    // 回滚事务
    public static void rollback(TransactionStatus status, DataSourceTransactionManager manager){
        manager.rollback(status);
    }

}
