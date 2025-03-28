package community.community.service;

import community.community.controller.notificationController.NotificationController;
import community.community.entity.Notification;
import community.community.entity.Post;
import community.community.service.postService.PostFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationController notificationController;
    private final PostFindService postFindService;

    private final Queue<Notification> notificationQueue = new ConcurrentLinkedQueue<>();

    //알림 생성
    public void createNotification(String senderEmail, Long postId) {
        Post post = postFindService.postFindId(postId);
        String targetEmail = post.getMember().getEmail();
        String message = senderEmail + "님이 " + targetEmail + "님이 작성하신 글에 좋아요를 눌렀습니다.";

        Notification notification = Notification.builder()
                .message(message)
                .targetEmail(targetEmail)
                .build();

        notificationQueue.add(notification);

        processNotifications();
    }

    //알림 출력
    public void processNotifications() {
        while (!notificationQueue.isEmpty()) {
            Notification notification = notificationQueue.poll();
            notificationController.sendNotification(notification.getMessage(), notification.getTargetEmail());
        }
    }

}
