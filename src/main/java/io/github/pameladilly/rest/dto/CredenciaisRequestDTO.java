package io.github.pameladilly.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "Credencias DTO")
public class CredenciaisRequestDTO {

    @ApiModelProperty(dataType = "String", value = "Login do usuário")
    private String login;

    @ApiModelProperty(dataType = "String", value = "Senha do usuário")
    private String senha;
}
