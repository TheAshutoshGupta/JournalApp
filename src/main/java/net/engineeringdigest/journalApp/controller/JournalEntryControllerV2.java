package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.repository.JournalEntryRepo;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/_journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> getAll() {
        return journalEntryService.getAllEntries();
    }


    @GetMapping("/all/{myId}")
    public ResponseEntity<?> getEntry(@PathVariable ObjectId myId) {
        Optional<JournalEntry> response = journalEntryService.findById(myId);
        if(response.isPresent()){
            return new ResponseEntity<>(response.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("not found",HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add")
    public JournalEntry add(@RequestBody JournalEntry myEntry) {
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);
        return myEntry;
    }

    @GetMapping("/delete/{myId}")
    public Boolean deleteById(ObjectId myId){
        journalEntryService.deleteById(myId);
        return true;
    }

    @PutMapping("/id/{myId}")
    public JournalEntry updateById(@PathVariable ObjectId myId, @RequestBody JournalEntry myEntry){
        JournalEntry old = journalEntryService.findById(myId).orElse(null);
        if(old!=null){
            old.setTitle(myEntry.getTitle()!=null&&!myEntry.getTitle().equals("")?myEntry.getTitle(): old.getTitle());
            old.setContent(myEntry.getContent()!=null&&!myEntry.getContent().equals("")?myEntry.getContent(): old.getContent());
        }
        journalEntryService.saveEntry(old);
        return old;
    }
}
