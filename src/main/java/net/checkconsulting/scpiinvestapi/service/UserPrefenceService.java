package net.checkconsulting.scpiinvestapi.service;

import net.checkconsulting.scpiinvestapi.dto.ProfileInformationDto;
import net.checkconsulting.scpiinvestapi.entity.UserPreference;
import net.checkconsulting.scpiinvestapi.mapper.UserMapper;
import net.checkconsulting.scpiinvestapi.repository.UserPreferenceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPrefenceService {

    private final UserPreferenceRepository userPreferenceRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    public UserPrefenceService(UserPreferenceRepository userPreferenceRepository, UserService userService, UserMapper userMapper) {
        this.userPreferenceRepository = userPreferenceRepository;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    public void createOrUpdateUserPreferences(ProfileInformationDto profileInformationDto) {

        userPreferenceRepository.findById(userService.getEmail()).ifPresentOrElse(userPreference -> {
                    userPreference.setIncome(profileInformationDto.getIncome());
                    userPreference.setProfession(profileInformationDto.getProfession());
                    userPreference.setBirthDate(profileInformationDto.getBirthDate());
                    userPreference.setProfileType(profileInformationDto.getProfileType());
                    userPreference.setFamilyStatus(profileInformationDto.getFamilyStatus());
                    userPreference.setChildrenCount(profileInformationDto.getChildrenCount());
                    userPreferenceRepository.save(userPreference);
                },
                () -> {
                    UserPreference userPreference = UserPreference.builder()
                            .email(userService.getEmail())
                            .birthDate(profileInformationDto.getBirthDate())
                            .childrenCount(profileInformationDto.getChildrenCount())
                            .income(profileInformationDto.getIncome())
                            .familyStatus(profileInformationDto.getFamilyStatus())
                            .profession(profileInformationDto.getProfession())
                            .profileType(profileInformationDto.getProfileType())
                            .build();
                    userPreferenceRepository.save(userPreference);
                }
        );


    }

    public ProfileInformationDto getUserPreference() {
        Optional<UserPreference> optionalUserPreference = userPreferenceRepository.findById(userService.getEmail());
        return optionalUserPreference.map(userMapper::toProfileInformationDto)
                .orElse(ProfileInformationDto.builder()
                        .email(userService.getEmail())
                        .build());
    }
}
