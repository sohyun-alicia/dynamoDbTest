package com.example.dynamodbtest.repository;

import com.example.dynamodbtest.model.Music;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@EnableScan
@Component
public interface MusicRepository extends CrudRepository<Music, String>{
    
    Optional<Music> findByArtist(String Artist);
    
}
