package alzpaCare.server.security.controller;


import alzpaCare.server.member.entity.Authority;
import alzpaCare.server.member.entity.Member;
import alzpaCare.server.member.repository.MemberRepository;
import alzpaCare.server.member.response.MemberResponse;
import alzpaCare.server.member.response.MemberSummaryResponse;
import alzpaCare.server.security.jwt.JwtFilter;
import alzpaCare.server.security.jwt.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest {
//
//
//    @Autowired
//    private WebApplicationContext context;
//    @Autowired
//    private MemberRepository memberRepository;
//    @Autowired
//    private TokenProvider tokenProvider;
//    @Autowired
//    private AuthenticationManagerBuilder authenticationManagerBuilder;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    private MockMvc mvc;
//
//    //테스트용 유저 데이터
//    private Long memberId;
//    private String email = "test@gmail.com";
//    private String password = "test";
//
//    @Before
//    public void setUp(){
//        mvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(springSecurity()).build();
//
//        //data.sql로 미리들어간 데이터가 있으므로 테스트 실행전 전체 삭제해줌.
//        memberRepository.deleteAll();
//
//        //테스트용 유저정보 입력
//        Authority authority = Authority.builder()
//                .role("ROLE_USER")
//                .build();
//
//        Member member = Member.builder()
//                .email(email)
//                .password(passwordEncoder.encode(password))
//                .authorities(Collections.singleton(authority))
//                .build();
//
//        memberRepository.save(member);
//
//        //Security Context에 유저정보 등록, 토큰발급
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(email, password);
//
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        String jwt = tokenProvider.createToken(authentication);
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER,"Bearer "+ jwt);//
//    }
//
//    @After // 테스트 후 실행
//    public void tearDown() throws Exception {
//        memberRepository.deleteAll();
//    }
//
//    @Test
//    public void 로그인() throws Exception {
//        //given
//        String url = "http://localhost:8080/login";
//
//        //정상응답, 토큰값이 리턴되었는지 확인
//        mvc.perform(post(url)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.token").exists())
//        ;
//    }


}