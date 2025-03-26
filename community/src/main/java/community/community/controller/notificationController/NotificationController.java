package community.community.controller.notificationController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 실시간 알림을 처리하는 컨트롤러 클래스
 * Server-Sent Events (SSE)를 사용하여 클라이언트에게 실시간 알림을 전송
 */
@RestController
public class NotificationController {

    // 각 사용자의 이메일을 키로 하여 SSE Emitter를 저장하는 맵
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    /**
     * 클라이언트의 SSE 연결 요청을 처리하는 메소드
     * @param email 사용자 이메일 (식별자)
     * @return SseEmitter 객체
     */
    @GetMapping("/api/notifications/{email}")
    public SseEmitter getNotifications(@PathVariable String email) {
        SseEmitter emitter = new SseEmitter();
        emitters.put(email, emitter);

        // 연결이 완료되거나 에러 발생 시 emitter를 맵에서 제거
        emitter.onCompletion(() -> emitters.remove(email));
        emitter.onError(ex -> emitters.remove(email));

        return emitter;
    }

    /**
     * 특정 사용자에게 알림을 전송하는 메소드
     * @param message 전송할 알림 메시지
     * @param targetEmail 알림을 받을 사용자의 이메일
     */
    public void sendNotification(String message, String targetEmail) {
        try {
            SseEmitter emitter = emitters.get(targetEmail);
            if (emitter != null) {
                emitter.send(message);
            }
        } catch (IOException e) {
            // 여기에 적절한 에러 로깅이나 처리 로직을 추가
        }
    }
}
