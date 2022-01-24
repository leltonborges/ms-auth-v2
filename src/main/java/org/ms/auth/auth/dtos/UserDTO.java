package org.ms.auth.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.ms.auth.auth.entities.Permission;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
@Data
@AllArgsConstructor
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 952403064025384715L;

//    private Long id;
    private String userName;
    private String password;
//    private Set<Permission> permissions =  new HashSet<>();
}
