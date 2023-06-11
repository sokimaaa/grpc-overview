package com.sokima.server.persistent.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@ToString
@Entity
@Data
@Table(name = "grpcServerTable")
public class GrpcServerEntity {

    @Id
    private String field1;

    private Integer field2;

    private Double field3;

}
