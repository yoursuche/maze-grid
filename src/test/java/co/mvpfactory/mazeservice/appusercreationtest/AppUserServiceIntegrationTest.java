package co.mvpfactory.mazeservice.appusercreationtest;

import co.mvpfactory.maze.MazeService;
import co.mvpfactory.maze.dto.NewUserReq;
import co.mvpfactory.maze.repository.AppUserRepository;
import co.mvpfactory.maze.util.Utility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author uchechukwu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MazeService.class})
public class AppUserServiceIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    AppUserRepository userRepo;

    private MockMvc mockMvc;
    String testUser = "Uche";
    boolean testUserExit = false;

    @Before
    public void setup() throws JsonProcessingException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .addFilter(springSecurityFilterChain).build();
        testUserExit = userRepo.existsByUsernameIgnoreCase(testUser);

    }

    @Test
    @Order(1)
    public void user_successful_creation() throws Exception {
        if (testUserExit) {
            testUser = Utility.requestId(testUser);
        }
        NewUserReq newUserReq = new NewUserReq();
        newUserReq.setUsername(testUser);
        newUserReq.setPassword("password");
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(post("/user")
                .content(mapper.writeValueAsString(newUserReq))
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("userId").isNumber())
                .andExpect(jsonPath("password", is("password")))
                .andExpect(jsonPath("username", is(testUser)));
    }

}
