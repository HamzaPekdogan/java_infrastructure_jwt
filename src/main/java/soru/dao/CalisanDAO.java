package soru.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import soru.entity.Kullanici;

import java.util.List;

public interface CalisanDAO extends JpaRepository<Kullanici, Long>{
    Kullanici findByKullaniciAdi(String kulAdi);
    Kullanici findById(Long id);
    List<Kullanici> findAllByDurum(Integer durum);
}
