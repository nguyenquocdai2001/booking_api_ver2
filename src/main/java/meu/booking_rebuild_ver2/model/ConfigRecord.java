package meu.booking_rebuild_ver2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.ZonedDateTime;

@Data
public class ConfigRecord {
    private User user_config;
    private ZonedDateTime created_at;
    private ZonedDateTime updated_at = ZonedDateTime.now();

}
