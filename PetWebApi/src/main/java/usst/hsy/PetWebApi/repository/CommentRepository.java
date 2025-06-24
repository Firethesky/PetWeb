package usst.hsy.PetWebApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import usst.hsy.PetWebApi.entity.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByPostId(Integer postId);
    List<Comment> findByPostIdAndParentCommentIsNull(Integer postId);
    List<Comment> findByParentCommentId(Integer parentCommentId);
    List<Comment> findByUserId(Integer userId);
    Long countByPostId(Integer postId);
} 