package org.ivanov.notificationservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blog.notificationdto.notificationoutbox.CreateMessageDto;
import org.ivanov.notificationservice.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<Void> saveMessage(@RequestBody List<CreateMessageDto> dto) {
        log.info("Поступил запрос post /notification");
        log.debug("List<CreateMessageDto>: {}", dto);
        notificationService.saveMessage(dto);
        return ResponseEntity.ok().build();
    }
}
