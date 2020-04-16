package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RecaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RecaptchaService captchaService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid User user,
                                    @RequestParam(name="g-recaptcha-response") String recaptchaResponse,
                                    HttpServletRequest request){

        String ip = request.getRemoteAddr();
        String captchaVerifyMessage =
                captchaService.verifyRecaptcha(ip, recaptchaResponse);

        if (StringUtils.isNotEmpty(captchaVerifyMessage)) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", captchaVerifyMessage);
            return ResponseEntity.badRequest()
                    .body(response);
        }

        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> users(){
        return ResponseEntity.ok(userRepository.findAll());
    }
}
