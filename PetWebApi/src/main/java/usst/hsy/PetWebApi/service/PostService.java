package usst.hsy.PetWebApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import usst.hsy.PetWebApi.entity.Post;
import usst.hsy.PetWebApi.entity.User;
import usst.hsy.PetWebApi.repository.PostRepository;
import usst.hsy.PetWebApi.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Page<Post> findByUserId(Integer userId, Pageable pageable) {
        return postRepository.findByUserId(userId, pageable);
    }

    public List<Post> findTopByLikes() {
        return postRepository.findTop5ByOrderByLikesCountDesc();
    }

    public List<Post> findTopByComments() {
        return postRepository.findTop5ByOrderByCommentsCountDesc();
    }

    public Optional<Post> findById(Integer id) {
        return postRepository.findById(id);
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public Post create(Post post, Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            post.setUser(user);
            post.setLikesCount(0);
            post.setCommentsCount(0);
            return postRepository.save(post);
        } else {
            throw new RuntimeException("用户不存在！");
        }
    }

    public Post update(Post post) {
        Optional<Post> optionalPost = postRepository.findById(post.getId());
        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get();
            
            // 只更新内容和标题，其他数据如点赞数等不应通过此方法修改
            existingPost.setTitle(post.getTitle());
            existingPost.setContent(post.getContent());
            existingPost.setImageUrls(post.getImageUrls());
            
            return postRepository.save(existingPost);
        } else {
            throw new RuntimeException("帖子不存在！");
        }
    }

    public Post incrementLikes(Integer id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setLikesCount(post.getLikesCount() + 1);
            return postRepository.save(post);
        } else {
            throw new RuntimeException("帖子不存在！");
        }
    }

    public Post decrementLikes(Integer id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (post.getLikesCount() > 0) {
                post.setLikesCount(post.getLikesCount() - 1);
            }
            return postRepository.save(post);
        } else {
            throw new RuntimeException("帖子不存在！");
        }
    }

    public Post incrementComments(Integer id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setCommentsCount(post.getCommentsCount() + 1);
            return postRepository.save(post);
        } else {
            throw new RuntimeException("帖子不存在！");
        }
    }

    public Post decrementComments(Integer id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (post.getCommentsCount() > 0) {
                post.setCommentsCount(post.getCommentsCount() - 1);
            }
            return postRepository.save(post);
        } else {
            throw new RuntimeException("帖子不存在！");
        }
    }

    public void deleteById(Integer id) {
        postRepository.deleteById(id);
    }
} 