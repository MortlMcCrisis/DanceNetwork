package com.mortl.dancenetwork.repository;

import com.mortl.dancenetwork.model.NewsfeedEntry;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsfeedEntryRepository extends JpaRepository<NewsfeedEntry, Long> {

  List<NewsfeedEntry> findAllByOrderByCreationDateDesc();
}
