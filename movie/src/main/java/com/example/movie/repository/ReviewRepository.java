package com.example.movie.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import com.example.movie.entity.Movie;
import com.example.movie.entity.Movie.MovieBuilder;
import com.example.movie.entity.Review;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Modifying // delete, update 시 반드시 작성
    @Query("DELETE FROM Review r where r.movie = :movie")
    void deleteByMovie(Movie movie);

    // movie 아이디를 이용해 리뷰 가져오기

    @EntityGraph(attributePaths = "member", type = EntityGraphType.FETCH) // 가져올때 멤버는 무조건 처음부터 (fetch로)가져와 ->
                                                                          // transactional 없어도 됨
    List<Review> findByMovie(Movie movie); // test 해보기

}
