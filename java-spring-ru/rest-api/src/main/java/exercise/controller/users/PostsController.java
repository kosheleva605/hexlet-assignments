package exercise.controller.users;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import exercise.model.Post;
import exercise.Data;

// BEGIN
@RestController
@RequestMapping("/api/users")
public class PostsController {
    private List<Post> posts = Data.getPosts();
    private List<Post> result;

    @GetMapping("/{id}/posts")
    public List<Post> getUsersPosts(@PathVariable int id) {
        var result = posts.stream()
                .filter(it -> it.getUserId()== id)
                .toList();
        return result;
    }

    @PostMapping("/{id}/posts") // создание нового поста
    @ResponseStatus(HttpStatus.CREATED)
    public Post createUserPost(@PathVariable int id, @RequestBody Post post) {
        post.setUserId(id);
        posts.add(post);
        return post;
    }
}
// END
