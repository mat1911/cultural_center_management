package com.app.cultural_center_management.service;

import com.app.cultural_center_management.dto.competitionsDto.*;
import com.app.cultural_center_management.entity.Competition;
import com.app.cultural_center_management.entity.Contestant;
import com.app.cultural_center_management.entity.User;
import com.app.cultural_center_management.exception.NotAllowedOperationException;
import com.app.cultural_center_management.exception.ObjectNotFoundException;
import com.app.cultural_center_management.mapper.CompetitionMapper;
import com.app.cultural_center_management.repository.CompetitionRepository;
import com.app.cultural_center_management.repository.ContestantRepository;
import com.app.cultural_center_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class CompetitionsService {

    private final String DEFAULT_PICTURE_URL = "https://www.dropbox.com/s/ru4od6mqj5alo34/default.jpg?raw=1";
    private final UserRepository userRepository;
    private final CompetitionRepository competitionRepository;
    private final ContestantRepository contestantRepository;
    private final DropboxService dropboxService;

    public Long createCompetition(UpdateCompetitionDto updateCompetitionDto){
        Competition competitionToCreate = CompetitionMapper.fromUpdateCompetitionDtoToCompetition(updateCompetitionDto);
        competitionToCreate.setPictureUrl(DEFAULT_PICTURE_URL);
        competitionToCreate.setSinceDate(LocalDate.now());
        if (Objects.nonNull(updateCompetitionDto.getPicture())){
            competitionToCreate.setPictureUrl(dropboxService.uploadFile(updateCompetitionDto.getPicture()));
        }
        return competitionRepository.save(competitionToCreate).getId();
    }

    public Long createContestant(Long competitionId, Long userId, UpdateContestantsDto updateContestantsDto){

        if (contestantRepository.existsByCompetition_IdAndUser_Id(competitionId, userId)){
            throw new NotAllowedOperationException("User already takes part in the competition!");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("User with given id does not exist!"));
        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new ObjectNotFoundException("Competition with given id does not exist!"));

        Contestant contestantToCreate = new Contestant();
        contestantToCreate.setCompetition(competition);
        contestantToCreate.setUserComment(updateContestantsDto.getUserComment());
        contestantToCreate.setUser(user);
        contestantToCreate.setVotesNumber(0L);
        contestantToCreate.setIsAccepted(false);

        if(Objects.nonNull(updateContestantsDto.getUserFile())){
            contestantToCreate.setResultUrl(dropboxService.uploadFile(updateContestantsDto.getUserFile()));
        }
        return contestantRepository.save(contestantToCreate).getId();
    }

    public Page<GetAllCompetitionsDto> getAllCompetitions(int pageNumber, int pageSize){
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Competition> page = competitionRepository.findAllByOrderBySinceDateDesc(pageRequest);
        List<GetAllCompetitionsDto> resultContent = CompetitionMapper.fromCompetitionListToGetAllCompetitionsDtoList(page.getContent());

        return new PageImpl<>(resultContent, pageRequest, page.getTotalElements());
    }

    public GetCompetitionDto getCompetitionById(Long competitionId){
        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new ObjectNotFoundException("Competition with given id does not exist!"));
        return CompetitionMapper.fromCompetitionToGetCompetitionDto(competition);
    }

    public Page<GetCompetitionContestantsDto> getAcceptedCompetitionContestants(Long competitionId, int pageNumber, int pageSize, String keyword){
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Contestant> page;
        if(Objects.nonNull(keyword) && !keyword.isEmpty()){
            page = contestantRepository.findAllByCompetitionIdAndFiltered(pageRequest, keyword, competitionId);
        }else {
            page = contestantRepository.findAllByIsAcceptedTrueAndCompetition_Id(pageRequest, competitionId);
        }
        List<GetCompetitionContestantsDto> resultContent =
                CompetitionMapper.fromContestantListToGetCompetitionContestantsDtoList(page.getContent());
        return new PageImpl<>(resultContent, pageRequest, page.getTotalElements());
    }

    public Page<GetCompetitionContestantsDto> getNotAcceptedCompetitionContestants(Long competitionId, int pageNumber, int pageSize){
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Contestant> page = contestantRepository.findAllByIsAcceptedFalseAndCompetition_Id(pageRequest, competitionId);
        List<GetCompetitionContestantsDto> resultContent =
                CompetitionMapper.fromContestantListToGetCompetitionContestantsDtoList(page.getContent());
        return new PageImpl<>(resultContent, pageRequest, page.getTotalElements());
    }

    public GetContestantResultDto getContestantResult(Long competitionId, Long userId){
        Contestant contestant = contestantRepository.findByCompetition_IdAndUser_Id(competitionId, userId)
                .orElseThrow(() -> new ObjectNotFoundException("Contestant with given ids does not exist!"));
        return CompetitionMapper.fromContestantToGetContestantResultDto(contestant);
    }

    public Long voteForContestant(Long competitionId, Long contestantId, Long voterId){
        User voter = userRepository.findById(voterId)
                .orElseThrow(() -> new ObjectNotFoundException("User with given id does not exist!"));
        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new ObjectNotFoundException("Competition with given id does not exist!"));
        Contestant contestant = contestantRepository.findByCompetition_IdAndUser_Id(competitionId, contestantId)
                .orElseThrow(() -> new ObjectNotFoundException("Contestant with given ids does not exist!"));

        if(!contestant.getIsAccepted()){
            throw new NotAllowedOperationException("Contestant is not accepted by administrator!");
        }

        if(voter.getCompetitions().contains(competition)){
            throw new NotAllowedOperationException("User has already voted!");
        }
        contestant.setVotesNumber(contestant.getVotesNumber() + 1);
        voter.getCompetitions().add(competition);
        return voter.getId();
    }

    public Long updateCompetitionById(UpdateCompetitionDto updateCompetitionDto, Long competitionId){
        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new ObjectNotFoundException("Competition with given id does not exist!"));

        competition.setTitle(updateCompetitionDto.getTitle());
        competition.setDescription(updateCompetitionDto.getDescription());

        if (Objects.nonNull(updateCompetitionDto.getPicture())) {
            dropboxService.deleteFile(competition.getPictureUrl());
            competition.setPictureUrl(dropboxService.uploadFile(updateCompetitionDto.getPicture()));
        }

        return competition.getId();
    }

    public Long changeContestantAcceptance(Long competitionId, Long contestantId, Boolean isAccepted){
        Contestant contestant = contestantRepository.findByCompetition_IdAndUser_Id(competitionId, contestantId)
                .orElseThrow(() -> new ObjectNotFoundException("Contestant with given ids does not exist!"));
        contestant.setIsAccepted(isAccepted);
        return contestant.getId();
    }

    public Long deleteCompetitionById(Long competitionId){
        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new ObjectNotFoundException("Competition with given id does not exist!"));
        Set<Contestant> contestants = competition.getContestants();

        contestants.forEach(x -> System.out.println(x.getId()));

        for(Contestant contestant: contestants){deleteContestant(competitionId, contestant.getUser().getId());}
        if (competition.getPictureUrl().compareTo(DEFAULT_PICTURE_URL) != 0) {dropboxService.deleteFile(competition.getPictureUrl());}

        competitionRepository.delete(competition);
        return competition.getId();
    }

    public Long deleteContestant(Long competitionId, Long contestantId){
        Contestant contestant = contestantRepository.findByCompetition_IdAndUser_Id(competitionId, contestantId)
                .orElseThrow(() -> new ObjectNotFoundException("Contestant with given ids does not exist!"));

        if (contestant.getResultUrl() != null) {dropboxService.deleteFile(contestant.getResultUrl());}
        contestantRepository.delete(contestant);
        return contestant.getId();
    }
}
