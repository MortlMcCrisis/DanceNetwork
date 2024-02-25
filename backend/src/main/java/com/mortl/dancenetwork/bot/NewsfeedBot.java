package com.mortl.dancenetwork.bot;

import com.mortl.dancenetwork.entity.User;
import com.mortl.dancenetwork.model.NewsfeedEntry;
import com.mortl.dancenetwork.repository.NewsfeedEntryRepository;
import com.mortl.dancenetwork.service.UserService;
import de.svenjacobs.loremipsum.LoremIpsum;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NewsfeedBot {

  private NewsfeedEntryRepository newsfeedEntryRepository;

  private LoremIpsum loremIpsum;

  private List<User> users;

  public NewsfeedBot(UserService userService, NewsfeedEntryRepository newsfeedEntryRepository){
    this.newsfeedEntryRepository = newsfeedEntryRepository;
    loremIpsum = new LoremIpsum();
    users = userService.getAllUsers();
  }

  @Scheduled(fixedRate = 24000000)
  public void reportCurrentTime() {

    users.stream().forEach(user -> createRandomNewsfeed(user));
  }
  private void createRandomNewsfeed(User user){
    Random random = new Random();

    // Generieren Sie einen Zufallswert zwischen 20 und 3000
    int minValue = 20;
    int maxValue = 200;
    int randomValue = random.nextInt(maxValue - minValue + 1) + minValue;

    newsfeedEntryRepository.save(NewsfeedEntry.builder()
        .creator(user.uuid())
        .textField(loremIpsum.getWords(randomValue))
        .creationDate(LocalDateTime.now())
        .build());
  }
}
