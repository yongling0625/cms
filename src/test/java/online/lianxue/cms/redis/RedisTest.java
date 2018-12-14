package online.lianxue.cms.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testSetKey() {
        redisTemplate.expireAt("mykey", new Date(System.currentTimeMillis() + 1000 * 10));
        redisTemplate.opsForValue().set("mykey", "水电费是多少");
//        redisTemplate.boundHashOps("test:cache").put("1", "2");


    }

    @Test
    public void testGetByKey() {
        Object mykey = redisTemplate.opsForValue().get("mykey");
        System.out.println(mykey);
    }


}
