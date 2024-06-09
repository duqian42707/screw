/*
 * screw-server - 简洁好用的数据库表结构文档生成工具
 * Copyright © 2020 SanLi (qinggang.zuo@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
    public static long WORKER_ID     = 1;
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
    public Serializable generate(SharedSessionContractImplementor session,
                                 Object object) throws HibernateException {
        Snowflake snowflake = IdUtil.getSnowflake(WORKER_ID, DATACENTER_ID);
        return snowflake.nextIdStr();
    }

}
