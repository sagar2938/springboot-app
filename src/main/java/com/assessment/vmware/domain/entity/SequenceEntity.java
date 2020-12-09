package com.assessment.vmware.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sequence")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SequenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SEQUENCE_ID")
    private Long sequenceId;

    @Column(name = "SEQUENCE")
    private String sequence;

    @ManyToOne
    @JoinColumn(name = "TASK_ID")
    private TaskEntity task;
}
