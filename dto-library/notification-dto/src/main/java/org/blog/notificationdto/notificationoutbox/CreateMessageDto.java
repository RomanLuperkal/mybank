package org.blog.notificationdto.notificationoutbox;

public record CreateMessageDto(String email, String theme, String message) {
}
