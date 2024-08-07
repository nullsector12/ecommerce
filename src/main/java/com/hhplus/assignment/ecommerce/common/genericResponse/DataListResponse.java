package com.hhplus.assignment.ecommerce.common.genericResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DataListResponse<T> implements GenericResponse {

    private List<T> data;
}
