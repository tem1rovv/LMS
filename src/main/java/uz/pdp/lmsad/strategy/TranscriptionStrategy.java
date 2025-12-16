package uz.pdp.lmsad.strategy;


import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
public class TranscriptionStrategy {

    private final List<SpeechToTextClient> list;


    public TranscriptionStrategy(List<SpeechToTextClient> list) {
        this.list = list;
    }

    public SpeechToTextClient get(Model model) {
        return list.stream().filter(speechToTextClient -> speechToTextClient.model().equals(model)).findFirst().orElseThrow(()->new RuntimeException("AI Client not found!"));
    }
}
