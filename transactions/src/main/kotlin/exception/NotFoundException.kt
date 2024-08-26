package io.vinicius.banking.transactions.exception

import io.grpc.Metadata
import io.grpc.Status

class NotFoundException(
    description: String? = null,
    metadata: Metadata? = null
) : GrpcException(Status.NOT_FOUND, description, metadata)