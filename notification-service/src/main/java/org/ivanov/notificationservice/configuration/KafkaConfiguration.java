package org.ivanov.notificationservice.configuration;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.blog.notificationdto.kafka.event.KafkaNotificationEvent;
import org.ivanov.notificationservice.handler.NotificationEventHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
public class KafkaConfiguration {
    @Value("${kafka.bootstrapServers}")
    private String bootstrapServers;
    @Value("${kafka.group-id}")
    private String groupId;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new KafkaAdmin(properties);
    }

    @Bean
    public NewTopic myEventTopic() {
        return TopicBuilder.name("notification")
                .partitions(3)
                .replicas(1)
                .config("cleanup.policy", "delete")
                .config("retention.ms", "604800000") // 7 дней в миллисекундах
                .build();
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaNotificationEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaNotificationEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryString());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        factory.setBatchListener(true);
        ContainerProperties containerProperties = factory.getContainerProperties();
        containerProperties.setObservationEnabled(true);
        return factory;
    }

    @Bean
    public <T extends KafkaNotificationEvent> Set<NotificationEventHandler<T>> eventHandlers(Set<NotificationEventHandler<T>> eventHandlers) {
        return new HashSet<>(eventHandlers);
    }

    private ConsumerFactory<String, KafkaNotificationEvent> consumerFactoryString() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 20000);
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 30000);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "org.blog.notificationdto.kafka.event");
        return new DefaultKafkaConsumerFactory<>(props);
    }
}
