package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.net.InetAddress.getLocalHost;
import static java.util.Collections.singletonMap;

@RestController
@SpringBootApplication
public class ServiceOneApplication {
    @Autowired
    private Environment environment;

    @GetMapping
    public Map<String, String> one() throws Exception {
        return singletonMap(
                getLocalHost().getHostName(),
                environment.getProperty("local.server.port")
        );
    }

    @GetMapping("/all")
    public Map<String, String> all() throws Exception {
        Map<String, String> response = new LinkedHashMap<>(one());
        response.putAll(two());
        return response;
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> two() {
        String url = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(environment.getProperty("SERVICE_TWO_HOST"))
                .port(environment.getProperty("SERVICE_TWO_PORT"))
                .build()
                .toString();
        return new RestTemplate().getForObject(url, Map.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ServiceOneApplication.class, args);
    }
}
