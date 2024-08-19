package io.vinicius.banking.exception

import io.grpc.Metadata
import io.grpc.Status

class UnauthenticatedException(
    description: String? = null,
    metadata: Metadata? = null
) : GrpcException(Status.UNAUTHENTICATED, description, metadata)