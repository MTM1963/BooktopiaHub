package booktopiahub.dto.category;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CreateCategoryRequestDto {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
}
