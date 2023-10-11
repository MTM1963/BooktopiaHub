package booktopiahub.dto.category;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateCategoryRequestDto {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
}
