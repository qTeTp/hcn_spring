package com.hcn.hcn.controller;

import com.hcn.hcn.Loader;
import com.hcn.hcn.model.WebUserData;
import com.hcn.hcn.repository.WebUserDataRepository;
import com.hcn.hcn.service.WebUserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Map;
import java.util.Set;

@RestController
public class WebUserController {

    @Autowired
    private WebUserDataRepository webUserDataRepository;
    @Autowired
    private WebUserDataService webUserDataService;

    private static final String UPLOAD_DIR = "C:/uploads/";
    //회원가입
    @PostMapping("/SignUp")
    public String signUp(@RequestBody WebUserData webUserData) {
        webUserDataRepository.save(webUserData);
        // 메모리 갱신
        Loader.addUserCredential(webUserData.getUserId(), webUserData.getPassword());
        System.out.println("Received user data: " + webUserData);
        return "User signed up successfully";
    }

    // 아이디 중복 확인
    @GetMapping("/duplicationId/{wantId}")
    public int duplicationId(@PathVariable("wantId") String wantId) {
        Map<String, String> userCredentials = Loader.getUserCredentials();
        if (userCredentials.containsKey(wantId)) {return 0;}  //중복
        else {return 1;} //중복 아님
    }

    // 앱에서 회원가입
    @PostMapping("/AppSignUp")
    public ResponseEntity<String> appSignUp(@RequestBody Map<String, String> appUserData) {
        WebUserData webUserData = new WebUserData();
        webUserData.setName(appUserData.get("name"));
        webUserData.setUserId(appUserData.get("userId"));
        webUserData.setPassword(appUserData.get("password"));

        webUserDataRepository.save(webUserData);
        // 메모리 갱신
//        Loader.addUserCredential(webUserData.getUserId(), webUserData.getPassword());
        System.out.println("Received user data from app: " + webUserData);
        return ResponseEntity.ok("User signed up successfully from app");
    }

    //로그인
    @PostMapping("/login") //포스트 방식
    public Map<String, Object> login(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");

        Map<String, String> userCredentials = Loader.getUserCredentials();
        if (userCredentials.containsKey(email) && userCredentials.get(email).equals(password)) {
            String filePath = "./insta.json";
            Map<String, Object> jsonData = Loader.loadJsonData(filePath);
            jsonData.put("status", "success");
            return jsonData;
        } else {
            return Map.of("status", "error", "message", "Invalid credentials");
        }
    }

    //사용자 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<WebUserData> getUserData(@PathVariable("userId") String userId) {
        System.out.printf(userId);
        WebUserData userData = webUserDataRepository.findByUserId(userId);
        if (userData == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userData);
    }


    //파일 업로드
    @PostMapping("/upload")
    public ResponseEntity<String> uploadProfileImage(@RequestParam("file") MultipartFile file, @RequestParam("userId") String userId) {
        try {
            // 파일 저장
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename(); // 파일 이름에 타임스탬프 추가
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.write(path, file.getBytes());

            // DB 업데이트
            WebUserData userData = webUserDataRepository.findByUserId(userId);
            if (userData == null) {
                return ResponseEntity.notFound().build();
            }
            userData.setProfileImageUrl(fileName);
            webUserDataRepository.save(userData);

            return ResponseEntity.ok(fileName); // 업로드된 파일 이름을 반환
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload file");
        }
    }

    @PostMapping("/update")
    public ResponseEntity<WebUserData> updateUserData(@RequestBody WebUserData userData) {
        WebUserData existingUserData = webUserDataRepository.findByUserId(userData.getUserId());
        if (existingUserData == null) {
            return ResponseEntity.notFound().build();
        }
        existingUserData.setCompanyDescription(userData.getCompanyDescription());
        existingUserData.setCategory(userData.getCategory());
        existingUserData.setAgeGroups(userData.getAgeGroups());
        // 기타 필드 업데이트

        webUserDataRepository.save(existingUserData);
        return ResponseEntity.ok(existingUserData);
    }

    // 금액 충전
    @PostMapping("/charge")
    public ResponseEntity<WebUserData> chargePoints(@RequestBody Map<String, Object> chargeData) {
        String userId = (String) chargeData.get("userId");
        Integer amount = (Integer) chargeData.get("amount");

        WebUserData userData = webUserDataRepository.findByUserId(userId);
        if (userData == null) {
            return ResponseEntity.notFound().build();
        }

        userData.setPoints(userData.getPoints() + amount);
        webUserDataRepository.save(userData);

        return ResponseEntity.ok(userData);
    }
}
