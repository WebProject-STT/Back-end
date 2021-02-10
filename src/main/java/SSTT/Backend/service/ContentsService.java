package SSTT.Backend.service;

import SSTT.Backend.dto.ContentsDto;
import SSTT.Backend.repository.ContentsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ContentsService {
    private ContentsRepository contentsRepository;

    public void savePost(ContentsDto contentsDto) {
        contentsDto.setDate(LocalDateTime.now());
        contentsRepository.save(contentsDto.toEntity());
    }
}