package com.dossier.ability.abilitybot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dossier_subjects")
public class Subject {

    @Column(name = "pdf_uri")
    private String pdfUri;

    /* Id consists of an id of a dossier and a year when the dossier was submitted
     *
     *  Ex.: 85387/2016
     *  Where 85387 is id of dossier and 2016 is a year when the dossier was submitted
     * */
    @Id
    @Column(name = "dossier_id")
    private String id;
}
