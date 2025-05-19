package com.example.movie.repository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.example.movie.entity.Member;
import com.example.movie.entity.MemberRole;
import com.example.movie.entity.Movie;
import com.example.movie.entity.MovieImage;
import com.example.movie.entity.Review;

@SpringBootTest
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieImageRepository movieImageRepository;

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 리뷰 조회
    @Test
    public void testFindByMovie() {
        System.out.println(reviewRepository.findByMovie(Movie.builder().mno(10L).build()));
    }

    // 리뷰 조회(member정보 포함)
    // @Transactional : select 2개, 나눠서 가져옴 // -> EntityGraph(attributePaths =
    // "member", type
    // = EntityGraphType.FETCH) --> join(한번에 가져옴)
    @Test
    public void testFindByMovie2() {
        List<Review> list = reviewRepository.findByMovie(Movie.builder().mno(10L).build());

        for (Review review : list) {
            System.out.println(review);
            // 리뷰 작성자 조회
            System.out.println(review.getMember().getEmail());

        }
    }

    // 영화 삽입
    @Test
    public void insertMovieTest() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Movie movie = Movie.builder()
                    .title("Movie " + i)
                    .build();
            movieRepository.save(movie); // 먼저 올려두고(onetoMay x)
            // 임의의 이미지
            int count = (int) (Math.random() * 5) + 1;
            for (int j = 0; j < count; j++) {
                MovieImage movieImage = MovieImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .ord(j)
                        .imgName("test" + j + ".jpg")
                        .movie(movie) // movie가져오고
                        .build();

                // movie.addImage(movieImage);
                movieImageRepository.save(movieImage); // 저장
            }

        });
    }

    // 멤버 삽입
    @Test
    public void memeberInsertTest() {
        IntStream.rangeClosed(1, 20).forEach(i -> {
            Member member = Member.builder()
                    .email("user" + i + "@gmail.com")
                    .password(passwordEncoder.encode("1111"))
                    .memberRole(MemberRole.Memeber)
                    .nickname("viewer" + i)
                    .build();

            memberRepository.save(member);
        });
    }

    // 리뷰 삽입
    @Test
    public void reviewInsertTest() {
        // 리뷰 200개 / 영화 100 무작위로 추출/ 멤버 무작위(20)
        IntStream.rangeClosed(1, 200).forEach(i -> {
            Long mid = (long) (Math.random() * 20) + 1;
            Long mno = (long) (Math.random() * 100) + 1;
            // int grade = (int) (Math.random() * 5) + 1;

            Review review = Review.builder()
                    .grade((int) (Math.random() * 5) + 1)
                    .text("text" + i)
                    .member(Member.builder().mid(mid).build())
                    .movie(Movie.builder().mno(mno).build())
                    .build();

            reviewRepository.save(review);
        });
    }

    // list
    @Test
    public void listTest() {

        Pageable pageable = PageRequest.of(0, 10);

        Page<Object[]> result = movieImageRepository.getTotalList(null, null, pageable);
        for (Object[] objects : result) {
            // [Movie(mno=100, title=Movie 100) : Movie[0]
            // , MovieImage(inum=312, :
            // uuid=ed9a6946-051e-491d-be81-f6982965d5e1, imgName=test0.jpg, path=null,
            // ord=0), Movie[1]
            // 1, long : Movie[2]
            // 4.0] Int: Movie[3]
            System.out.println(Arrays.toString(objects));
        }
    }

    @Test
    public void getMovieTest() {
        List<Object[]> result = movieImageRepository.getMovieRow(2L);
        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
        }
        // Object[] row = result.get(0); // -> [Movie(mno=2, title=Movie 2),
        // MovieImage(inum=5,
        // uuid=be4e9d86-2378-4e15-ac05-c4a79b3ca252, imgName=test3.jpg, path=null,
        // ord=3), 1, 4.0]

        // Movie movie = (Movie) result.get(0)[0]; // -> (mno=2, title=Movie 2)
        // MovieImage movieImage = (MovieImage) result.get(0)[1]; // ->MovieImage
        // Long cnt = (Long) result.get(0)[2]; // -> 1
        // Double avg = (Double) result.get(0)[3]; // -> 4.0 ==> for문

    }
}
