package idusw.springboot.boradthymleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  //(exclude = DataSourceAutoConfiguration.class)
public class BoardCshApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardCshApplication.class, args);
    }

}
