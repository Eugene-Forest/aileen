package org.aileen.mod.auth.mapper;

import org.aileen.mod.auth.entity.AuthAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * 账号 Mapper
 *
 * @author Eugene-Forest
 */
@Mapper
public interface AccountMapper {

    /**
     * 根据用户名查询账号
     *
     * @param username 用户名
     * @return 账号信息（未删除的）
     */
    AuthAccount selectByUsername(@Param("username") String username);

    /**
     * 更新最后登录信息
     *
     * @param id        账号ID
     * @param loginIp   登录IP
     * @param loginTime 登录时间
     * @return 影响行数
     */
    int updateLoginInfo(@Param("id") Long id,
                        @Param("loginIp") String loginIp,
                        @Param("loginTime") LocalDateTime loginTime);
}
