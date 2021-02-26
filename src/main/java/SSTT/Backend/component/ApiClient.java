package SSTT.Backend.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ApiClient {

    RestTemplate restTemplate = new RestTemplate();
    ObjectMapper objectMapper = new ObjectMapper();

    public Map<String, Object> getApi(String filename, String extension, String s3url, Integer subjectNum) {

        String url = "http://34.229.101.96:5000/classifier";
//        String url = "http://localhost:8080/test";

        List<String> fileInfo = new ArrayList<>();
        fileInfo.add(filename);
        fileInfo.add(extension);
        fileInfo.add(s3url);

        Map<String, Object> params = new HashMap<>();
        params.put("file_info", fileInfo);
        params.put("subject_nums", subjectNum);

        System.out.println("==========================");
        System.out.println(params.get("file_info"));
        System.out.println(params.get("subject_nums"));
        System.out.println("==========================");

        String body;

        try {
            body = objectMapper.writeValueAsString(params);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(body != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

            HttpEntity entity = new HttpEntity(body, headers);

            try {
                ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
                return response.getBody();
            } catch (Exception e){
                Map<String, Object> error = new HashMap<>();
                error.put("origin", "change");
                return error;
            }

        }

        return null;
    }
}
