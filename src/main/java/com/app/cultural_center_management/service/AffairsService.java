package com.app.cultural_center_management.service;

import com.app.cultural_center_management.dto.affairsDto.*;
import com.app.cultural_center_management.entity.Affair;
import com.app.cultural_center_management.entity.AffairRating;
import com.app.cultural_center_management.entity.User;
import com.app.cultural_center_management.exception.NotAllowedOperationException;
import com.app.cultural_center_management.exception.ObjectNotFoundException;
import com.app.cultural_center_management.mapper.AffairsMapper;
import com.app.cultural_center_management.mapper.UsersMapper;
import com.app.cultural_center_management.repository.AffairRatingRepository;
import com.app.cultural_center_management.repository.AffairRepository;
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
public class AffairsService {

    private final String DEFAULT_PICTURE_URL = "https://www.dropbox.com/s/ru4od6mqj5alo34/default.jpg?raw=1";
    private final AffairRepository affairRepository;
    private final AffairRatingRepository affairRatingRepository;
    private final UserRepository userRepository;
    private final DropboxService dropboxService;


    public Long createAffair(UpdateAffairDto updateAffairDto, Long ownerId){
        Affair affairToCreate = AffairsMapper.fromUpdateAffairDtoToAffair(updateAffairDto);
        affairToCreate.setSinceDate(LocalDate.now());
        affairToCreate.setPictureUrl(DEFAULT_PICTURE_URL);
        affairToCreate.setOwner(userRepository.findById(ownerId)
                .orElseThrow(() -> new ObjectNotFoundException("Affair with given id does not exist!")));

        if (Objects.nonNull(updateAffairDto.getPicture())){
            affairToCreate.setPictureUrl(dropboxService.uploadFile(updateAffairDto.getPicture()));
        }

        return affairRepository.save(affairToCreate).getId();
    }

    public Page<GetAllAffairsDto> getAllAffairs(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Affair> page = affairRepository.findAllByOrderBySinceDateDesc(pageRequest);
        List<GetAllAffairsDto> resultContent = AffairsMapper.fromAffairListToGetAllAffairsList(page.getContent());

        return new PageImpl<>(resultContent, pageRequest, page.getTotalElements());
    }

    public List<GetAllUserAffairs> getAllAffairsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("User with given id does not exists!"));
        return AffairsMapper.fromAffairsListToGetAllUserAffairs(user.getUserAffairs());
    }

    public GetAffairDto getAffairById(Long affairId){
        return AffairsMapper.fromAffairToGetAffairDto(affairRepository.findById(affairId)
                .orElseThrow(() -> new ObjectNotFoundException("Affair with given id does not exist!")));
    }

    public Set<GetEnrolledForAffairUser> getEnrolledForAffairUsers(Long affairId){
        Affair affair = affairRepository.findById(affairId)
                .orElseThrow(() -> new ObjectNotFoundException("Affair with given id does not exist!"));
        return UsersMapper.fromUserSetToGetEnrolledForAffairUserSet(affair.getParticipants());
    }

    public Long updateAffair(UpdateAffairDto updateAffairDto, Long affairId){
        Affair affair = affairRepository.findById(affairId)
                .orElseThrow(() -> new ObjectNotFoundException("Affair with given id does not exist!"));

        affair.setTitle(updateAffairDto.getTitle());
        affair.setAvailableSeats(updateAffairDto.getAvailableSeats());
        affair.setShortDescription(updateAffairDto.getShortDescription());

        if(Objects.nonNull(updateAffairDto.getPicture())){
            dropboxService.deleteFile(affair.getPictureUrl());
            affair.setPictureUrl(dropboxService.uploadFile(updateAffairDto.getPicture()));
        }

        return affair.getId();
    }

    public Long updateAffairRate(Long affairId, Long userId, Long affairRate) {
        Affair affair = affairRepository.findById( affairId)
                .orElseThrow(() -> new ObjectNotFoundException("Affair with given id does not exist!"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("User with given id does not exist!"));

        if (affairRatingRepository.existsByAffair_IdAndUser_Id(affairId, userId)){
            throw new NotAllowedOperationException("User already voted!");
        }

        AffairRating affairRating = AffairRating.builder()
                .affair(affair)
                .user(user)
                .rating(affairRate)
                .build();

        return affairRatingRepository.save(affairRating).getId();
    }

    public Long deleteAffair(Long affairId){
        Affair affair = affairRepository.findById(affairId)
                .orElseThrow(() -> new ObjectNotFoundException("Affair with given id does not exist!"));
        Set<User> users = affair.getParticipants();
        for(User user : users){
            user.getUserAffairs().remove(affair);
            affair.getParticipants().remove(user);
        }
        if (affair.getPictureUrl().compareTo(DEFAULT_PICTURE_URL) != 0) {dropboxService.deleteFile(affair.getPictureUrl());}
        affairRepository.delete(affair);
        return affair.getId();
    }

    public Long delistUserFromAffair(Long affairId, Long userId){
        Affair affair = affairRepository.findById(affairId)
                .orElseThrow(() -> new ObjectNotFoundException("Affair with given id does not exist!"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("User with given id does not exist!"));

        user.getUserAffairs().remove(affair);
        affair.setAvailableSeats(affair.getAvailableSeats() + 1);

        return affair.getId();
    }

    public Long enrollUsersToAffair(Long affairId, List<Long> usersIds){
        Affair affair = affairRepository.findById(affairId)
                .orElseThrow(() -> new ObjectNotFoundException("Affair with given id does not exist!"));

        if (affair.getAvailableSeats() < usersIds.size()){
            throw new NotAllowedOperationException("There are not enough places for such a number of participants!");
        }

        for(Long userId : usersIds){
            enrollUser(affair, userId);
        }
        return (long)usersIds.size();
    }

    public Long enrollUser(Affair affair, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("User with given id does not exist!"));

        if (user.getUserAffairs().contains(affair)){
            throw new NotAllowedOperationException("User is already enrolled to this affair!");
        }

        if (affair.getAvailableSeats() <= 0){
            throw new NotAllowedOperationException("There is no free seat for this affair!");
        }

        affair.setAvailableSeats(affair.getAvailableSeats() - 1);
        user.getUserAffairs().add(affair);

        return user.getId();
    }
}
