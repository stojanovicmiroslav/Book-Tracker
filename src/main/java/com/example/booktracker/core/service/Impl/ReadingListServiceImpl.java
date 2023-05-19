package com.example.booktracker.core.service.Impl;


import com.example.booktracker.core.model.ReadingList;
import com.example.booktracker.core.repository.ReadingListRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ReadingListServiceImpl {
    private final ReadingListRepository readingListRepository;

    public List<ReadingList> getAllReadingLists() {
      return  readingListRepository.findAll();
    }

    public ReadingList getReadingListById(Long id) {
        return readingListRepository.findById(id).orElseThrow(() -> new RuntimeException("ReadingList not found "));
    }

    public ReadingList saveReadingList(ReadingList readingList) {
        return  readingListRepository.save(readingList);
    }

    public void deleteReadingListById(Long id) {
        readingListRepository.deleteById(id);
    }
}
