package com.github.stan256.springtest;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class SpringTestApplication {
    @Bean
    public PostDao postDao() {
        return new PostDao();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringTestApplication.class, args);
    }


    public static class Query implements GraphQLQueryResolver {
        private PostDao postDao;
        public List<Post> getRecentPosts(int count, int offset) {
            return postDao.getRecentPosts(count, offset);
        }
    }

    public static class PostDao {
        private List<Post> posts = new ArrayList<>(Arrays.asList(
                new Post("12", "First post", "Private", "1"),
                new Post("11", "Second post", "Other", "1"),
                new Post("10", "Third post", "Dogs", "1"),
                new Post("9", "Fourth post", "Book", "2"),
                new Post("8", "Fifth post", "Laptop", "2")
        ));

        public List<Post> getRecentPosts(int count, int offset) {
            return posts
                    .stream()
                    .skip(offset)
                    .limit(count)
                    .collect(Collectors.toList());
        }
    }

    @Data
    @AllArgsConstructor
    public static class Post {
        private String id;
        private String title;
        private String category;
        private String authorId;
    }

//    @Data
//    @AllArgsConstructor
//    public static class Author {
//        private String id;
//        private String name;
//        private String thumbnail;
//        private List<Post> posts;
//    }

}
