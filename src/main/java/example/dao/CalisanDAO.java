package example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import example.entity.Kullanicilar;

import java.util.List;

public interface CalisanDAO extends JpaRepository<Kullanicilar, Long>{
    Kullanicilar findByKullaniciAdi(String kulAdi);
    Kullanicilar findById(Long id);
    List<Kullanicilar> findAllByDurum(Integer durum);
}
