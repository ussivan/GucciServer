package by.iuss.gucciserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class KalController {

    private final KalRepository kalRepository;

    public KalController(KalRepository kalRepository) {
        this.kalRepository = kalRepository;
    }

    @GetMapping("/gucci")
    List<Kal> all() {
        return kalRepository.findAll();
    }
}
