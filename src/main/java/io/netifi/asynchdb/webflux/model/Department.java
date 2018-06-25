package io.netifi.asynchdb.webflux.model;

import org.davidmoten.rx.jdbc.annotations.Column;
import org.davidmoten.rx.jdbc.annotations.Query;

@Query("SELECT * FROM department")
public interface Department {

    @Column("department_id")
    int getId();

    @Column("department_name")
    String getName();
}
