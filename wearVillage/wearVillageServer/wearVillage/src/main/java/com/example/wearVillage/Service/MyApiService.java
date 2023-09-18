import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Serivce;

@Service
public class MyApiService {
  
  @Value("${Naver-Api-Id}")
  private String apiKey;
}