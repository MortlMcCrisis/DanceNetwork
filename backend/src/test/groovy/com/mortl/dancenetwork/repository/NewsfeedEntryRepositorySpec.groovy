package com.mortl.dancenetwork.repository

import com.mortl.dancenetwork.entity.NewsfeedEntry
import com.mortl.dancenetwork.entity.NewsfeedEntryType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.time.LocalDateTime

@SpringBootTest
class NewsfeedEntryRepositorySpec extends Specification{

    @Autowired
    NewsfeedEntryRepository newsfeedEntryRepository

    def setup() {
        newsfeedEntryRepository.deleteAll()
    }

    def cleanup() {
        newsfeedEntryRepository.deleteAll()
    }

    def "test findAllOrderByCreationDateDesc no content"(){
        expect:
        newsfeedEntryRepository.findAllOrderByCreationDateDesc().size() == 0
    }

    def "test findByPublishedTrueOrderByStartDateAsc test order"(){
        when:
        UUID userUuid1 = UUID.randomUUID();
        UUID userUuid2 = UUID.randomUUID();
        UUID userUuid3 = UUID.randomUUID();

        NewsfeedEntry newsfeedEntry1 = createTestNewsfeedEntry(userUuid1, LocalDateTime.now().minusMinutes(1))
        NewsfeedEntry newsfeedEntry2 = createTestNewsfeedEntry(userUuid2, LocalDateTime.now())
        NewsfeedEntry newsfeedEntry3 = createTestNewsfeedEntry(userUuid3, LocalDateTime.now().plusMinutes(1))

        newsfeedEntryRepository.saveAndFlush(newsfeedEntry1)
        newsfeedEntryRepository.saveAndFlush(newsfeedEntry2)
        newsfeedEntryRepository.saveAndFlush(newsfeedEntry3)

        List<NewsfeedEntry> newsfeedEntry = newsfeedEntryRepository.findAllOrderByCreationDateDesc()

        then:
        newsfeedEntry.size() == 3

        newsfeedEntry.get(0).getCreator() == userUuid3
        newsfeedEntry.get(1).getCreator() == userUuid2
        newsfeedEntry.get(2).getCreator() == userUuid1
    }

    def createTestNewsfeedEntry(UUID creator, LocalDateTime creationDate) {
        new NewsfeedEntry(
                null,
                NewsfeedEntryType.POST,
                creator,
                "text",
                null,
                creationDate)
    }
}
