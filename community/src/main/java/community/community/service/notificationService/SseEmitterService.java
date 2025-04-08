package community.community.service.notificationService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class SseEmitterService {

    // 각 사용자의 이메일을 키로 하여 SSE Emitter를 저장하는 맵
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    // 타임아웃 설정 (30분)
    private static final long SSE_TIMEOUT = 30 * 60 * 1000L;

    /**
     * 새로운 SSE 연결을 생성하고 저장
     */
    public SseEmitter createEmitter(String email) {
        SseEmitter emitter = new SseEmitter(SSE_TIMEOUT);
        emitters.put(email, emitter);

        // 연결이 완료되거나 에러 발생 시 emitter를 맵에서 제거
        emitter.onCompletion(() -> {
            log.info("SSE connection completed for user: {}", email);
            emitters.remove(email);
        });
        emitter.onTimeout(() -> {
            log.info("SSE connection timed out for user: {}", email);
            emitters.remove(email);
        });
        emitter.onError(ex -> {
            log.error("SSE error for user: {}: {}", email, ex.getMessage());
            emitters.remove(email);
        });

        // 연결 시 초기 이벤트 전송 (클라이언트 연결 확인용)
        try {
            emitter.send(SseEmitter.event().name("connect").data("Connected!"));
        } catch (IOException e) {
            log.error("Error sending initial SSE event to user: {}", email);
            emitters.remove(email);
        }

        return emitter;
    }

    /**
     * 특정 사용자에게 알림을 전송
     */
    public void sendToClient(String targetEmail, String message) {
        SseEmitter emitter = emitters.get(targetEmail);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name("notification")
                        .data(message));
                log.info("Notification sent to user: {}", targetEmail);
            } catch (IOException e) {
                log.error("Failed to send notification to user: {}: {}", targetEmail, e.getMessage());
                emitters.remove(targetEmail);
            }
        } else {
            log.info("No active SSE connection for user: {}", targetEmail);
        }
    }
}
