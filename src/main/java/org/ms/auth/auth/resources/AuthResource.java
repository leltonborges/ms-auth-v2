package org.ms.auth.auth.resources;

import org.ms.auth.auth.dtos.UserDTO;
import org.ms.auth.auth.entities.User;
import org.ms.auth.auth.jwt.JWTTokernProvider;
import org.ms.auth.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/login",
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
)
public class AuthResource {
    private AuthenticationManager authenticationManager;
    private JWTTokernProvider jwtTokernProvider;
    private UserService userService;

    @Autowired
    public AuthResource(AuthenticationManager authenticationManager, JWTTokernProvider jwtTokernProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokernProvider = jwtTokernProvider;
        this.userService = userService;
    }

    @PostMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<Map<Object, Object>> save(@RequestBody UserDTO userDTO){
        var userName = userDTO.getUserName();
        var passwd = userDTO.getPassword();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, passwd));
        User user = userService.findByUserName(userName);
        String token = null;

        if(!user.equals(null))
            token = jwtTokernProvider.createToken(userName);

        Map<Object, Object> model = new HashMap<>();
        model.put("userName", userName);
        model.put("token", token);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(model);
    }

    @GetMapping("/test")
    public String test(){
        return  "Test";
    }
}
