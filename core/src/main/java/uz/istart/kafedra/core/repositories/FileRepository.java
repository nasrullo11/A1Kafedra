package uz.istart.kafedra.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.istart.kafedra.core.entities.FileEntity;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity,Long>, JpaSpecificationExecutor<FileEntity> {
    Optional<List<FileEntity>> findByOrgFileNameIgnoreCase(String orgFileName);
}
