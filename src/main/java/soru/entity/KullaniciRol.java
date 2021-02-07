package soru.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class KullaniciRol extends BaseEntity {
    private Long kulId;
    private Long roleId;
}
