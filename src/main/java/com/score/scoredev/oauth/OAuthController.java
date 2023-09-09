package com.score.scoredev.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {

    @ResponseBody
    @GetMapping("/kakao")
    public String kakaoCallback(@RequestParam String code) {
        return code;
    }
}
