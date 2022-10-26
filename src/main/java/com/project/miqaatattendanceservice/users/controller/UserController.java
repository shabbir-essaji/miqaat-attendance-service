package com.project.miqaatattendanceservice.users.controller;

import com.project.miqaatattendanceservice.miqaatattendance.service.MiqaatService;
import com.project.miqaatattendanceservice.users.domain.User;
import com.project.miqaatattendanceservice.users.domain.User.Gender;
import com.project.miqaatattendanceservice.users.dto.UserDTO;
import com.project.miqaatattendanceservice.users.dto.UserWithActionDTO;
import com.project.miqaatattendanceservice.users.service.UserService;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RestController
public class UserController {
    private final UserService userService;
    private final MiqaatService miqaatService;

    UserController(UserService userService, MiqaatService miqaatService) {
        this.userService = userService;
        this.miqaatService = miqaatService;
    }

    @GetMapping("/users")
    public void addUsers(@RequestParam(value = "removeMiqaats", required = false, defaultValue = "false") boolean removeMiqaats) {
        try {
            List<User> ITSRecords = new ArrayList<>();
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream("ITS miqaat.xlsx");
            // Finds the workbook instance for XLSX file
            XSSFWorkbook myWorkBook = new XSSFWorkbook(is);
            // Return first sheet from the XLSX workbook
            XSSFSheet mySheet = myWorkBook.getSheetAt(0);

            // Get iterator to all the rows in current sheet
            Iterator<Row> rowIterator = mySheet.iterator();
            //header row
            rowIterator.next();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                User emp = new User();
                // For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();
                int i = 0;
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    String cellValue = cell.getStringCellValue();
                    if (i == 0) {
                        emp.setIts(Integer.parseInt(cellValue));
                    }
                    else if (i == 1) {
                        emp.setHofIts(Integer.parseInt(cellValue));
                    }
                    else if (i == 2) {
                        emp.setName(cellValue);
                    }
                    else if (i == 3) {
                        emp.setGender(cellValue.equalsIgnoreCase("Male") ? Gender.MALE : Gender.FEMALE);
                    }
                    i++;
                }
                ITSRecords.add(emp);
            }
            userService.removeExisting();
            if (removeMiqaats) {
                miqaatService.removeExistingMiqaat();
            }
            userService.save(ITSRecords);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/{hofId}/members")
    public ResponseEntity<Set<UserWithActionDTO>> getMembersForHOF(@PathVariable("hofId") Integer hofId, @RequestParam("miqaatId") String miqaatId) {
        try {
            Set<UserDTO> members = userService.getMembersForHOF(hofId);
            Set<UserWithActionDTO> userWithActionDTOs = miqaatService.getMembersAttendance(members, miqaatId);
            return ResponseEntity.ok(userWithActionDTOs);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptySet());
        }
    }
}
