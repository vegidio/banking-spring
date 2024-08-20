package io.vinicius.banking.feat.transaction

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository : JpaRepository<Transaction, Int> {
    fun findByAccountId(accountId: Int): List<Transaction>
}