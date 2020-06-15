package io.github.pameladilly.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@ApiModel(description = "Credencias DTO")
public class CredenciaisRequestDTO {

    @ApiModelProperty(dataType = "String", value = "Login do usuário")
    private String login;

    @ApiModelProperty(dataType = "String", value = "Senha do usuário")
    private String senha;
}
