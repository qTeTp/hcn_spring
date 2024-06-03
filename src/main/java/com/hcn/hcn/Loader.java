package com.hcn.hcn;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcn.hcn.model.WebUserData;
import com.hcn.hcn.repository.WebUserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

//json 읽어오는 클래스
@Component
public class Loader implements ApplicationRunner {

    private static Map<String, String> userCredentials = new HashMap<>();

    @Autowired
    private WebUserDataRepository webUserDataRepository;

    //오버라이드해서 시작할 때 실행함
    @Override
    public void run(ApplicationArguments args) throws Exception { //시작 시 실행
        userCredentials.clear(); //초기화
        //가져온 정보 map에다가 넣어줌
        Iterable<WebUserData> users = webUserDataRepository.findAll(); //<- jpa의 메소드
        for (WebUserData user : users) {
            userCredentials.put(user.getUserId(), user.getPassword());
        }
    }

    public static Map<String, String> getUserCredentials() { //map의 getter
        return userCredentials;
    }

    public static void addUserCredential(String userId, String password) { //map에 추가할 때
        userCredentials.put(userId, password);
        System.out.println(userCredentials);
    }

    public static Map<String, Object> loadJsonData(String filePath) { //json 불러오는 메소드
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // classpath 접두사를 처리하여 파일을 읽음
            Resource resource = new ClassPathResource(filePath.replace("classpath:", ""));
            InputStream inputStream = resource.getInputStream();
            return objectMapper.readValue(inputStream, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
