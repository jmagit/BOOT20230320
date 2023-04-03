package com.example.batch;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.listener.ItemListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class ItemFailureLoggerListener extends ItemListenerSupport {
    private static Log logger = LogFactory.getLog("ITEM ERROR");
    @Override 
    public void onReadError(Exception ex) {  
    	logger.error("Read Error", ex);  
    	}
//    @Override
//    public void onWriteError(Exception ex, List item) { 
//    	logger.error("Write Error", ex); 
//    }
}
