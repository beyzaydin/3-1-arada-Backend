package main.java.winx.bitirme.auth.service.logic;

import main.java.winx.bitirme.auth.service.entity.User;
import main.java.winx.bitirme.auth.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    UserRepository userRepository;
    MongoTemplate mongoTemplate;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository,
                                  MongoTemplate mongoTemplate) {
        this.userRepository = userRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        User user = mongoTemplate.find(query, User.class).get(0);
        if(user == null )
               throw new UsernameNotFoundException("User Not Found with username: " + username);
        return UserDetailsImpl.build(user);
    }
}
