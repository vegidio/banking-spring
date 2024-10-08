package io.vinicius.banking.transactions.feat.user

import io.vinicius.banking.transactions.exception.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(private val repo: UserRepository) {
    fun findById(id: Int): User =
        repo.findByIdOrNull(id) ?: throw NotFoundException("No user found with this id")
}