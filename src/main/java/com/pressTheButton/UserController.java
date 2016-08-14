package com.pressTheButton;

import com.pressTheButton.Auth.StormpathApp;
import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.servlet.account.AccountResolver;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Tyler on 2016-08-10.
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    private StormpathApp stormpathApp;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public ResponseEntity<String> user(HttpServletRequest req){
        Account account = AccountResolver.INSTANCE.getAccount(req);
        logger.debug("Account requested {}, req {}", account, req);
        if (account != null) {
            return ResponseEntity.ok(account.getFullName());
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping("/")
    public ResponseEntity<String> home(HttpServletRequest req){
        ResponseEntity<String> entity = user(req);
        if (entity.getStatusCode() != HttpStatus.OK){
            return ResponseEntity.ok("Hey, just a reminder to login!");
        }
        return ResponseEntity.ok("Thanks for logging in! :)");
    }
}
