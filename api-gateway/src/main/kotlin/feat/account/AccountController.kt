package io.vinicius.banking.api.feat.account

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import io.vinicius.banking.api.feat.account.dto.AccountCreateRequestDto
import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("\${apiPrefix}/v1/accounts")
@Tag(name = "Account")
class AccountController(private val accountService: AccountService) {

    @PostMapping
    @Operation(security = [SecurityRequirement(name = "access-token")])
    suspend fun createAccount(
        principal: Principal,
        @Valid @RequestBody dto: AccountCreateRequestDto
    ) {
        accountService.createAccount(dto)
    }
}