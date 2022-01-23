package org.ms.auth.auth.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_permission")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission implements Serializable, GrantedAuthority {
    private static final long serialVersionUID = 3484060683364631997L;
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String description;

    @Override
    public String getAuthority() {
        return description;
    }


}
