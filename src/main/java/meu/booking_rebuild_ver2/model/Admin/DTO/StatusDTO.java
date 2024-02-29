package meu.booking_rebuild_ver2.model.Admin.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/*
 * author: Nguyen Quoc Dai
 * ticket: BS-17
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusDTO {
    private UUID id;
    private String status;
    private boolean flag;
}
