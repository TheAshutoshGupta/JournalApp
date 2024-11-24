package net.engineeringdigest.journalApp.controller;
import net.engineeringdigest.journalApp.entity.JournalEntry;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private Map<Long, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping("/all")
    public List<JournalEntry> getAll(){
        return new ArrayList<>(journalEntries.values());
    }


    @GetMapping("/all/{myId}")
    public JournalEntry getEntry(@PathVariable long myId){
        return journalEntries.get(myId);
    }

    @PostMapping("/add")
    public Boolean add(@RequestBody JournalEntry myEntry){
        journalEntries.put(myEntry.getId(), myEntry);
        return true;
    }
}
