package community.community.controller.notificationController;

import community.community.service.notificationService.SseEmitterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * 실시간 알림을 처리하는 컨트롤러 클래스
 * Server-Sent Events (SSE)를 사용하여 클라이언트에게 실시간 알림을 전송
 */
@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final SseEmitterService sseEmitterService;

    /**
     * 클라이언트의 SSE 연결 요청을 처리하는 메소드
     * @param email 사용자 이메일 (식별자)
     * @return SseEmitter 객체
     */
    @GetMapping("/api/notifications/{email}")
    public SseEmitter getNotifications(@PathVariable String email) {
        return sseEmitterService.createEmitter(email);
    }
}
