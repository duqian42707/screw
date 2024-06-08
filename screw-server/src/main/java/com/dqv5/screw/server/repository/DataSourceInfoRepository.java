package com.dqv5.screw.server.repository;

import com.dqv5.screw.server.entity.DataSourceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author duq
 * @date 2024/6/7
 */
public interface DataSourceInfoRepository extends JpaRepository<DataSourceInfo, String> {
    List<DataSourceInfo> findAllByOrderByIdDesc();
}
