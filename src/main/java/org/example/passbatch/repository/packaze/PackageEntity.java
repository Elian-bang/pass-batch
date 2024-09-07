package org.example.passbatch.repository.packaze;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import org.example.passbatch.base.BaseEntity;

@Getter
@Setter
@ToString
@Entity
@Table(name = "package")
public class PackageEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 DB에 위임합니다. (AUTO_INCREMENT)
    private Integer packageSeq;

    private String packageName;
    private Integer count;
    private Integer period;

}
