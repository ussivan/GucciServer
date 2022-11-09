package by.iuss.gucciserver.controller;

import by.iuss.gucciserver.entity.Kal;
import by.iuss.gucciserver.repository.KalRepository;
import by.iuss.gucciserver.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
public class KalController {

    private final KalRepository kalRepository;

    private final FileStorageService fileStorageService;

    public KalController(KalRepository kalRepository, FileStorageService fileStorageService) {
        this.kalRepository = kalRepository;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/gucci")
    List<Kal> all() {
        return kalRepository.findAll();
    }

}
