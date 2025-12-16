package uz.pdp.lmsad.strategy;

import org.springframework.web.multipart.MultipartFile;

public interface SpeechToTextClient {


    Model model();

    public String transcribe(MultipartFile file);


}
