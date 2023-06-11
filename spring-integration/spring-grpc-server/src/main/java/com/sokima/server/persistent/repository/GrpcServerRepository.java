package com.sokima.server.persistent.repository;

import com.sokima.server.persistent.entity.GrpcServerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrpcServerRepository extends JpaRepository<GrpcServerEntity, String> {
}
