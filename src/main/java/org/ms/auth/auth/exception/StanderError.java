package org.ms.auth.auth.exception;

import javassist.SerialVersionUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class StanderError implements Serializable {
    private static final long serialVersionUID = 6218053110603951170L;

    private String error;
    private String message;
    private HttpStatus status;
    private String path;
}
