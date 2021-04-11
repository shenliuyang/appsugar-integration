package org.appsugar.integration.data.jpa.entity;

import lombok.Data;

/**
 * @author shenliuyang
 * @version 1.0.0
 * @package org.appsugar.integration.data.jpa.entity
 * @className PersonPetStatDTO
 * @date 2021-04-11  20:43
 */
@Data
public class PersonPetStatDTO {
    private Long id;
    private String name;
    private Long petSize;
}
