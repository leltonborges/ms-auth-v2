package org.ms.auth.auth.service;

import org.ms.auth.auth.entities.Permission;
import org.ms.auth.auth.repositories.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    public Optional<Permission> findByDescription(String description) {
        return permissionRepository.findByDescription(description);
    }

    public Permission save(Permission entity) {
        return permissionRepository.save(entity);
    }

    public Optional<Permission> findById(Long aLong) {
        return permissionRepository.findById(aLong);
    }
}
