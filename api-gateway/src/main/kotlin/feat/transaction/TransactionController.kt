package io.vinicius.banking.api.feat.transaction

import io.github.oshai.kotlinlogging.KotlinLogging
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import io.vinicius.banking.api.feat.transaction.dto.TransactionCreateDto
import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("\${apiPrefix}/v1/transactions")
@Tag(name = "Transaction")
class TransactionController {
    private val logger = KotlinLogging.logger {}

    @PostMapping
    @Operation(security = [SecurityRequirement(name = "access-token")])
    fun createTransaction(
        principal: Principal,
        @Valid @RequestBody dto: TransactionCreateDto
    ) {
        logger.info { "Principal: ${principal.name}" }
        logger.info { "Creating transaction: $dto" }
    }
}