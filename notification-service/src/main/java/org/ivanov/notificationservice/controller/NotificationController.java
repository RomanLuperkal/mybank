package org.ivanov.notificationservice.controller;

import org.blog.notificationdto.notificationoutbox.CreateMessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    @PostMapping
    public ResponseEntity<Void> saveMessage(@RequestBody CreateMessageDto dto) {
        System.out.println(dto.message());
        System.out.println(dto.email());
        return ResponseEntity.ok().build();
    }
}
