package com.eranga.reconcile;

import com.redis.S;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Record {
    private String id;
    private String date;
    private String statusCode;
}
