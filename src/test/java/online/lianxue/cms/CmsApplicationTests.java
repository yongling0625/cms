package online.lianxue.cms;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CmsApplicationTests {

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    protected String token;

    @Before
    public void setUp() throws Exception {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .build();

        MvcResult mvcResult = this.mockMvc.perform(post("/login")
                .param("username", "xiaoming")
                .param("password", "123456"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        HashMap hashMap = new ObjectMapper().readValue(contentAsString, HashMap.class);
        this.token = hashMap.get("data").toString();
    }

    @Test
    public void testLogin() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(post("/login")
                .param("username", "xiaoming")
                .param("password", "123456"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        HashMap hashMap = new ObjectMapper().readValue(contentAsString, HashMap.class);
        this.token = hashMap.get("data").toString();
        System.out.println(token);

    }
}
