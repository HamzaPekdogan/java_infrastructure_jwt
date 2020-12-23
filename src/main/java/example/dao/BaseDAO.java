package example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import example.entity.BaseEntity;

import java.io.Serializable;

public interface BaseDAO<T extends BaseEntity> extends JpaRepository<T, Serializable> {
}
