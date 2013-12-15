/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ims.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class PersistenceDomain extends AbstractPersistable<Long> {

    private static final long serialVersionUID = 1L;
    @Version
    private Long version;
    @Embedded
    private ChangeLog changeLog = new ChangeLog();

    public PersistenceDomain() {
    }

    public void setId(Long id) {
        super.setId(id);
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public ChangeLog getChangeLog() {
        return changeLog;
    }

    public void setChangeLog(ChangeLog changeLog) {
        this.changeLog = changeLog;
    }

    @PrePersist
    void prePersist() {
        changeLog.setCreatedDate(new Date());
        changeLog.setCreatedBy("system");
        System.out.println("prePersist");
    }

    @PreUpdate
    void preUpdate() {
        changeLog.setLastModifiedBy("update");
        changeLog.setLastModifiedDate(new Date());
        System.out.println("preUpdate");
    }

//    @PreRemove
//    void preRemove() {
//        System.out.println("preRemove");
//    }
//
//    @PostLoad
//    void postLoad() {
//        System.out.println("postLoad");
//    }
//
//    @PostPersist
//    void postPersist() {
//        System.out.println("postPersist");
//    }
//
//    @PostRemove
//    void postRemove() {
//        System.out.println("postRemove");
//    }
//
//    @PostUpdate
//    void postUpdate() {
//        System.out.println("postUpdate");
//    }
}
