package soru.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Kullanici extends BaseEntity {
    private String okulAdi;
    private String ogretmenAdi;
    private String kullaniciAdi;
    private String sifre;
    private String yetki;
}
