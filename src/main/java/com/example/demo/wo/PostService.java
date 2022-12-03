package com.example.demo.wo;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

@Component
public class PostService implements GraphQLQueryResolver {

    /**
     * 为了测试，只查询id为1的结果
     */
    public Post getPostById(int id){
        if(id == 1){
            User user = new User(1,"javadaily","JAVA日知录","zhangsan@qq.com");
            Post post = new Post(1,"Hello,Graphql","Graphql初体验","日记");
            post.setUser(user);
            return post;
        }else{
            return null;
        }

    }
}
