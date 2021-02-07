package soru.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Rol extends BaseEntity {
    private String rolAdi;
}
