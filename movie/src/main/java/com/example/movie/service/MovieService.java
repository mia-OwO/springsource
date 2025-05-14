package com.example.movie.service;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.movie.dto.MovieDTO;
import com.example.movie.dto.MovieImageDTO;
import com.example.movie.dto.PageRequestDTO;
import com.example.movie.dto.PageResultDTO;
import com.example.movie.entity.Movie;
import com.example.movie.entity.MovieImage;
import com.example.movie.repository.MovieImageRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class MovieService {

    public final MovieImageRepository movieImageRepository;

    // PageRequestDTO: 페이지 정보 -> 가지고 오기
    public PageResultDTO<MovieDTO> getList(PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(),
                Sort.by("mno").descending());
        Page<Object[]> result = movieImageRepository.getTotalList(null, null, pageable);
        //
        // [Movie(mno=91, title=Movie 91), MovieImage(inum=285,
        // uuid=cc895070-151d-4177-88d1-9ea22411ed0f, imgName=test0.jpg, path=null,
        // ord=0), 2, 4.0]
        // -> 바꾸기

        Function<Object[], MovieDTO> function = (en -> entityToDto((Movie) en[0],
                (List<MovieImage>) Arrays.asList((MovieImage) en[1]), (Long) en[2],
                (Double) en[3]));

        List<MovieDTO> dtoList = result.stream().map(function).collect(Collectors.toList());
        Long totalCount = result.getTotalElements();

        PageResultDTO<MovieDTO> pageResultDTO = PageResultDTO.<MovieDTO>withAll()
                .dtoList(dtoList)
                .totalCount(totalCount)
                .pageRequestDTO(pageRequestDTO)
                .build();
        return pageResultDTO;
    }

    private MovieDTO entityToDto(Movie movie, List<MovieImage> movieImages, Long count, Double avg) {
        MovieDTO movieDTO = MovieDTO.builder()
                .mno(movie.getMno())
                .title(movie.getTitle())
                .createdDate(movie.getCreateDate())
                .build();
        // 이미지 정보 담기
        List<MovieImageDTO> mImageDTOs = movieImages.stream().map(movieImage -> {
            return MovieImageDTO.builder()
                    .inum(movieImage.getInum())
                    .uuid(movieImage.getUuid())
                    .imgName(movieImage.getImgName())
                    .path(movieImage.getPath())
                    .build();
        }).collect(Collectors.toList());
        movieDTO.setMovieImages(mImageDTOs);
        movieDTO.setAvg(avg != null ? avg : 0.0);
        movieDTO.setReviewCnt(count);
        return movieDTO;
    }

}
