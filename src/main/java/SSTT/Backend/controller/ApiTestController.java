package SSTT.Backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ApiTestController {
    @PostMapping("/test")
    public Map<String, Object> test(@RequestBody Map<String, Object> params) {
        System.out.println("==========================");
        System.out.println(params);
        System.out.println(params.get("subject_nums"));
        System.out.println(params.get("file_info"));
        System.out.println("==========================");

        // 결과로 전송할 데이터
        List<String> titleList = new ArrayList<>();
        titleList.add("test1");
        titleList.add("test2");
        List<String> descList = new ArrayList<>();
        descList.add("desc1");
        descList.add("desc2");
        List<String> tagList = new ArrayList<>();
        tagList.add("tag1");
        tagList.add("tag2");
        tagList.add("tag3");

        Map<String, Object> result = new HashMap<>();
        result.put("origin", "origin text");
        result.put("title", titleList);
        result.put("desc", descList);
        result.put("tagList", tagList);

        return result;
    }
}
