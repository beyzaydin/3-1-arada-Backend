package winx.bitirme.auth.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import winx.bitirme.auth.client.model.JwtResponse;
import winx.bitirme.auth.client.model.LoginRequest;
import winx.bitirme.auth.client.model.MessageResponse;
import winx.bitirme.auth.client.model.SignupRequest;
import winx.bitirme.auth.service.entity.ERole;
import winx.bitirme.auth.service.entity.Role;
import winx.bitirme.auth.service.entity.User;
import winx.bitirme.auth.service.logic.UserDetailsImpl;
import winx.bitirme.auth.service.repository.RoleRepository;
import winx.bitirme.auth.service.repository.UserRepository;
import winx.bitirme.auth.service.utils.JwtUtils;
import winx.bitirme.mongo.service.logic.SequenceGeneratorService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final MongoOperations mongo;

    private final MongoTemplate mongoTemplate;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;

    private final SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    public AuthController(MongoOperations mongo,
                          MongoTemplate mongoTemplate,
                          AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder encoder,
                          JwtUtils jwtUtils,
                          SequenceGeneratorService sequenceGeneratorService) {
        this.mongo = mongo;
        this.mongoTemplate = mongoTemplate;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.sequenceGeneratorService = sequenceGeneratorService;

    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @GetMapping("/test")
    public User test() {
        User user = new User();
        user.setId(1L);
        user.setEmail("beyzaaydin@etu.edu.tr");
        user.setUsername("baydin");
        user.setPassword("12345");
        Role role = new Role();
        return user;
    }

    @PostMapping("/role")
    public Role addRole(@RequestBody Role role){
        long id = sequenceGeneratorService.generateSequence(Role.SEQUENCE_NAME);
        role.setId(id);
        return roleRepository.insert(role);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        if (mongo.exists(
                Query.query(Criteria.where("username").is(signUpRequest.getUsername())), User.class)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (mongo.exists(
                Query.query(Criteria.where("email").is(signUpRequest.getEmail())), User.class)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = mongo.findOne(
                    Query.query(Criteria.where("name").is(ERole.ROLE_USER)), Role.class);
            if(userRole == null)
                throw new RuntimeException("Error: Role is not found.");
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                Role userRole;
                switch (role) {
                    case "admin":
                        userRole = mongo.findOne(
                                Query.query(Criteria.where("name").is(ERole.ROLE_ADMIN)), Role.class);
                        if(userRole == null)
                            throw new RuntimeException("Error: Role is not found.");
                        roles.add(userRole);

                        break;
                    case "mod":
                        userRole = mongo.findOne(
                                Query.query(Criteria.where("name").is(ERole.ROLE_MODERATOR)), Role.class);
                        if(userRole == null)
                            throw new RuntimeException("Error: Role is not found.");
                        roles.add(userRole);

                        break;
                    default:
                        userRole = mongo.findOne(
                                Query.query(Criteria.where("name").is(ERole.ROLE_USER)), Role.class);
                        if(userRole == null)
                            throw new RuntimeException("Error: Role is not found.");
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        long id = sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME);
        user.setId(id);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
