package uz.istart.kafedra.core.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.istart.kafedra.core.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {
    Optional<UserEntity> findByUsernameIgnoreCase(String username);
    Optional<UserEntity> findById(Long id);
    List<UserEntity> findByDepartmentId(Long departmentId);
    Page<UserEntity> findByDepartmentId(Long departmentId, Pageable pageable);

    List<UserEntity> findAll();

    @Query("SELECT u FROM UserEntity u WHERE LOWER(u.username) = LOWER(:username) and u.password = :password")
    Optional<UserEntity> findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
