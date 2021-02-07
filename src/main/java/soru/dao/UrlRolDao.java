package soru.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import soru.entity.Rol;
import soru.entity.RolUrl;

import java.util.List;

public interface UrlRolDao extends JpaRepository<RolUrl, Long>{
    List<RolUrl> findAllByRolId(Long rolId);
}
