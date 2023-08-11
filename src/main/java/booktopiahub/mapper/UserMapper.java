package booktopiahub.mapper;

import booktopiahub.config.MapperConfig;
import booktopiahub.dto.user.CreateUserRequestDto;
import booktopiahub.dto.user.UserDto;
import booktopiahub.dto.user.UserResponseDto;
import booktopiahub.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    User toModel(CreateUserRequestDto requestDto);

    UserResponseDto toUserResponse(User user);
}
