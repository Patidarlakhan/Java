package lakhan.patidar.runnerz;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import lakhan.patidar.runnerz.run.Location;
import lakhan.patidar.runnerz.run.Run;
import lakhan.patidar.runnerz.user.User;
import lakhan.patidar.runnerz.user.UserHttpClient;
import lakhan.patidar.runnerz.user.UserRestClient;
import lakhan.patidar.runnerz.run.JdbcClientRunRepository;



@SpringBootApplication
public class RunnerzApplication {

	public static final Logger log = LoggerFactory.getLogger(RunnerzApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RunnerzApplication.class, args);
	
	}

	@Bean
	UserHttpClient userHttpClient(){
		RestClient restClient = RestClient.create("https://jsonplaceholder.typicode.com/");
		HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
		return factory.createClient(UserHttpClient.class);
	}

	@Bean
	CommandLineRunner runner(UserHttpClient client){
		return args -> {
			List<User> users = client.findAll();
			System.out.println(users);

			User user1 = client.finsById(1);
			System.out.println(user1);
		};
	}
}
