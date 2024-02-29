package meu.booking_rebuild_ver2.controller;

import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.repository.StatusRepository;
import meu.booking_rebuild_ver2.response.StatusResponse;
import meu.booking_rebuild_ver2.service.concretions.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

/*
 * author: Nguyen Quoc Dai
 * ticket: BS-17
 * */
@RestController
@RequestMapping(path = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
public class StatusController {

    private final StatusRepository statusRepository;

    public StatusController(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Autowired
    private StatusService statusService;


    @PostMapping (path = "/addStatus")
    public StatusResponse addStatusPostMapping(@RequestBody @Valid Status status){
        return statusService.createStatus(status);
    }

    @GetMapping(path = "getAllStatus")
    public StatusResponse getAllStatus(){
        return statusService.getAll();
    }

    @GetMapping(path = "getAllStatusByFlag")
    public StatusResponse getAllStatusByFlag(@RequestParam Boolean flag){
          return statusService.getAllByFlag(flag);
    }

    @GetMapping(path = "getStatusById")
    public StatusResponse getStatusById(@RequestParam UUID id){
        return statusService.getStatusById(id);
    }

    @PutMapping(path = "updateStatus")
    public StatusResponse updateStatus(@RequestBody @Valid Status status){
       return statusService.updateStatus(status);
    }

    @DeleteMapping(path = "deleteStatusById")
    public StatusResponse deleteStatusById(@RequestParam UUID idStatus){
        return statusService.deleteStatusById(idStatus);
    }
}
