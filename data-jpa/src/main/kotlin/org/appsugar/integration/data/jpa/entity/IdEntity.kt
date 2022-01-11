package org.appsugar.integration.data.jpa.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.UpdateTimestamp
import java.io.Serializable
import java.util.*
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class IdEntity : Serializable {
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @get:GenericGenerator(name = "native", strategy = "native")
    abstract var id: Long

    @get:CreationTimestamp
    abstract var createdAt: Date?

    @get:UpdateTimestamp
    abstract var updatedAt: Date?
}