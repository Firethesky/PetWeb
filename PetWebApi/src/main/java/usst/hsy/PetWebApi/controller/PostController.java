package usst.hsy.PetWebApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import usst.hsy.PetWebApi.entity.Post;
import usst.hsy.PetWebApi.service.PostService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<Page<Post>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ? 
                Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return ResponseEntity.ok(postService.findAll(pageable));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<Post>> getPostsByUserId(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return ResponseEntity.ok(postService.findByUserId(userId, pageable));
    }

    @GetMapping("/top/likes")
    public ResponseEntity<List<Post>> getTopPostsByLikes() {
        return ResponseEntity.ok(postService.findTopByLikes());
    }

    @GetMapping("/top/comments")
    public ResponseEntity<List<Post>> getTopPostsByComments() {
        return ResponseEntity.ok(postService.findTopByComments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Integer id) {
        return postService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<?> createPost(
            @RequestBody Post post,
            @PathVariable Integer userId) {
        try {
            Post createdPost = postService.create(post, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(
            @PathVariable Integer id,
            @RequestBody Post post) {
        if (!id.equals(post.getId())) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "路径ID与帖子ID不匹配");
            return ResponseEntity.badRequest().body(response);
        }
        
        try {
            Post updatedPost = postService.update(post);
            return ResponseEntity.ok(updatedPost);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PatchMapping("/{id}/like")
    public ResponseEntity<?> likePost(@PathVariable Integer id) {
        try {
            Post likedPost = postService.incrementLikes(id);
            return ResponseEntity.ok(likedPost);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PatchMapping("/{id}/unlike")
    public ResponseEntity<?> unlikePost(@PathVariable Integer id) {
        try {
            Post unlikedPost = postService.decrementLikes(id);
            return ResponseEntity.ok(unlikedPost);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Integer id) {
        try {
            postService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "删除帖子失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
} 