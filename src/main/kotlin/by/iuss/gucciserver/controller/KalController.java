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

    @Autowired
    private FileStorageService fileStorageService;


    public KalController(KalRepository kalRepository) {
        this.kalRepository = kalRepository;
    }

    @GetMapping("/gucci")
    List<Kal> all() {
        return kalRepository.findAll();
    }

    @GetMapping("/downloadFile")
    public ResponseEntity<Resource> downloadFile(HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource("kal");

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("kal");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
