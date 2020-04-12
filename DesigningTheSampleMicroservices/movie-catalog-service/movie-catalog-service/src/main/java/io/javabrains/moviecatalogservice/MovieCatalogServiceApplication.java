package io.javabrains.moviecatalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication

public class MovieCatalogServiceApplication {
	
	@Bean
	@LoadBalanced //서비스를 찾아주는 역할 //url을 던저주면 서버에가서 목적지를 알림
	/*@LoadBalanced
	 * 만약 포트가 다르고 같은 이름의 서비스가 여러개 존재한다면
	 * 로드밸런스 어노테이션을 사용하여
	 * 균형있게 분배해준다
	 */
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public WebClient.Builder getWebClientBuilder(){
		return WebClient.builder();

	}
	
	public static void main(String[] args) {
		SpringApplication.run(MovieCatalogServiceApplication.class, args);
	}

}
