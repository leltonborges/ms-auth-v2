package org.ms.auth.auth.configs;

import org.ms.auth.auth.entities.Permission;
import org.ms.auth.auth.entities.User;
import org.ms.auth.auth.repositories.PermissionRepository;
import org.ms.auth.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class TestDBConfig implements CommandLineRunner {
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private PermissionRepository permissionRepository;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        Permission admin_permission = new Permission(null, "ADMIN");
        Permission operator_permision = new Permission(null, "OPERATOR");
        Permission client_permision = new Permission(null, "CLIENT");
        Set<Permission> permissions_adm = Arrays.asList(admin_permission, client_permision).stream().collect(Collectors.toSet());
        Set<Permission> permissions_operator = Arrays.asList(operator_permision, client_permision).stream().collect(Collectors.toSet());

        User lia = new User(null, "lia", passwordEncoder.encode("1234"), permissions_adm);
        User bob = new User(null, "bob", passwordEncoder.encode("1234"), permissions_operator);

//        permissionRepository.save(admin_permission);
//        permissionRepository.save(operator_permision);
//        permissionRepository.save(client_permision);
        userService.save(lia);
        userService.save(bob);

    }


}
