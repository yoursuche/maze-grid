package co.mvpfactory.mazeservice.mazetest;

import co.mvpfactory.maze.MazeService;
import co.mvpfactory.maze.dto.request.LoginRequest;
import co.mvpfactory.maze.dto.request.MazeRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
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
public class MazeServiceIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    private MockMvc mockMvc;

    @Before
    public void setup() throws JsonProcessingException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .addFilter(springSecurityFilterChain).build();

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

    @Test
    @Order(1)
    public void user_maze_success_creation() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        String token = obtainAccessToken("Uche", "password");
        headers.add("Authorization", "Bearer " + token);
        MazeRequest mazeRequest = new MazeRequest();
        mazeRequest.setEntrance("A1");
        mazeRequest.setGridSize("8x8");
        ArrayList<String> walls = new ArrayList<>();
        walls.add("G1");
        walls.add("C1");
        walls.add("A2");
        walls.add("C2");
        walls.add("E2");
        walls.add("G2");
        walls.add("C3");
        walls.add("E3");
        walls.add("B4");
        walls.add("C4");
        walls.add("E4");
        walls.add("F4");
        walls.add("G4");
        walls.add("B5");
        walls.add("E5");
        walls.add("B6");
        walls.add("D6");
        walls.add("E6");
        walls.add("G6");
        walls.add("H6");
        walls.add("B7");
        walls.add("D7");
        walls.add("G7");
        walls.add("B8");
        mazeRequest.setWalls(walls);
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(post("/maze").headers(headers)
                .content(mapper.writeValueAsString(mazeRequest))
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("mazeId").isNumber())
                .andExpect(jsonPath("$.mazeId", is(notNullValue())))
                .andExpect(jsonPath("$.entrance", is(mazeRequest.getEntrance())))
                .andExpect(jsonPath("$.gridSize", is(mazeRequest.getGridSize())))
                .andExpect(jsonPath("walls").isNotEmpty())
                .andExpect(jsonPath("walls").isArray())
                .andExpect(jsonPath("$.walls", hasItem("B6")));

    }

    @Test
    @Order(2)
    public void user_maze_creation_failure() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        String token = obtainAccessToken("Uche", "password");
        headers.add("Authorization", "Bearer " + token);
        MazeRequest mazeRequest = new MazeRequest();
        mazeRequest.setEntrance("A1");
        mazeRequest.setGridSize("4x4");
        ArrayList<String> walls = new ArrayList<>();
        walls.add("G1");
        walls.add("C1");
        walls.add("A2");
        walls.add("C2");
        walls.add("E2");
        walls.add("M2");
        mazeRequest.setWalls(walls);
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(post("/maze").headers(headers)
                .content(mapper.writeValueAsString(mazeRequest))
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    @Order(3)
    public void user_maze_solution_min_success() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        String token = obtainAccessToken("Uche", "password");
        headers.add("Authorization", "Bearer " + token);
        mockMvc.perform(get("/maze/1/solution?step=min").headers(headers)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("path").isArray())
                .andExpect(jsonPath("path").isNotEmpty());

    }

    @Test
    @Order(4)
    public void user_maze_solution_max_success() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        String token = obtainAccessToken("Uche", "password");
        headers.add("Authorization", "Bearer " + token);
        mockMvc.perform(get("/maze/1/solution?step=max").headers(headers)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("path").isArray())
                .andExpect(jsonPath("path").isNotEmpty());

    }

    @Test
    @Order(5)
    public void user_maze_success_2_creation() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        String token = obtainAccessToken("Uche", "password");
        headers.add("Authorization", "Bearer " + token);
        MazeRequest mazeRequest = new MazeRequest();
        mazeRequest.setEntrance("A1");
        mazeRequest.setGridSize("4x4");
        ArrayList<String> walls = new ArrayList<>();
        walls.add("A4");
        walls.add("B4");
        walls.add("C4");
        walls.add("D4");
        mazeRequest.setWalls(walls);
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(post("/maze").headers(headers)
                .content(mapper.writeValueAsString(mazeRequest))
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("mazeId").isNumber())
                .andExpect(jsonPath("$.mazeId", is(notNullValue())))
                .andExpect(jsonPath("$.entrance", is(mazeRequest.getEntrance())))
                .andExpect(jsonPath("$.gridSize", is(mazeRequest.getGridSize())))
                .andExpect(jsonPath("walls").isNotEmpty())
                .andExpect(jsonPath("walls").isArray());

    }

    @Test
    @Order(6)
    public void user_maze_solution_max_failure() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        String token = obtainAccessToken("Uche", "password");
        headers.add("Authorization", "Bearer " + token);
        mockMvc.perform(get("/maze/2/solution?step=max").headers(headers)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isBadRequest());

    }
}
