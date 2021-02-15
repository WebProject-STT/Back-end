package SSTT.Backend.repository;

import SSTT.Backend.domain.Contents;
import SSTT.Backend.mapping.ContentsListMapping;
import SSTT.Backend.mapping.ContentsMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentsRepository extends JpaRepository<Contents, Long> {

    List<ContentsListMapping> findAllByMemberId(Long memberId);

    List<ContentsListMapping> findAllByMemberIdAndCategoryId(Long memberId, Long categoryId);

    List<ContentsMapping> findByIdAndAndMemberId(Long Id, Long memberId);
}
