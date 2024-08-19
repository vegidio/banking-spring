package io.vinicius.banking.advice

import io.github.oshai.kotlinlogging.KotlinLogging
import io.vinicius.banking.exception.BadRequestException
import io.vinicius.banking.exception.HttpException
import io.vinicius.banking.exception.ServerErrorException
import io.vinicius.banking.exception.UnauthorizedException
import io.vinicius.banking.ktx.capitalize
import io.vinicius.banking.model.Response
import jakarta.validation.ConstraintViolationException
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@Suppress("MethodOverloading")
@ControllerAdvice
class RestExceptionHandler {
    private val logger = KotlinLogging.logger {}

    @ExceptionHandler(value = [HttpException::class])
    fun handleApiException(ex: HttpException) = ResponseEntity<Response<Nothing>>(Response(error = ex.body), ex.status)

    // region - HTTP 400 Bad Request
    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun handleApiException(ex: ConstraintViolationException): ResponseEntity<Response<Nothing>> {
        val title = with(ex.constraintViolations.first()) { "The parameter '$invalidValue' is invalid" }
        val detail = with(ex.constraintViolations.first()) { "${propertyPath.last().name.capitalize()} $message" }
        val exception = BadRequestException(title = title, detail = detail)
        return ResponseEntity(Response(error = exception.body), exception.status)
    }

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handleApiException(ex: MethodArgumentNotValidException): ResponseEntity<Response<Nothing>> {
        val title = with(ex.fieldErrors.first()) { "Validation failed for '${objectName.capitalize()}'" }
        val detail = with(ex.fieldErrors.first()) { "${field.capitalize()} $defaultMessage" }
        val exception = BadRequestException(title = title, detail = detail)
        return ResponseEntity(Response(error = exception.body), exception.status)
    }

    @ExceptionHandler(value = [HttpMessageNotReadableException::class])
    fun handleApiException(ex: HttpMessageNotReadableException): ResponseEntity<Response<Nothing>> {
        val title = "The request is incomplete or malformed."
        val detail = ex.cause?.message
        val exception = BadRequestException(title = title, detail = detail)
        return ResponseEntity(Response(error = exception.body), exception.status)
    }
    // endregion

    // region - HTTP 401 Unauthorized
    @ExceptionHandler(
        value = [
            UnauthorizedException::class,
            InsufficientAuthenticationException::class,
            AccessDeniedException::class
        ]
    )
    fun handleApiException(ex: RuntimeException): ResponseEntity<Response<Nothing>> {
        return if (ex is UnauthorizedException) {
            ResponseEntity(Response(error = ex.body), ex.status)
        } else {
            val type = "JWT_INVALID"
            val detail = "The bearer token is invalid"
            val exception = UnauthorizedException(type = type, detail = detail)
            return ResponseEntity(Response(error = exception.body), exception.status)
        }
    }
    // endregion

    // region - HTTP 500 Internal Server Error
    @ExceptionHandler(value = [Exception::class])
    fun handleApiException(ex: Exception): ResponseEntity<Response<Nothing>> {
        logger.error(ex) { "An unexpected error occurred" }

        val title = "Unexpected error"
        val detail = "For security reasons, check the server logs for detailed information"
        val exception = ServerErrorException(title = title, detail = detail)
        return ResponseEntity(Response(error = exception.body), exception.status)
    }
    // endregion
}