package meu.booking_rebuild_ver2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

/*
 * author: Nguyen Quoc Dai
 * ticket: BS-17
 * */
@Entity
@Table(name = "status")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String status;
    private boolean flag;
    @JsonIgnore
    private ZonedDateTime createdAt = ZonedDateTime.now();;
    @JsonIgnore
    private ZonedDateTime updatedAt = ZonedDateTime.now();

    public Status(UUID id) {
        this.id = id;
    }
}
