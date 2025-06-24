package usst.hsy.PetWebApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import usst.hsy.PetWebApi.entity.Comment;
import usst.hsy.PetWebApi.service.CommentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments() {
        return ResponseEntity.ok(commentService.findAll());
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Integer postId) {
        return ResponseEntity.ok(commentService.findByPostId(postId));
    }

    @GetMapping("/post/{postId}/root")
    public ResponseEntity<List<Comment>> getRootCommentsByPostId(@PathVariable Integer postId) {
        return ResponseEntity.ok(commentService.findRootCommentsByPostId(postId));
    }

    @GetMapping("/parent/{parentCommentId}/replies")
    public ResponseEntity<List<Comment>> getRepliesByParentCommentId(@PathVariable Integer parentCommentId) {
        return ResponseEntity.ok(commentService.findRepliesByParentCommentId(parentCommentId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Comment>> getCommentsByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(commentService.findByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Integer id) {
        return commentService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/post/{postId}/user/{userId}")
    public ResponseEntity<?> createRootComment(
            @RequestBody Comment comment,
            @PathVariable Integer postId,
            @PathVariable Integer userId) {
        try {
            Comment createdComment = commentService.createRootComment(comment, postId, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/post/{postId}/user/{userId}/parent/{parentCommentId}")
    public ResponseEntity<?> createReply(
            @RequestBody Comment comment,
            @PathVariable Integer postId,
            @PathVariable Integer userId,
            @PathVariable Integer parentCommentId) {
        try {
            Comment createdComment = commentService.createReply(comment, postId, userId, parentCommentId);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(
            @PathVariable Integer id,
            @RequestBody Comment comment) {
        if (!id.equals(comment.getId())) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "路径ID与评论ID不匹配");
            return ResponseEntity.badRequest().body(response);
        }
        
        try {
            Comment updatedComment = commentService.update(comment);
            return ResponseEntity.ok(updatedComment);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer id) {
        try {
            commentService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "删除评论失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/post/{postId}/count")
    public ResponseEntity<Map<String, Long>> countCommentsByPostId(@PathVariable Integer postId) {
        Long count = commentService.countByPostId(postId);
        Map<String, Long> response = new HashMap<>();
        response.put("count", count);
        return ResponseEntity.ok(response);
    }
} 