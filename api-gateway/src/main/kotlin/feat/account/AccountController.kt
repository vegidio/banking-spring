package io.vinicius.banking.api.feat.account

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import io.vinicius.banking.api.feat.account.dto.AccountCreateRequestDto
import io.vinicius.banking.api.feat.account.dto.AccountResponseDto
import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("\${apiPrefix}/v1/accounts")
@Tag(name = "Account")
class AccountController(private val service: AccountService) {

    @PostMapping
    @Operation(security = [SecurityRequirement(name = "access-token")])
    suspend fun createAccount(@Valid @RequestBody dto: AccountCreateRequestDto): AccountResponseDto {
        return service.createAccount(dto)
    }

    @GetMapping
    @Operation(security = [SecurityRequirement(name = "access-token")])
    suspend fun retrieveAccounts(): List<AccountResponseDto> {
        return service.retrieveAccounts()
    }
}