package alzpaCare.server.member.controller;

import alzpaCare.server.member.entity.Authority;
import alzpaCare.server.member.entity.Member;
import alzpaCare.server.member.request.FindEmailRequest;
import alzpaCare.server.member.request.FindPasswordRequest;
import alzpaCare.server.member.request.JoinRequest;
import alzpaCare.server.member.response.MemberResponse;
import alzpaCare.server.member.service.MemberService;
import alzpaCare.server.security.jwt.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Column;
import org.apache.tomcat.util.http.parser.Authorization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.security.Principal;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@Import(TestSecurityConfig.class)
class MemberControllerTest {

    @InjectMocks
    private MemberController memberController;
    @Mock
    private MemberService memberService;
    @Autowired
    private MockMvc mockMvc;

    private Principal mockPrincipal;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }

//    private void mockMemberSetup() {
//        Integer memberId = 1000;
//        String email = "example@email.com";
//        String password = "secure1462";
//        String name = "아이묭";
//        String nickname = "아이돌";
//        String phoneNumber = "11111111111";
//        String birth = "1990-01-01";
//        String imgUrl = "www.xzfsf.com";
//        String termsOfServiceYn = "Y";
//        String privacyPolicyYn = "Y";
//        String locationYn = "Y";
//        String emailYn = "N";
//        String deleteYn = "N";
//
//        Member testMember = Member.builder()
//                .memberId(memberId)
//                .email(email)
//                .password(password)
//                .name(name)
//                .nickname(nickname)
//                .phoneNumber(phoneNumber)
//                .birth(birth)
//                .imgUrl(imgUrl)
//                .termsOfServiceYn(termsOfServiceYn)
//                .privacyPolicyYn(privacyPolicyYn)
//                .locationYn(locationYn)
//                .emailYn(emailYn)
//                .deleteYn(deleteYn)
//                .build();
//
//        Authority authority = Authority.builder()
//                .role("ROLE_USER")
//                .build();
//
//        Set<Authority> grantedAuthorities = testMember.getAuthorities()
//                .stream()
//                .map(authority -> (GrantedAuthority) authority)
//                .collect(Collectors.toSet());
//
//        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
//                testMember.getEmail(),
//                testMember.getPassword(),
//                grantedAuthorities);
//
//
//
//        mockPrincipal = new UsernamePasswordAuthenticationToken(testMember.getEmail(), testMember.getPassword());
//    }

    @Test
    @DisplayName("회원 가입 성공 테스트")
    void join() throws Exception {
        //given
        JoinRequest joinRequest = new JoinRequest(
                "example@email.com",
                "secure1462",
                "아이묭",
                "아이돌",
                "11111111111",
                "1990-01-01",
                "Y",
                "Y",
                "Y",
                "N",
                "N"
        );

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(joinRequest);

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());
    }



    @Test
    @DisplayName("아이디 찾기 성공 테스트")
    void findId() throws Exception {
        //given
        FindEmailRequest findEmailRequest = new FindEmailRequest(
                "아이묭",
                "1990-01-01",
                "11111111111"
        );

        String expectedEmail = "example@email.com";
        when(memberService.findEmail(any())).thenReturn(expectedEmail);

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(findEmailRequest);

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/find/id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedEmail));
    }

    @Test
    @DisplayName("비밀번호 찾기 성공 테스트")
    void findPassword() throws Exception {
        // given
        FindPasswordRequest findPasswordRequest = new FindPasswordRequest(
                "example@email.com",
                "아이묭",
                "11111111111",
                "1990-01-01",
                "password123"
        );

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(findPasswordRequest);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/find/pw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());
    }

//    @GetMapping("/member")
//    public ResponseEntity<MemberResponse> getMember(Authentication authentication) {
//        String username = authentication.getName();
//
//        Member member = memberService.findMemberByEmail(username);
//
//        MemberResponse memberResponse = MemberMapper.toMemberResponse(member);
//
//        return ResponseEntity.ok(memberResponse);
//    }

//    @Test
//    @DisplayName("회원 정보 조회 성공 테스트")
//    @WithMockUser(username = "example@email.com", authorities = "ROLE_USER")
//    void getMember() throws Exception {
//        // Given
//        Member member = Member.builder()
//                .memberId(1000)
//                .email("example@email.com")
//                .password("secure1462")
//                .name("아이묭")
//                .nickname("아이돌")
//                .phoneNumber("11111111111")
//                .birth("1990-01-01")
//                .imgUrl(null)
//                .termsOfServiceYn("Y")
//                .privacyPolicyYn("Y")
//                .locationYn("Y")
//                .emailYn("N")
//                .deleteYn("N")
//                .authorities(Set.of(new Authority("ROLE_USER")))
//                .build();
//
//
//        Authentication authentication = new TestingAuthenticationToken("example@email.com", "secure1462", "ROLE_USER");
//
//        when(memberService.findMemberByEmail("example@email.com")).thenReturn(member);
//
//
//
//        // When, Then
//        mockMvc.perform(MockMvcRequestBuilders.get("/member")
//                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJVU0VSIl0sIm1lbWJlckVtYWlsIjoidGVzdDFAZ21haWwuY29tIiwic3ViIjoidGVzdDFAZ21haWwuY29tIiwiaWF0IjoxNjgxODIxOTEwLCJleHAiOjE2ODE4MjM3MTB9.h_V93dhS-RhzqVdYuRkxHHIxYjG61LSn87a_8HtpBgM") // JWT 토큰 값 설정
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .with(authentication(authentication)))
//                .andExpect(status().isOk());
//    }

//    @Test
//    @DisplayName("회원 정보 조회 성공 테스트")
//    @WithMockUser(username = "example@email.com", authorities = "ROLE_USER")
//    void getMember() throws Exception {
//        // Given
//        Member member = Member.builder()
//                .memberId(1000)
//                .email("example@email.com")
//                .password("secure1462")
//                .name("아이묭")
//                .nickname("아이돌")
//                .phoneNumber("11111111111")
//                .birth("1990-01-01")
//                .imgUrl(null)
//                .termsOfServiceYn("Y")
//                .privacyPolicyYn("Y")
//                .locationYn("Y")
//                .emailYn("N")
//                .deleteYn("N")
//                .authorities(Set.of(new Authority("ROLE_USER")))
//                .build();
//
//        when(memberService.findMemberByEmail(any())).thenReturn(member);
//
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken("example@email.com", "secure1462");
//
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String jwt = tokenProvider.createToken(authentication);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
//
////        TestingAuthenticationToken authentication = new TestingAuthenticationToken("example@email.com", "secure1462");
////        authentication.setAuthenticated(true);
////        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        // When, Then
//        mockMvc.perform(MockMvcRequestBuilders.get("/member")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .with(authentication(authentication)))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("example@email.com"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("아이묭"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.nickname").value("아이돌"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("11111111111"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.birth").value("1990-01-01"));
//    }

//    @Test
//    @DisplayName("회원 정보 조회 성공 테스트")
//    @WithMockUser(username = "example@email.com", authorities = "ROLE_USER")
//    void getMember() throws Exception {
//        // Given
//
//
//        // When, Then
//        mockMvc.perform(MockMvcRequestBuilders.get("/member")
//                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJVU0VSIl0sIm1lbWJlckVtYWlsIjoidGVzdDFAZ21haWwuY29tIiwic3ViIjoidGVzdDFAZ21haWwuY29tIiwiaWF0IjoxNjgxODIxOTEwLCJleHAiOjE2ODE4MjM3MTB9.h_V93dhS-RhzqVdYuRkxHHIxYjG61LSn87a_8HtpBgM") // JWT 토큰 값 설정
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(print());
//
//        verify(memberService, times(1))
//                .findMemberByEmail(mockPrincipal.getName());
//    }


    @Test
    void patchMember() throws Exception {

    }

    @Test
    void patchImgUrl() {
    }

    @Test
    void patchPassword() {
    }

    @Test
    void patchDelete() {
    }


}