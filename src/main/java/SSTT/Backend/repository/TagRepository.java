package SSTT.Backend.repository;

import SSTT.Backend.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long>  {
}
