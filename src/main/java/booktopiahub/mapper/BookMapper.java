package booktopiahub.mapper;

import booktopiahub.config.MapperConfig;
import booktopiahub.dto.BookDto;
import booktopiahub.dto.CreateBookRequestDto;
import booktopiahub.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    @Mapping(target = "id", ignore = true)
    Book toModel(CreateBookRequestDto requestDto);
}
