package io.vinicius.banking.api.feat.user

import io.vinicius.banking.api.exception.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepo: UserRepository) {
    fun fetchUserById(id: Int): User =
        userRepo.findByIdOrNull(id) ?: throw NotFoundException(detail = "No user found with this id")

    fun fetchUserByEmail(email: String): User =
        userRepo.findByEmail(email) ?: throw NotFoundException(detail = "No user found with this id")
}