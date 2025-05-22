//package community.community.service.notificationService;
//
//import community.community.entity.Notification;
//import community.community.entity.Post;
//import community.community.service.postService.PostFindService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//import java.util.Queue;
//import java.util.concurrent.ConcurrentLinkedQueue;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class NotificationService {
//
//    private final SseEmitterService sseEmitterService;
//    private final PostFindService postFindService;
//
//    private final Queue<Notification> notificationQueue = new ConcurrentLinkedQueue<>();
//
//    /**
//     * 알림 생성 및 큐에 추가
//     */
//    public void createNotification(String senderEmail, Long postId) {
//        Post post = postFindService.postFindId(postId);
//        String targetEmail = post.getMember().getEmail();
//        String message = senderEmail + "님이 " + targetEmail + "님이 작성하신 글에 좋아요를 눌렀습니다.";
//
//        Notification notification = Notification.builder()
//                .message(message)
//                .targetEmail(targetEmail)
//                .build();
//
//        notificationQueue.add(notification);
//
//        // 알림 처리 시작
//        processNotifications();
//    }
//
//    /**
//     * 알림 처리 (비동기적으로 실행)
//     */
//    @Async
//    public void processNotifications() {
//        while (!notificationQueue.isEmpty()) {
//            Notification notification = notificationQueue.poll();
//            if (notification != null) {
//                try {
//                    sseEmitterService.sendToClient(notification.getTargetEmail(), notification.getMessage());
//                } catch (Exception e) {
//                    log.error("Failed to process notification: {}", e.getMessage());
//                    // 필요시 실패한 알림 재처리 로직 추가
//                }
//            }
//        }
//    }
//}
