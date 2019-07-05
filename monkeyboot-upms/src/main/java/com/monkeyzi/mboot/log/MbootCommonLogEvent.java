package com.monkeyzi.mboot.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

@Slf4j
public class MbootCommonLogEvent extends ApplicationEvent {

    public MbootCommonLogEvent(Object source) {
        super(source);
    }
}
