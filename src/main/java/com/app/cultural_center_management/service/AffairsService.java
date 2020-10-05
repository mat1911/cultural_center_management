package com.app.cultural_center_management.service;

import com.app.cultural_center_management.dto.securityDto.affairs.GetAllAffairsDto;
import com.app.cultural_center_management.entities.Affair;
import com.app.cultural_center_management.exceptions.ObjectNotFoundException;
import com.app.cultural_center_management.mapper.AffairsMapper;
import com.app.cultural_center_management.repositories.AffairRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AffairsService {

    private final String DEFAULT_PICTURE_URL = "https://www.dropbox.com/s/ru4od6mqj5alo34/default.jpg?raw=1";
    private final AffairRepository affairRepository;
    private final DropboxService dropboxService;

    public Page<GetAllAffairsDto> getAllAffairs(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Affair> page = affairRepository.findAllByIsAcceptedTrueOrderBySinceDateDesc(pageRequest);
        List<GetAllAffairsDto> resultContent = AffairsMapper.fromAffairListToGetAllAffairsList(page.getContent());

        return new PageImpl<>(resultContent, pageRequest, page.getTotalElements());
    }

    public Double updateAffairRate(int affairId, int affairRate) {
        Affair affair = affairRepository.findById((long) affairId)
                .orElseThrow(() -> new ObjectNotFoundException("Affair with given id does not exist!"));
        affair.setRate((affair.getRate() + affairRate) / 2);

        return affair.getRate();
    }
}
