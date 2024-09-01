package io.vinicius.banking.api.feat.transaction

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import io.vinicius.banking.api.feat.transaction.dto.TransactionCreateDto
import io.vinicius.banking.api.feat.transaction.dto.TransactionResponseDto
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("\${apiPrefix}/v1/transactions")
@Tag(name = "Transaction")
class TransactionController(private val service: TransactionService) {

    @PostMapping
    @Operation(security = [SecurityRequirement(name = "access-token")])
    suspend fun createTransaction(
        @Valid @RequestBody dto: TransactionCreateDto
    ): TransactionResponseDto {
        return service.createTransaction(dto)
    }

    @GetMapping("/{accountId}")
    @Operation(security = [SecurityRequirement(name = "access-token")])
    suspend fun retrieveTransactions(
        @PathVariable("accountId") @Min(1) accountId: Int
    ): List<TransactionResponseDto> {
        return service.retrieveTransactions(accountId)
    }
}