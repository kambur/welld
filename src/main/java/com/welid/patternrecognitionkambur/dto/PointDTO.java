package com.welid.patternrecognitionkambur.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PointDTO {

    @NotNull
    @Min(0)
    private Integer x;

    @NotNull
    @Min(0)
    private Integer y;

    @Override
    public String toString() {
        if (x != null && y != null){
            return "(" + x + ", " + y + ")";
        }
        return "";
    }
}
