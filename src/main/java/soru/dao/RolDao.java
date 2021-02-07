package soru.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import soru.entity.Rol;

public interface RolDao extends JpaRepository<Rol, Long>{
    Rol findById(Long id);
    Rol findByRolAdi(String rolAdi);
}
