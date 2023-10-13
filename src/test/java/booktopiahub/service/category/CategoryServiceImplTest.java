package booktopiahub.service.category;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import booktopiahub.dto.category.CategoryDto;
import booktopiahub.dto.category.CreateCategoryRequestDto;
import booktopiahub.exception.EntityNotFoundException;
import booktopiahub.mapper.CategoryMapper;
import booktopiahub.model.Category;
import booktopiahub.repository.category.CategoryRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    public void testSuccessfulSaveCategory() {
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto();
        Category category = new Category();
        CategoryDto categoryDto = new CategoryDto();

        when(categoryMapper.toModel(requestDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        CategoryDto expectedResult = categoryService.save(requestDto);

        assertNotNull(expectedResult);
    }

    @Test
    public void testGetAllCategories() {
        Category category = new Category();
        Pageable pageable = PageRequest.of(0, 10);
        List<Category> categories = List.of(new Category());
        List<CategoryDto> expectedCategories = List.of(new CategoryDto());
        Page<Category> page = new PageImpl<>(categories, pageable, categories.size());

        when(categoryRepository.findAll(pageable)).thenReturn(page);
        when(categoryMapper.toDto(category)).thenReturn(new CategoryDto());

        List<CategoryDto> result = categoryService.getAll(pageable);

        Assertions.assertEquals(expectedCategories.size(), result.size());
    }

    @Test
    public void testFindCategoryById() {
        Long categoryId = 1L;

        Category category = new Category();
        CategoryDto categoryDto = new CategoryDto();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        CategoryDto result = categoryService.findById(categoryId);

        Assertions.assertEquals(category.getId(), result.getId());
    }

    @Test
    void testFindCategoryByNonExistedId() {
        Long categoryId = 50L;

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> categoryService.findById(categoryId));
    }

    @Test
    public void testDeleteCategoryById() {
        Long categoryId = 1L;
        categoryService.deleteById(categoryId);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> categoryService.findById(categoryId));
    }

    @Test
    public void testUpdateCategory() {
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto();
        requestDto.setName("Updated");
        requestDto.setDescription("Updated");

        CategoryDto expectedResult = new CategoryDto();
        expectedResult.setName("Updated");
        expectedResult.setDescription("Updated");

        Category category = new Category();
        category.setName("Old");
        category.setDescription("Old");

        Long categoryId = 1L;

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(expectedResult);

        CategoryDto result = categoryService.update(categoryId, requestDto);

        Assertions.assertEquals(result.getName(), expectedResult.getName());
        Assertions.assertEquals(result.getDescription(), expectedResult.getDescription());
    }
}
