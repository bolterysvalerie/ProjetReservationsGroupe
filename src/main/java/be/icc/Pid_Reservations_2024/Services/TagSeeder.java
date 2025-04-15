package be.icc.Pid_Reservations_2024.Services;

import be.icc.Pid_Reservations_2024.Models.Tag;
import be.icc.Pid_Reservations_2024.Repositories.TagRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagSeeder implements CommandLineRunner {

    private final TagRepository tagRepository;

    public TagSeeder(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public void run(String... args) {
        if (tagRepository.count() == 0) {
            List<Tag> tags = List.of(
                    new Tag("Action"),
                    new Tag("Comedy"),
                    new Tag("Drama")
            );
            tagRepository.saveAll(tags);
        }
    }
}
