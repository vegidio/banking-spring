package io.vinicius.banking.feat.user

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonProperty.Access
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue
    val id: Int,

    val name: String,
    val username: String,
    val email: String,

    @JdbcTypeCode(SqlTypes.JSON)
    val address: Address,

    @Schema(hidden = true)
    @JsonProperty(access = Access.WRITE_ONLY)
    val hash: String
) {
    data class Address(
        val street: String,
        val suite: String?,
        val city: String,
        val zipCode: String,
    )
}