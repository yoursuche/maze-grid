package co.mvpfactory.mazeservice.AuthTest;

import co.mvpfactory.maze.MazeService;
import co.mvpfactory.maze.dto.request.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MazeService.class})
public class AuthserviceApplicationTests {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .addFilter(springSecurityFilterChain).build();
    }

    @Test
    public void init() {
    }

    private String obtainAccessToken(String username, String password) throws Exception {
        String token = null;
        ResultActions result = null;
        LoginRequest loginReq = new LoginRequest();
        loginReq.setPassword(password);
        loginReq.setUsername(username);
        try {
            ObjectMapper mapper = new ObjectMapper();
            result
                    = mockMvc.perform(post("/login")
                            .content(mapper.writeValueAsString(loginReq))
                            .contentType(MediaType.APPLICATION_JSON)).andDo(print());

        } catch (Exception ex) {
            System.err.println("Error  :" + ex.getMessage());
        }

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();

        Object obj = jsonParser.parseMap(resultString).get("accessToken");
        if (obj != null) {
            token = obj.toString();
        }
        return token;
    }

   // @Test
   // @Order(1)
    public void login_success() throws Exception {
        final String accessToken = obtainAccessToken("Uche", "password");
        System.out.println("token:" + accessToken);
        Assert.assertNotNull(accessToken);

    }

   // @Test
   // @Order(2)
    public void login_failure() throws Exception {
        final String accessToken = obtainAccessToken("fakeuser@fakeemail.com", "password");
        Assert.assertNull(accessToken);

    }
}
