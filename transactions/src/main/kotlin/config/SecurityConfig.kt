package io.vinicius.banking.config

import com.nimbusds.jose.crypto.ECDSAVerifier
import com.nimbusds.jwt.SignedJWT
import io.grpc.Metadata
import io.grpc.ServerCall
import io.grpc.kotlin.CoroutineContextServerInterceptor
import io.vinicius.banking.exception.UnauthorizedException
import io.vinicius.banking.ktx.isFresh
import io.vinicius.banking.ktx.toJwt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ThreadContextElement
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor
import net.devh.boot.grpc.server.security.authentication.BearerAuthenticationReader
import net.devh.boot.grpc.server.security.authentication.CompositeGrpcAuthenticationReader
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider
import java.text.ParseException
import kotlin.coroutines.CoroutineContext

@Configuration
@EnableMethodSecurity(proxyTargetClass = true)
class SecurityConfig(private val certProperties: CertProperties) {
    @Bean
    fun authenticationManager(jwtDecoder: JwtDecoder): AuthenticationManager {
        val providers = listOf(JwtAuthenticationProvider(jwtDecoder))
        return ProviderManager(providers)
    }

    @Bean
    fun authenticationReader(): GrpcAuthenticationReader {
        val readers = listOf(BearerAuthenticationReader(::BearerTokenAuthenticationToken))
        return CompositeGrpcAuthenticationReader(readers)
    }

    @Bean
    fun jwtDecoder() = JwtDecoder { token ->
        val signedJwt = try { SignedJWT.parse(token) } catch (ex: ParseException) { null }
        val isValid = signedJwt?.verify(ECDSAVerifier(certProperties.accessTokenPublic))

        if (isValid != true) throw UnauthorizedException(
            type = "JWT_INVALID",
            detail = "The bearer token is invalid"
        )

        if (!signedJwt.isFresh()) throw UnauthorizedException(
            type = "JWT_EXPIRED",
            detail = "The bearer token expired"
        )

        signedJwt.toJwt()
    }

    // region - Fix for gRPC Kotlin Security Handler
    // For some reason, either a problem in the implementation of Spring gRPC or a bug in Kotlin gRPC, Spring Security
    // doesn't work when we use *CoroutineImplBase; it only works with *ImplBase. This is a workaround to make it work.
    @GrpcGlobalServerInterceptor
    fun contextCoroutineInterceptor() = object : CoroutineContextServerInterceptor() {
        override fun coroutineContext(call: ServerCall<*, *>, headers: Metadata): CoroutineContext =
            Dispatchers.Default + SecurityCoroutineContext()
    }

    private class SecurityCoroutineContext(
        private val securityContext: SecurityContext = SecurityContextHolder.getContext()
    ) : ThreadContextElement<SecurityContext?> {

        companion object Key : CoroutineContext.Key<SecurityCoroutineContext>
        override val key: CoroutineContext.Key<SecurityCoroutineContext> get() = Key

        override fun updateThreadContext(context: CoroutineContext): SecurityContext? {
            val previousContext = SecurityContextHolder.getContext()
            SecurityContextHolder.setContext(securityContext)
            return previousContext.takeIf { it.authentication != null }
        }

        override fun restoreThreadContext(context: CoroutineContext, oldState: SecurityContext?) {
            if (oldState == null) {
                SecurityContextHolder.clearContext()
            } else {
                SecurityContextHolder.setContext(oldState)
            }
        }
    }
    // endregion
}