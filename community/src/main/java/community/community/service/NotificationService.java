package community.community.service;

import community.community.controller.notificationController.NotificationController;
import community.community.entity.Notification;
import community.community.entity.Post;
import community.community.service.postService.PostFindService;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class NotificationService {

    private final NotificationController notificationController;
    private final PostFindService postFindService;

    public NotificationService(NotificationController notificationController, PostFindService postFindService){
        this.notificationController=notificationController;
        this.postFindService=postFindService;
    }

    private final Queue<Notification> notificationQueue = new ConcurrentLinkedQueue<>();

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

    public void processNotifications() {
        while (!notificationQueue.isEmpty()) {
            Notification notification = notificationQueue.poll();
            notificationController.sendNotification(notification.getMessage(), notification.getTargetEmail());
        }
    }

}
