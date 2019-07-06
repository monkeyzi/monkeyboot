package com.monkeyzi.mboot.common.core.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

@Slf4j
public class MbootErrorLogEvent extends ApplicationEvent {

    public MbootErrorLogEvent(Object source) {
        super(source);
    }
}
