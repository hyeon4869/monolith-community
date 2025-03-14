package community.community.controller.notificationController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class NotificationController {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    @GetMapping("/api/notifications/{email}")
    public SseEmitter getNotifications(@PathVariable String email) {
        SseEmitter emitter = new SseEmitter();
        emitters.put(email, emitter);

        emitter.onCompletion(() -> emitters.remove(email));
        emitter.onError(ex -> emitters.remove(email));

        return emitter;
    }

    public void sendNotification(String message, String targetEmail) {
        try {
            SseEmitter emitter = emitters.get(targetEmail);
            if (emitter != null) {
                emitter.send(message);
            }
        } catch (IOException e) {
            // 에러 처리 로직
        }
    }
}
