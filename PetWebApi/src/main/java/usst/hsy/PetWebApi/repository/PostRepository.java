package usst.hsy.PetWebApi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import usst.hsy.PetWebApi.entity.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Page<Post> findAll(Pageable pageable);
    Page<Post> findByUserId(Integer userId, Pageable pageable);
    List<Post> findTop5ByOrderByLikesCountDesc();
    List<Post> findTop5ByOrderByCommentsCountDesc();
} 