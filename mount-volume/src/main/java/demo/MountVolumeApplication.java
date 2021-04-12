package demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Paths;

@RestController
@SpringBootApplication
public class MountVolumeApplication {
    @Value("${mounted.volume}")
    private String volume;

    @GetMapping
    public String[] index() {
        return Paths.get(volume).toFile().list();
    }

    public static void main(String[] args) {
        SpringApplication.run(MountVolumeApplication.class, args);
    }
}
