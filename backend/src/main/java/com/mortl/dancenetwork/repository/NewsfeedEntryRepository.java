package com.mortl.dancenetwork.repository;

import com.mortl.dancenetwork.model.NewsfeedEntry;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NewsfeedEntryRepository extends JpaRepository<NewsfeedEntry, Long> {

  @Query("SELECT newsfeedEntry.id FROM NewsfeedEntry newsfeedEntry ORDER BY newsfeedEntry.creationDate DESC ")
  List<Long> findAllIdsOrderByCreationDateDesc();
}
