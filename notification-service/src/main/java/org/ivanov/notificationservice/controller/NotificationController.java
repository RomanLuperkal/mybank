package org.ivanov.notificationservice.controller;

import org.blog.notificationdto.notificationoutbox.CreateMessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    @PostMapping
    public ResponseEntity<Void> saveMessage(@RequestBody List<CreateMessageDto> dto) {
        dto.forEach(d -> {
            System.out.println(d.message());
            System.out.println(d.email());
        });
        return ResponseEntity.ok().build();
    }
}
