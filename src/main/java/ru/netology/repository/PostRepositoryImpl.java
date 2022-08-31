package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepositoryImpl implements PostRepository {
    private final Map<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicLong identifier = new AtomicLong();

    public List<Post> all() {
        return new ArrayList<>(posts.values());
    }

    public Optional<Post> getById(Long id) {
        return Optional.ofNullable(posts.get(id));
    }

    public Post save(Post post) {
        if(post.getId() != 0 && !(posts.containsKey(post.getId()))){
            throw new NotFoundException();
        }
        if(post.getId() == 0){
            post.setId(identifier.incrementAndGet());
        }

        posts.put(post.getId(), post);
        return post;
    }

    public void removeById(Long id) {
        if(posts.remove(id) == null){
            throw new NotFoundException();
        }
    }
}