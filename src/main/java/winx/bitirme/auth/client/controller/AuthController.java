package winx.bitirme.auth.client.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import winx.bitirme.achievement.service.logic.UserAchievementService;
import winx.bitirme.auth.client.model.*;
import winx.bitirme.auth.service.constant.EmailTemplate;
import winx.bitirme.auth.service.entity.ERole;
import winx.bitirme.auth.service.entity.PasswordChangeTokenEntity;
import winx.bitirme.auth.service.entity.Role;
import winx.bitirme.auth.service.entity.User;
import winx.bitirme.auth.service.logic.EmailService;
import winx.bitirme.auth.service.logic.UserDetailsImpl;
import winx.bitirme.auth.service.repository.PasswordChangeTokenRepository;
import winx.bitirme.auth.service.repository.RoleRepository;
import winx.bitirme.auth.service.repository.UserRepository;
import winx.bitirme.auth.service.utils.JwtUtils;
import winx.bitirme.mongo.service.logic.SequenceGeneratorService;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class AuthController {
    private static int jwtExpirationMs = 86400000;
    private final MongoOperations mongo;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final SequenceGeneratorService sequenceGeneratorService;
    @Qualifier("gmail")
    private final EmailService emailService;
    private final PasswordChangeTokenRepository passwordChangeTokenRepository;
    @Value("$ {bezkoder.app.jwtSecret} ")
    private String jwtSecret;
    private final UserAchievementService userAchievementService;

    @Autowired
    public AuthController(MongoOperations mongo,
                          AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder encoder,
                          JwtUtils jwtUtils,
                          SequenceGeneratorService sequenceGeneratorService,
                          EmailService emailService,
                          PasswordChangeTokenRepository passwordChangeTokenRepository,
                          UserAchievementService userAchievementService) {
        this.mongo = mongo;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.emailService = emailService;
        this.passwordChangeTokenRepository = passwordChangeTokenRepository;
        this.userAchievementService = userAchievementService;
    }

    @PostMapping(value = "/updatePass",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updatePassword(@RequestBody PasswordModel passwordModel) {
        User user = userRepository.findByUsername(passwordModel.getEmail());
        if (user == null)
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("User not found");
        if (!encoder.matches(passwordModel.getOldPassword(), user.getPassword()))
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("Password is not correct");
        user.setPassword(encoder.encode(passwordModel.getNewPassword()));
        user = userRepository.save(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Bearer " + Jwts.builder()
                        .setSubject(user.getUsername())
                        .setIssuedAt(new Date())
                        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                        .signWith(SignatureAlgorithm.HS512, jwtSecret)
                        .compact());
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

        userAchievementService.dailyLoginAchieved(loginRequest.getUsername());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));

    }

    @PostMapping("/role")
    public Role addRole(@RequestBody Role role) {
        long id = sequenceGeneratorService.generateSequence(Role.SEQUENCE_NAME);
        role.setId(id);
        return roleRepository.insert(role);
    }

    @PostMapping("/signup")
    public ResponseEntity registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (mongo.exists(
                Query.query(Criteria.where("email").is(signUpRequest.getEmail())), User.class)) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = new User(signUpRequest.getEmail(),
                signUpRequest.getName(),
                signUpRequest.getSurname(),
                signUpRequest.getBirthDate(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getGender()
        );


        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = mongo.findOne(
                    Query.query(Criteria.where("name").is(ERole.ROLE_USER)), Role.class);
            if (userRole == null)
                throw new RuntimeException("Error: Role is not found.");
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                Role userRole;
                switch (role) {
                    case "admin":
                        userRole = mongo.findOne(
                                Query.query(Criteria.where("name").is(ERole.ROLE_ADMIN)), Role.class);
                        if (userRole == null)
                            throw new RuntimeException("Error: Role is not found.");
                        roles.add(userRole);

                        break;
                    case "mod":
                        userRole = mongo.findOne(
                                Query.query(Criteria.where("name").is(ERole.ROLE_MODERATOR)), Role.class);
                        if (userRole == null)
                            throw new RuntimeException("Error: Role is not found.");
                        roles.add(userRole);

                        break;
                    default:
                        userRole = mongo.findOne(
                                Query.query(Criteria.where("name").is(ERole.ROLE_USER)), Role.class);
                        if (userRole == null)
                            throw new RuntimeException("Error: Role is not found.");
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        long id = sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME);
        user.setId(id);
        userRepository.save(user);

        userAchievementService.saveInitialEntities(user.getEmail());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Bearer " + Jwts.builder()
                        .setSubject(user.getUsername())
                        .setIssuedAt(new Date())
                        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                        .signWith(SignatureAlgorithm.HS512, jwtSecret)
                        .compact());
    }

    @GetMapping("/forgotPass")
    public ResponseEntity sendEmail(@RequestParam String email) throws MessagingException {
        if(userRepository.findByEmail(email) == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("user not found!");
        PasswordChangeTokenEntity entity = new PasswordChangeTokenEntity();
        entity.setId(sequenceGeneratorService.generateSequence(PasswordChangeTokenEntity.SEQUENCE_NAME));
        entity.setEmail(email);
        entity.setToken(UUID.randomUUID().toString());
        passwordChangeTokenRepository.save(entity);

        String mail = new EmailTemplate().getTemplate();
        String url = "http://localhost:3000/resetPassword/token="+entity.getToken();
        mail = String.format(mail, url);

        emailService.sendSimpleEmail(email,
                "3-1 Arada Password Reset",
                mail);
        return ResponseEntity.ok("Password Verification Mail Sent.");
    }

    @GetMapping("/forgotPass/checkExpire")
    public ResponseEntity checkExpired(@RequestParam String token) {
        PasswordChangeTokenEntity entity = passwordChangeTokenRepository.findByToken(token);
        if(entity == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("token is invalid.");
        return ResponseEntity.ok(entity.isExpired());
    }
}
