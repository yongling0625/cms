package online.lianxue.cms.system;

import online.lianxue.cms.CmsApplicationTests;
import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserControllerTest extends CmsApplicationTests {

    @Test
    public void testGetUserById() throws Exception {
        this.mockMvc.perform(get("/users/{id}", 1)
//                .header("Token", token)
        )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetUsers() throws Exception {
        this.mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
