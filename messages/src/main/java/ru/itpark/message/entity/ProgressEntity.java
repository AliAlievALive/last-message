package ru.itpark.message.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity(name = "progress")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProgressEntity extends BaseEntity {

    @Column(nullable = false)
    private Boolean status = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_message_id")
    private LastMessageEntity message;

}
