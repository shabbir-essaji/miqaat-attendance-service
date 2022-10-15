package com.project.miqaatattendanceservice.miqaatattendance.controller;

import com.project.miqaatattendanceservice.miqaatattendance.dto.AttendanceDTO;
import com.project.miqaatattendanceservice.miqaatattendance.dto.DashboardDTO;
import com.project.miqaatattendanceservice.miqaatattendance.dto.MiqaatDTO;
import com.project.miqaatattendanceservice.miqaatattendance.service.MiqaatService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MiqaatAttendanceController {
    private final MiqaatService miqaatService;

    MiqaatAttendanceController(MiqaatService miqaatService) {
        this.miqaatService = miqaatService;
    }

    @GetMapping("/miqaat")
    @ResponseBody
    public ResponseEntity<List<MiqaatDTO>> addMiqaat() {
        return ResponseEntity.ok(miqaatService.getLiveMiqaats());
    }

    @PostMapping("/miqaat/add")
    @ResponseBody
    public ResponseEntity<MiqaatDTO> addMiqaat(@RequestParam(value = "miqaatName") String miqaatName) {
        return ResponseEntity.ok(miqaatService.addMiqaat(miqaatName));
    }

    @PostMapping("/miqaat/remove")
    @ResponseBody
    public boolean removeMiqaat(@RequestParam(value = "miqaatId") String miqaatId) {
        miqaatService.removeMiqaat(miqaatId);
        return true;
    }

    @PostMapping(value = "/markAttendance", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Boolean> markAttendance(@RequestBody AttendanceDTO attendanceDTO) {
        miqaatService.markAttendance(attendanceDTO);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @GetMapping("/dashboard")
    @ResponseBody
    public ResponseEntity<DashboardDTO> getDashboardData(@RequestParam(value = "miqaatId") String miqaatId) {
        return ResponseEntity.ok(miqaatService.getDashboardData(miqaatId));
    }
}