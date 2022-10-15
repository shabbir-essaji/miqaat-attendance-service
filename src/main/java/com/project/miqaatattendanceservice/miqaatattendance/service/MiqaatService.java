package com.project.miqaatattendanceservice.miqaatattendance.service;

import com.project.miqaatattendanceservice.miqaatattendance.domain.Miqaat;
import com.project.miqaatattendanceservice.miqaatattendance.domain.MiqaatRepository;
import com.project.miqaatattendanceservice.miqaatattendance.dto.AttendanceDTO;
import com.project.miqaatattendanceservice.miqaatattendance.dto.DashboardDTO;
import com.project.miqaatattendanceservice.miqaatattendance.dto.MiqaatDTO;
import com.project.miqaatattendanceservice.users.dto.UserDTO;
import com.project.miqaatattendanceservice.users.dto.UserWithActionDTO;
import com.project.miqaatattendanceservice.users.service.UserService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MiqaatService {

    private final UserService userService;
    private final MiqaatRepository miqaatRepository;

    MiqaatService(UserService userService, MiqaatRepository miqaatRepository) {

        this.userService = userService;
        this.miqaatRepository = miqaatRepository;
    }

    public MiqaatDTO addMiqaat(String miqaatName) {
        Miqaat miqaat = new Miqaat(miqaatName);
        Miqaat savedMiqaat = miqaatRepository.save(miqaat);
        return new MiqaatDTO(savedMiqaat);
    }

    public DashboardDTO getDashboardData(String miqaatId) {
        Miqaat miqaat = getMiqaat(miqaatId);
        DashboardDTO dashboardDTO = new DashboardDTO();
        if (miqaat != null) {
            dashboardDTO.setMiqaatName(miqaat.getName());
            Set<UserDTO> attending = userService.getUsersById(miqaat.getAttending());
            dashboardDTO.setAttending(attending);
            Set<UserDTO> notAttending = userService.getUsersById(miqaat.getNotAttending());
            dashboardDTO.setNotAttending(notAttending);
            Set<UserDTO> tentative = userService.getUsersById(miqaat.getTentative());
            dashboardDTO.setTentative(tentative);
        }
        return dashboardDTO;
    }

    private Miqaat getMiqaat(String miqaatId) {
        Optional<Miqaat> miqaatOptional = miqaatRepository.findById(miqaatId);
        return miqaatOptional.orElse(null);
    }

    public List<MiqaatDTO> getLiveMiqaats() {
        List<Miqaat> miqaats = miqaatRepository.findAll();
        return miqaats.stream().map(MiqaatDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public void markAttendance(AttendanceDTO attendanceDTO) {
        Optional<Miqaat> miqaatOptional = miqaatRepository.findById(attendanceDTO.getMiqaatId());
        if (miqaatOptional.isPresent()) {
            Miqaat miqaat = miqaatOptional.get();
            Set<Integer> attending = attendanceDTO.getAttending();
            Set<Integer> tentative = attendanceDTO.getTentative();
            Set<Integer> notAttending = attendanceDTO.getNotAttending();
            //remove all existing entries present in miqaat
            Set<Integer> ITSIds = new HashSet<>();

            ITSIds.addAll(attending);
            ITSIds.addAll(notAttending);
            ITSIds.addAll(tentative);
            miqaat.getAttending().removeAll(ITSIds);
            miqaat.getNotAttending().removeAll(ITSIds);
            miqaat.getTentative().removeAll(ITSIds);
            //add fresh ones
            miqaat.setAttending(attending);
            miqaat.setNotAttending(notAttending);
            miqaat.setTentative(tentative);
            miqaatRepository.save(miqaat);
        }
    }

    public Set<UserWithActionDTO> getMembersAttendance(Set<UserDTO> members, String miqaatId) {
        Miqaat miqaat = getMiqaat(miqaatId);
        Set<UserWithActionDTO> userWithActionDTOS = members.stream().map(member -> {
            UserWithActionDTO userWithActionDTO = new UserWithActionDTO();
            userWithActionDTO.setIts(member.getIts());
            userWithActionDTO.setName(member.getName());
            return userWithActionDTO;
        }).collect(Collectors.toSet());
        if (miqaat != null) {
            Set<Integer> attendingITS = miqaat.getAttending();
            Set<Integer> notAttendingITS = miqaat.getNotAttending();
            Set<Integer> tentativeITS = miqaat.getTentative();
            userWithActionDTOS.forEach(userWithActionDTO -> {
                if (attendingITS.contains(userWithActionDTO.getIts())) {
                    userWithActionDTO.setResponse(1);
                }
                else if (notAttendingITS.contains(userWithActionDTO.getIts())) {
                    userWithActionDTO.setResponse(0);
                }
                else if (tentativeITS.contains(userWithActionDTO.getIts())) {
                    userWithActionDTO.setResponse(-1);
                }
            });
        }
        return userWithActionDTOS;
    }
}
