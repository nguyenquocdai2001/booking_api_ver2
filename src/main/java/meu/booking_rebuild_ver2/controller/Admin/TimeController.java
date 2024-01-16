package meu.booking_rebuild_ver2.controller.Admin;

import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.model.Admin.TimeModel;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.repository.Admin.TimeRepository;
import meu.booking_rebuild_ver2.repository.StatusRepository;
import meu.booking_rebuild_ver2.response.Admin.TimeResponse;
import meu.booking_rebuild_ver2.response.StatusResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/time", produces = MediaType.APPLICATION_JSON_VALUE)
public class TimeController {
    private final TimeRepository timeRepository;

    public TimeController(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @PostMapping(path = "/addTime")
    public TimeResponse addTimePostMapping(@RequestBody @Valid TimeModel timeModel){
        try {
            timeRepository.save(timeModel);
            TimeResponse response = new TimeResponse(Constants.MESSAGE_STATUS_ADD_SUCCESS, true, timeModel);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }
}
