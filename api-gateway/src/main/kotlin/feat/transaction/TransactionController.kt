package io.vinicius.banking.feat.transaction

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import io.vinicius.banking.feat.transaction.dto.TransactionCreateDto
import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("\${apiPrefix}/v1/transactions")
@Tag(name = "Transaction")
class TransactionController {

    @PostMapping
    @Operation(security = [SecurityRequirement(name = "access-token")])
    fun createTransaction(@Valid @RequestBody dto: TransactionCreateDto) {

    }
}