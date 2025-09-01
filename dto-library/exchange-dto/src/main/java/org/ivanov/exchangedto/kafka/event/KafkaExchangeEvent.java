package org.ivanov.exchangedto.kafka.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.ivanov.exchangedto.kafka.enums.ExchangeEvent;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "event"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SaveMessageEvent.class, name = "SAVE_MESSAGE")
})
public interface KafkaExchangeEvent {
    ExchangeEvent getEvent();
}
