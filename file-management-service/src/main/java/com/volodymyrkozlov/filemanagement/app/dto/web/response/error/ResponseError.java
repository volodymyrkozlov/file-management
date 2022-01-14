package com.volodymyrkozlov.filemanagement.app.dto.web.response.error;

import java.util.Collection;


public record ResponseError(ResponseErrorStatus status,
                            Collection<ResponseErrorDetails> errors) {
}
