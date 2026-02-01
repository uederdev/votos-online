package br.com.ueder.votoonline.validators;

import br.com.ueder.votoonline.exceptions.RNException;
import org.springframework.stereotype.Component;

@Component
public class ImutableFieldValidator {

    public <T>void validate(String field, Object fieldOrigin, Object fieldValue){
        if ((fieldOrigin == null) && (fieldValue == null)){
            throw new IllegalArgumentException("Campo de origem e de comparação devem ser informados.");
        }

        if(fieldOrigin != fieldValue){
            throw new RNException("O campo " + field + " não pode ser alterado");
        }
    }
}
