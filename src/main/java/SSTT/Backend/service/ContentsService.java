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

@Service
@AllArgsConstructor
public class ContentsService {

    private final ContentsRepository contentsRepository;

    @Transactional
    public List<ContentsListMapping> findAll(Member member) {
        System.out.println("============find-member=============");
        System.out.println("find All");
        System.out.println("====================================");
        List<ContentsListMapping> findContents = contentsRepository.findAllByMemberId(member.getId());
        return findContents;
    }

    @Transactional
    public List<ContentsListMapping> findCategory(Member member, Long categoryId) {
        System.out.println("============find-member=============");
        System.out.println("find Category");
        System.out.println("====================================");
        return contentsRepository.findAllByMemberIdAndCategoryId(member.getId(), categoryId);
    }

    @Transactional
    public List<ContentsMapping> findContents(Member member, Long contentsId) {
        return contentsRepository.findByIdAndAndMemberId(contentsId, member.getId());
    }

}
