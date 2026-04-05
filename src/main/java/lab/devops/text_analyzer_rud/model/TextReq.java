package lab.devops.text_analyzer_rud.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TextReq {

    @NotBlank(message = "Text cannot be blank")
    private String text;
}
