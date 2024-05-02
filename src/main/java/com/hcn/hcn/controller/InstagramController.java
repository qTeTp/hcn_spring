package com.hcn.hcn.controller;

import com.hcn.hcn.model.InstagramData;
import com.hcn.hcn.service.InstagramService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.hcn.hcn.model.InstagramData.Comment;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class InstagramController {
    @Autowired
    private InstagramService instagramService;

    @GetMapping("/instagram")
    public InstagramData getInstagramData() throws IOException {
        return instagramService.loadInstagramData();
    }

    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        Resource file = new FileSystemResource("C:/uploads/" + filename);
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    // 게시물 아이디에 따른 댓글과 답글을 반환
    @GetMapping("/posts/{postId}/comments") //get 방식
    public List<InstagramData.Comment> getCommentsByPostId(@PathVariable int postId) throws IOException {
        InstagramData data = instagramService.loadInstagramData();
        return data.getAccounts().stream()
                .flatMap(account -> account.getPosts().stream())
                .filter(post -> post.getPost_id() == postId)
                .flatMap(post -> post.getComments().stream())
                .collect(Collectors.toList());
    }

    // 모든 댓글과 답글 정보를 반환하는 메소드
    @GetMapping("/comments") //get 방식
    public List<Comment> getAllComments() throws IOException { //Data 클래스에 만든 comments 타입으로 반환
        InstagramData data = instagramService.loadInstagramData(); //data 변수에 넣어줌
        //스트림 이해가 필요할 거 같습니다 개인 공부좀 해서 보충하겠습니다
        return data.getAccounts().stream() //계정 리스트를 가져와 스트림 생성 스트림은 데이터 컬렉션을 함수형 스타일로 처리 가능하게 해줌
                .flatMap(account -> account.getPosts().stream()) //각 계정에서 게시물을 스트림으로 만듬
                .flatMap(post -> post.getComments().stream()) //각 계정에서 댓글 리스트를 가져와 스트림으로 만듬
                .collect(Collectors.toList()); //모든 스트림을 리스트로 수집
    }

    //ID별 모든 댓글과 답글을 반환
    @GetMapping("/comments/{accountId}") //http://localhost:8080/comments/1 맨 뒤에 json에 있는 account_id 넣으면 됩니당
    public ResponseEntity<List<Comment>> getCommentsByAccountId(@PathVariable("accountId") int accountId) {
        InstagramData data = null;

        try { //예외처리 꼭 해야한다네요 위에다가 바로 넣고싶었는데 안된다 함
            data = instagramService.loadInstagramData();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        List<Comment> comments = data.getAccounts().stream()
                .filter(account -> account.getAccount_id() == accountId)
                .flatMap(account -> account.getPosts().stream())
                .flatMap(post -> post.getComments().stream())
                .collect(Collectors.toList());

        if (comments.isEmpty()) { //댓글이 없을 때
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(comments);
    }

}

