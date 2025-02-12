package io.javabrains.moviecatalogservice.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.netflix.discovery.DiscoveryClient;

import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.Movie;
import io.javabrains.moviecatalogservice.models.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private DiscoveryClient DiscoveryClient; //같은 이름의 서비스가 여러개 있늘때 포트를 지정해서 해당포트경로로 보내준다
	
	@Autowired
	private WebClient.Builder WebClientBuilder;
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

		// get all rated movie IDs
		
		
		// rating-data-service에서 데이터를 가지고 온다
		//eureka에서 loadbalance를 사용하여 서비스 이름으로 서비스 주소를 찾아준다
		UserRating ratings = restTemplate.getForObject("http://ratings-data-service/ratingsdata/user/"+userId,
				UserRating.class);

		return ratings.getRatings().stream().map(rating -> {
			//영화객체 가져오기
			// for each movie ID, call movie info service and get details
			Movie movie = restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId(), Movie.class);
			/*
			 * Movie movie = WebClientBuilder.build() //위 코드를 빌더패턴을 사용하여 가져온다 .get()
			 * .uri("http://localhost:8083/movies/"+rating.getMovieId()) //접근할 uri
			 * .retrieve() //fatch한다 .bodyToMono(Movie.class) //바디의 내용을 Movie클래스로 가지고 온다
			 * //mono 언젠가는 객체를 가지고 오겠다! Reactive문법 .block();
			 */
			
			// put them all together
			return new CatalogItem(movie.getName(), "Desc", rating.getRating());
		})
		.collect(Collectors.toList());
		

		

	}
}
