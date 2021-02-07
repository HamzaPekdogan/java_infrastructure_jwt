package soru.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class RolUrl extends BaseEntity {
    private String urlAdi;
    private Long rolId;
}
