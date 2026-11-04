package presentation.BC4_Recomendaciones;

import BC4_Recomendaciones.RecomendacionRepository;
import application.BC4_Recomendaciones.RecomendacionAppService;
import application.BC4_Recomendaciones.RecomendacionAppServiceImpl;
import infrastructure.BC4_Recomendaciones.InMemoryRecomendacionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Cableado de dependencias del bounded context de Recomendaciones.
 * Sigue el patron de {@code SupportChatConfiguration} en BC3.
 */
@Configuration
public class RecomendacionesConfiguration {

    @Bean
    public RecomendacionRepository recomendacionRepository() {
        return new InMemoryRecomendacionRepository();
    }

    @Bean
    public RecomendacionAppService recomendacionAppService(RecomendacionRepository recomendacionRepository) {
        return new RecomendacionAppServiceImpl(recomendacionRepository);
    }
}
