package soru.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import soru.entity.KullaniciRol;

import java.util.List;

public interface KulRolDao extends JpaRepository<KullaniciRol, Long>{
    List<KullaniciRol> findAllByKulId(Long kulId);
}
