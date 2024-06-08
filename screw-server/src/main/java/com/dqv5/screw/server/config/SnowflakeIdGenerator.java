package com.dqv5.screw.server.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import javax.annotation.PostConstruct;
import java.io.Serializable;

@Slf4j
public class SnowflakeIdGenerator implements IdentifierGenerator {
    /**
     * 终端ID
     */
    public static long WORKER_ID = 1;
    /**
     * 数据中心id
     */
    public static long DATACENTER_ID = 1;

    @PostConstruct
    public void init() {
//        WORKER_ID = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
//        log.info("当前机器的workId:{}", WORKER_ID);
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        Snowflake snowflake = IdUtil.getSnowflake(WORKER_ID, DATACENTER_ID);
        return snowflake.nextIdStr();
    }

}
