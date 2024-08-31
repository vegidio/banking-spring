package io.vinicius.banking.transactions.feat.account

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<Account, Int> {
    fun findByUserId(userId: Int): List<Account>
    fun findByUserIdAndId(userId: Int, id: Int): Account?
}