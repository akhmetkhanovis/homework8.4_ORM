package ru.netology.app;

import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import ru.netology.entity.Person;
import ru.netology.repository.PersonRepo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@SpringBootApplication(scanBasePackages = "ru.netology")
@EntityScan(basePackages = "ru.netology")

public class Application implements CommandLineRunner {

    @PersistenceContext
    private EntityManager entityManager;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run();
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        var names = List.of("Ivan", "Vlad", "Alexey", "Sergey", "Anton", "Andrey");
        var surnames = List.of("Ivanov", "Petrov", "Saburov", "Scherbakov", "Usovich", "Masaev", "Makarov");
        var cities = List.of("Moskva", "SPB", "Ufa", "Tver", "Sochi", "Perm");
        var random = new Random();

        IntStream.range(0, 50)
                .forEach(i -> {
                    var person = Person.builder()
                            .name(names.get(random.nextInt(names.size())))
                            .surname(surnames.get(random.nextInt(names.size())))
                            .age(random.nextInt(10) + 21)
                            .phoneNumber("+7-999-888-00-" + ((i < 10) ? ("0" + i) : i))
                            .cityOfLiving(cities.get(random.nextInt(cities.size())))
                            .build();

                    entityManager.persist(person);
                });


    }

}

