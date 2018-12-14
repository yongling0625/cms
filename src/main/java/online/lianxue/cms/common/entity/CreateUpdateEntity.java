package online.lianxue.cms.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CreateUpdateEntity implements Serializable {

    private Long id;

    private String createBy;

    private LocalDateTime createTime;

    private String lastUpdateBy;

    private LocalDateTime lastUpdateTime;

}
