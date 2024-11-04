package net.checkconsulting.scpiinvestapi.mapper;

import net.checkconsulting.scpiinvestapi.dto.ProfileInformationDto;
import net.checkconsulting.scpiinvestapi.entity.UserPreference;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {


    ProfileInformationDto toProfileInformationDto(UserPreference userPreference);
}
