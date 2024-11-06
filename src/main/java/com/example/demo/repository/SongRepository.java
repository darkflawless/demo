package com.example.demo.repository;

import com.example.demo.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {


    List<Song> findTop3ByOrderByViewDesc();


    @Query(value = "SELECT * FROM Song ORDER BY RAND() LIMIT 3", nativeQuery = true)
    List<Song> findTop3Random();


    List<Song> findByNameContainingIgnoreCase(String name);



    @Query(value = "SELECT * FROM Song ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Song findRandomSong();

}