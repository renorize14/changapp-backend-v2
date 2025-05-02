package cl.changapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import cl.changapp.dto.PostResponse;
import cl.changapp.entity.notrelated.News;
import cl.changapp.repository.notrelated.NewsRepository;
import cl.changapp.service.LikeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/news")
@SecurityRequirement(name = "bearerAuth")
public class NewsController {
	
	final static double EARTH_RADIUS = 6371.0;	
	private NewsRepository newsRepository;
	private LikeService likeService;
	
	public NewsController(NewsRepository newsRepository, LikeService likeService) {
		this.newsRepository = newsRepository;
		this.likeService = likeService;
	}
	
	@GetMapping("/all")
    public List<PostResponse> getAllNews(@PathParam("lat") String lat, @PathParam("lon") String lon) {
    	try {
    		List<News> news = newsRepository.findAll(Sort.by(Sort.Direction.DESC, "timestamp"));
    		List<PostResponse> finalNews = new ArrayList<>();
    		Double lat1 = Double.parseDouble(lat);
        	Double lon1 = Double.parseDouble(lon);
    		
    		for ( int i = 0 ; i < news.size() ; i ++ ) {
    			Double lat2 = Double.parseDouble(news.get(i).getGeoreference().split(",")[0].toString());
            	Double lon2 = Double.parseDouble(news.get(i).getGeoreference().split(",")[1].toString());
            	
            	if ( isWithinRange(lat1, lon1, lat2, lon2, 30) ) {
            		PostResponse post = new PostResponse();
            		
            		post.set_id(news.get(i).get_id());
            		post.setActivo(news.get(i).getActivo());
            		post.setBody(news.get(i).getBody());
            		post.setGeoreference(news.get(i).getGeoreference());
            		post.setProfilePicture(news.get(i).getProfilePicture());
            		post.setNickname(news.get(i).getNickname());
            		post.setSport(news.get(i).getSport());
            		post.setTimestamp(news.get(i).getTimestamp());
            		post.setTopic(news.get(i).getTopic());
            		post.setUser_id(news.get(i).getUser_id());
            		post.setLikesCount(likeService.getLikesCount(news.get(i).get_id()));
            		post.setLikedByUser(likeService.hasUserLiked(news.get(i).get_id(), news.get(i).getUser_id() + ""));
            		finalNews.add(post);
            	}
    			
    		}
    		
    		return finalNews;
    		
		} catch (Exception e) {
			System.out.println(e);
			return null;			
		}
    }
	
	// Crear una noticia
    @PostMapping
    public ResponseEntity<News> createNews(@RequestBody News news) {
        News savedNews = newsRepository.save(news);
        return ResponseEntity.ok(savedNews);
    }

    // Eliminar una noticia por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable String id) {
        if (!newsRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        newsRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
	
	public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
    	
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        System.out.println(EARTH_RADIUS * c);
        return EARTH_RADIUS * c;
    }
    
    public boolean isWithinRange(double lat1, double lon1, double lat2, double lon2, double radius) {
        double distance = calculateDistance(lat1, lon1, lat2, lon2);
        return distance <= radius;
    }

}
