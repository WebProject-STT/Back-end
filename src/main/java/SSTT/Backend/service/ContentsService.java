package SSTT.Backend.service;

import SSTT.Backend.domain.Contents;
import SSTT.Backend.domain.Member;
import SSTT.Backend.mapping.ContentsListMapping;
import SSTT.Backend.mapping.ContentsMapping;
import SSTT.Backend.repository.ContentsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ContentsService {

    private final ContentsRepository contentsRepository;

    @Transactional
    public List<ContentsListMapping> findAll(Member member) {
        List<ContentsListMapping> findContents = contentsRepository.findAllByMemberId(member.getId());
        return findContents;
    }

    @Transactional
    public List<ContentsListMapping> findCategory(Member member, Long categoryId) {
        return contentsRepository.findAllByMemberIdAndCategoryId(member.getId(), categoryId);
    }

    @Transactional
    public List<ContentsMapping> findContents(Member member, Long contentsId) {
        return contentsRepository.findByIdAndAndMemberId(contentsId, member.getId());
    }

    @Transactional
    public void delete(Long contentsId) {
        contentsRepository.deleteById(contentsId);
    }

    public Boolean deleteContents(List<Long> ids) {
        for (Long id : ids) contentsRepository.deleteById(id);
        return true;
    }
}
