package booktopiahub.mapper;

import booktopiahub.config.MapperConfig;
import booktopiahub.dto.category.CategoryDto;
import booktopiahub.dto.category.CreateCategoryRequestDto;
import booktopiahub.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    @Mapping(target = "id", ignore = true)
    Category toModel(CreateCategoryRequestDto createCategoryRequestDto);
}
