package ru.itpark.message.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity(name = "last_message")
@Data
@EqualsAndHashCode(callSuper = true)
public class LastMessageEntity extends BaseEntity {

    @Column(nullable = false)
    private Boolean isNotify = false;

    @OneToMany(mappedBy = "last_message", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<NotificationEntity> notifies;

    private String description;

    @Column(nullable = false)
    private Boolean isActive = true;

    @OneToMany(mappedBy = "last_message", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<ProgressEntity> progress;

    @Column(nullable = false, updatable = false)
    private UUID userId;

}
