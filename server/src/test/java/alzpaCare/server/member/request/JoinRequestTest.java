package alzpaCare.server.member.request;

import alzpaCare.server.member.controller.MemberController;
import alzpaCare.server.member.entity.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class JoinRequestTest {

    @InjectMocks
    private MemberController memberController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }

    @Test
    @DisplayName("Dto를 Entity로 성공적으로 변환시키는지 확인하는 테스트")
    void toEntity() {
        // Given
        JoinRequest joinRequest = new JoinRequest(
                "email@gmail.com",
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

        // When
        Member member = joinRequest.toEntity();

        // Then
        assertNotNull(member);
        assertEquals("email@gmail.com", member.getEmail());
        assertEquals("secure1462", member.getPassword());
        assertEquals("아이묭", member.getName());
        assertEquals("아이돌", member.getNickname());
        assertEquals("1990-01-01", member.getBirth());
        assertEquals("Y", member.getTermsOfServiceYn());
        assertEquals("Y", member.getPrivacyPolicyYn());
        assertEquals("Y", member.getLocationYn());
        assertEquals("N",member.getEmailYn());
        assertEquals("N", member.getDeleteYn());
    }

    @Test
    @DisplayName("유효하지 않은 이메일 형식 테스트")
    void InvalidEmail() throws Exception {
        // given
        JoinRequest joinRequest = new JoinRequest(
                "invalid-email",
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

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("유효하지 않은 이메일 공백 테스트")
    void BlankEmail() throws Exception {
        // given
        JoinRequest joinRequest = new JoinRequest(
                " ",
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

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("숫자를 포함하지 않은 비밀번호 등록 테스트")
    void password() throws Exception {
        // given
        JoinRequest joinRequest = new JoinRequest(
                "email@gmailcom",
                "writeValue",
                "아이묭",
                "아이돌",
                "11111111111",
                "1990.01.01",
                "Y",
                "Y",
                "Y",
                "N",
                "N"
        );

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(joinRequest);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("이름 공백 등록 테스트")
    void name() throws Exception {
        // given
        JoinRequest joinRequest = new JoinRequest(
                "email@gmailcom",
                "secure1462",
                "     ",
                "나는야퉁퉁이골목대장이라네",
                "11111111111",
                "1990.01.01",
                "Y",
                "Y",
                "Y",
                "N",
                "N"
        );

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(joinRequest);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("너무 긴 닉네임 등록 테스트")
    void nickname() throws Exception {
        // given
        JoinRequest joinRequest = new JoinRequest(
                "email@gmailcom",
                "secure1462",
                "아이묭",
                "나는야퉁퉁이골목대장이라네",
                "11111111111",
                "1990.01.01",
                "Y",
                "Y",
                "Y",
                "N",
                "N"
        );

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(joinRequest);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("'-'를 포함한 전화번호 테스트")
    void phoneNumber() throws Exception {
        // given
        JoinRequest joinRequest = new JoinRequest(
                "email@gmailcom",
                "secure1462",
                "아이묭",
                "아이돌",
                "010-1111-3333",
                "1990-01-01",
                "Y",
                "Y",
                "Y",
                "N",
                "N"
        );

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(joinRequest);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("생일양식 변경 테스트")
    void birth() throws Exception {
        // given
        JoinRequest joinRequest = new JoinRequest(
                "email@gmailcom",
                "secure1462",
                "아이묭",
                "아이돌",
                "11111111111",
                "1990.01.01",
                "Y",
                "Y",
                "Y",
                "N",
                "N"
        );

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(joinRequest);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("필수약관 비동의 테스트")
    void termsOfServiceYn() throws Exception {
        // given
        JoinRequest joinRequest = new JoinRequest(
                "email@gmailcom",
                "secure1462",
                "아이묭",
                "아이돌",
                "11111111111",
                "1990-01-01",
                "N",
                "Y",
                "Y",
                "N",
                "N"
        );

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(joinRequest);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("유효하지 않은 값 테스트")
    void privacyPolicyYn() throws Exception {
        // given
        JoinRequest joinRequest = new JoinRequest(
                "email@gmailcom",
                "secure1462",
                "아이묭",
                "아이돌",
                "11111111111",
                "1990-01-01",
                "Y",
                "true",
                "Y",
                "N",
                "N"
        );

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(joinRequest);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void locationYn() {
    }

    @Test
    void emailYn() {
    }

    @Test
    void deleteYn() {
    }


}