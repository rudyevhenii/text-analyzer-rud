package lab.devops.text_analyzer_rud.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TextReq {

    @NotBlank(message = "Text cannot be blank")
    private String text;
}
