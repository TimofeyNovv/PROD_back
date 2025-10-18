package com.example.prodBack8.model.entity.group;

import com.example.prodBack8.model.entity.BaseEntity;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "group_std")
public class GroupEntity extends BaseEntity {

    private String name;
    private Integer GPUcount;
    private Integer distribution;

    @Embedded
    private UsageLimit usageLimit;
}
