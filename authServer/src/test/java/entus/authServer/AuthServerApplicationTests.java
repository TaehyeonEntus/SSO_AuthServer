package entus.authServer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class AuthServerApplicationTests {

	@MockitoBean
	private RedisTemplate<?, ?> redisTemplate;

	@Test
	void contextLoads() {
	}

}
