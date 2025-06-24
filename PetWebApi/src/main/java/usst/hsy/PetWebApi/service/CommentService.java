package usst.hsy.PetWebApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import usst.hsy.PetWebApi.entity.Comment;
import usst.hsy.PetWebApi.entity.Post;
import usst.hsy.PetWebApi.entity.User;
import usst.hsy.PetWebApi.repository.CommentRepository;
import usst.hsy.PetWebApi.repository.PostRepository;
import usst.hsy.PetWebApi.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostService postService;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository, 
                         UserRepository userRepository, PostService postService) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postService = postService;
    }

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public List<Comment> findByPostId(Integer postId) {
        return commentRepository.findByPostId(postId);
    }

    public List<Comment> findRootCommentsByPostId(Integer postId) {
        return commentRepository.findByPostIdAndParentCommentIsNull(postId);
    }

    public List<Comment> findRepliesByParentCommentId(Integer parentCommentId) {
        return commentRepository.findByParentCommentId(parentCommentId);
    }

    public List<Comment> findByUserId(Integer userId) {
        return commentRepository.findByUserId(userId);
    }

    public Optional<Comment> findById(Integer id) {
        return commentRepository.findById(id);
    }

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Transactional
    public Comment createRootComment(Comment comment, Integer postId, Integer userId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        Optional<User> optionalUser = userRepository.findById(userId);
        
        if (optionalPost.isPresent() && optionalUser.isPresent()) {
            Post post = optionalPost.get();
            User user = optionalUser.get();
            
            comment.setPost(post);
            comment.setUser(user);
            comment.setParentComment(null);
            
            Comment savedComment = commentRepository.save(comment);
            
            // 更新帖子评论计数
            postService.incrementComments(postId);
            
            return savedComment;
        } else {
            throw new RuntimeException("帖子或用户不存在！");
        }
    }

    @Transactional
    public Comment createReply(Comment comment, Integer postId, Integer userId, Integer parentCommentId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Comment> optionalParentComment = commentRepository.findById(parentCommentId);
        
        if (optionalPost.isPresent() && optionalUser.isPresent() && optionalParentComment.isPresent()) {
            Post post = optionalPost.get();
            User user = optionalUser.get();
            Comment parentComment = optionalParentComment.get();
            
            comment.setPost(post);
            comment.setUser(user);
            comment.setParentComment(parentComment);
            
            Comment savedComment = commentRepository.save(comment);
            
            // 更新帖子评论计数
            postService.incrementComments(postId);
            
            return savedComment;
        } else {
            throw new RuntimeException("帖子、用户或父评论不存在！");
        }
    }

    public Comment update(Comment comment) {
        Optional<Comment> optionalComment = commentRepository.findById(comment.getId());
        if (optionalComment.isPresent()) {
            Comment existingComment = optionalComment.get();
            
            // 仅允许更新内容
            existingComment.setContent(comment.getContent());
            
            return commentRepository.save(existingComment);
        } else {
            throw new RuntimeException("评论不存在！");
        }
    }

    @Transactional
    public void deleteById(Integer id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            Integer postId = comment.getPost().getId();
            
            // 删除评论
            commentRepository.deleteById(id);
            
            // 更新帖子评论计数
            postService.decrementComments(postId);
        } else {
            throw new RuntimeException("评论不存在！");
        }
    }

    public Long countByPostId(Integer postId) {
        return commentRepository.countByPostId(postId);
    }
} 