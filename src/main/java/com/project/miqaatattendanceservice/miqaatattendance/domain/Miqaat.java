package com.project.miqaatattendanceservice.miqaatattendance.domain;

import com.project.miqaatattendanceservice.Utils;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "Miqaat")
public class Miqaat {
    // Attributes
    @Id
    private String miqaatId;
    private String name;
    private Set<Integer> attending;
    private Set<Integer> notAttending;
    private Set<Integer> tentative;

    public Miqaat(String miqaatName) {
        this.name = miqaatName;
    }

    public void setAttending(Set<Integer> attending) {
        if (this.attending == null || this.attending.isEmpty()) {
            this.attending = new HashSet<>();
        }
        this.attending.addAll(attending);
    }

    public void setNotAttending(Set<Integer> notAttending) {
        if (this.notAttending == null || this.notAttending.isEmpty()) {
            this.notAttending = new HashSet<>();
        }
        this.notAttending.addAll(notAttending);
    }

    public void setTentative(Set<Integer> tentative) {
        if (this.tentative == null || this.tentative.isEmpty()) {
            this.tentative = new HashSet<>();
        }
        this.tentative.addAll(tentative);
    }

    public Set<Integer> getAttending() {
        return Utils.checkGet(attending);
    }

    public Set<Integer> getNotAttending() {
        return Utils.checkGet(notAttending);
    }

    public Set<Integer> getTentative() {
        return Utils.checkGet(tentative);
    }
}
