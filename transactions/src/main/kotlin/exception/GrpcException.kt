package io.vinicius.banking.transactions.exception

import io.grpc.Metadata
import io.grpc.Status
import io.grpc.StatusException

open class GrpcException(
    status: Status,
    description: String? = null,
    metadata: Metadata? = null
) : StatusException(status.withDescription(description), metadata)