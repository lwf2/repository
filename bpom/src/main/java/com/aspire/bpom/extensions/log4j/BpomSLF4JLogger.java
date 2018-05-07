/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache license, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the license for the specific language governing permissions and
 * limitations under the license.
 */
package com.aspire.bpom.extensions.log4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.apache.logging.log4j.message.SimpleMessage;
import org.apache.logging.log4j.spi.ExtendedLogger;
import org.apache.logging.log4j.util.LoaderUtil;
import org.apache.logging.slf4j.EventDataConverter;
import org.apache.logging.slf4j.Log4jMarker;
import org.apache.logging.slf4j.Log4jMarkerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.impl.StaticMarkerBinder;
import org.slf4j.spi.LocationAwareLogger;

/**
 *
 */
public class BpomSLF4JLogger implements LocationAwareLogger, Serializable {

	private static final String FQCN = BpomSLF4JLogger.class.getName();
	
	private static final long serialVersionUID = -428389447880689111L;
    private static final Marker EVENT_MARKER = MarkerFactory.getMarker("EVENT");
    private final boolean eventLogger;
    private transient ExtendedLogger logger;
    private final String name;
    private transient EventDataConverter converter;

    public BpomSLF4JLogger(final ExtendedLogger logger, final String name) {
        this.logger = logger;
        this.eventLogger = "EventLogger".equals(name);
        this.name = name;
        this.converter = createConverter();
    }

    @Override
    public void trace(final String format) {
        logger.logIfEnabled(FQCN, Level.TRACE, null, getNewMessage(format));
    }

    @Override
    public void trace(final String format, final Object o) {
        logger.logIfEnabled(FQCN, Level.TRACE, null, getNewMessage(format), o);
    }

    @Override
    public void trace(final String format, final Object arg1, final Object arg2) {
        logger.logIfEnabled(FQCN, Level.TRACE, null, getNewMessage(format), arg1, arg2);
    }

    @Override
    public void trace(final String format, final Object... args) {
        logger.logIfEnabled(FQCN, Level.TRACE, null, getNewMessage(format), args);
    }

    @Override
    public void trace(final String format, final Throwable t) {
        logger.logIfEnabled(FQCN, Level.TRACE, null, getNewMessage(format), t);
    }

    @Override
    public boolean isTraceEnabled() {
        return logger.isEnabled(Level.TRACE, null, null);
    }

    @Override
    public boolean isTraceEnabled(final Marker marker) {
        return logger.isEnabled(Level.TRACE, getMarker(marker), null);
    }

    @Override
    public void trace(final Marker marker, final String s) {
        logger.logIfEnabled(FQCN, Level.TRACE, getMarker(marker), getNewMessage(s));
    }

    @Override
    public void trace(final Marker marker, final String s, final Object o) {
        logger.logIfEnabled(FQCN, Level.TRACE, getMarker(marker), getNewMessage(s), o);
    }

    @Override
    public void trace(final Marker marker, final String s, final Object o, final Object o1) {
        logger.logIfEnabled(FQCN, Level.TRACE, getMarker(marker), getNewMessage(s), o, o1);
    }

    @Override
    public void trace(final Marker marker, final String s, final Object... objects) {
        logger.logIfEnabled(FQCN, Level.TRACE, getMarker(marker), getNewMessage(s), objects);
    }

    @Override
    public void trace(final Marker marker, final String s, final Throwable throwable) {
        logger.logIfEnabled(FQCN, Level.TRACE, getMarker(marker), getNewMessage(s), throwable);
    }

    @Override
    public void debug(final String format) {
        logger.logIfEnabled(FQCN, Level.DEBUG, null, getNewMessage(format));
    }

    @Override
    public void debug(final String format, final Object o) {
        logger.logIfEnabled(FQCN, Level.DEBUG, null, getNewMessage(format), o);
    }

    @Override
    public void debug(final String format, final Object arg1, final Object arg2) {
        logger.logIfEnabled(FQCN, Level.DEBUG, null, getNewMessage(format), arg1, arg2);
    }

    @Override
    public void debug(final String format, final Object... args) {
        logger.logIfEnabled(FQCN, Level.DEBUG, null, getNewMessage(format), args);
    }

    @Override
    public void debug(final String format, final Throwable t) {
        logger.logIfEnabled(FQCN, Level.DEBUG, null, getNewMessage(format), t);
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isEnabled(Level.DEBUG, null, null);
    }

    @Override
    public boolean isDebugEnabled(final Marker marker) {
        return logger.isEnabled(Level.DEBUG, getMarker(marker), null);
    }

    @Override
    public void debug(final Marker marker, final String s) {
        logger.logIfEnabled(FQCN, Level.DEBUG, getMarker(marker), getNewMessage(s));
    }

    @Override
    public void debug(final Marker marker, final String s, final Object o) {
        logger.logIfEnabled(FQCN, Level.DEBUG, getMarker(marker), getNewMessage(s), o);
    }

    @Override
    public void debug(final Marker marker, final String s, final Object o, final Object o1) {
        logger.logIfEnabled(FQCN, Level.DEBUG, getMarker(marker), getNewMessage(s), o, o1);
    }

    @Override
    public void debug(final Marker marker, final String s, final Object... objects) {
        logger.logIfEnabled(FQCN, Level.DEBUG, getMarker(marker), getNewMessage(s), objects);
    }

    @Override
    public void debug(final Marker marker, final String s, final Throwable throwable) {
        logger.logIfEnabled(FQCN, Level.DEBUG, getMarker(marker), getNewMessage(s), throwable);
    }

    @Override
    public void info(final String format) {
        logger.logIfEnabled(FQCN, Level.INFO, null, getNewMessage(format));
    }

    @Override
    public void info(final String format, final Object o) {
        logger.logIfEnabled(FQCN, Level.INFO, null, getNewMessage(format), o);
    }

    @Override
    public void info(final String format, final Object arg1, final Object arg2) {
        logger.logIfEnabled(FQCN, Level.INFO, null, getNewMessage(format), arg1, arg2);
    }

    @Override
    public void info(final String format, final Object... args) {
        logger.logIfEnabled(FQCN, Level.INFO, null, getNewMessage(format), args);
    }

    @Override
    public void info(final String format, final Throwable t) {
        logger.logIfEnabled(FQCN, Level.INFO, null, getNewMessage(format), t);
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isEnabled(Level.INFO, null, null);
    }

    @Override
    public boolean isInfoEnabled(final Marker marker) {
        return logger.isEnabled(Level.INFO, getMarker(marker), null);
    }

    @Override
    public void info(final Marker marker, final String s) {
        logger.logIfEnabled(FQCN, Level.INFO, getMarker(marker), getNewMessage(s));
    }

    @Override
    public void info(final Marker marker, final String s, final Object o) {
        logger.logIfEnabled(FQCN, Level.INFO, getMarker(marker), getNewMessage(s), o);
    }

    @Override
    public void info(final Marker marker, final String s, final Object o, final Object o1) {
        logger.logIfEnabled(FQCN, Level.INFO, getMarker(marker), getNewMessage(s), o, o1);
    }

    @Override
    public void info(final Marker marker, final String s, final Object... objects) {
        logger.logIfEnabled(FQCN, Level.INFO, getMarker(marker), getNewMessage(s), objects);
    }

    @Override
    public void info(final Marker marker, final String s, final Throwable throwable) {
        logger.logIfEnabled(FQCN, Level.INFO, getMarker(marker), getNewMessage(s), throwable);
    }

    private String getNewMessage(String format) {
    	
    	String transactionid = ThreadLocalTransactionID.getTransactionID() + " ";
		StringBuffer newMessage = new StringBuffer();
		if (format == null) {
			newMessage.append(transactionid);
		} else {
			// 处理message在多行的情况下每一行前都加 transactionid
			String[] splitMessage = format.toString().split("\n");
			if (splitMessage == null || splitMessage.length == 0) {
				newMessage.append(transactionid);
			} else {
				for (int i = 0; i < splitMessage.length - 1; i++) {
					newMessage.append(transactionid).append(splitMessage[i]).append("\n");
				}
				newMessage.append(transactionid).append(splitMessage[splitMessage.length - 1]);
			}
		}
		return newMessage.toString();
	}
 
    @Override
    public void warn(final String format) {
        logger.logIfEnabled(FQCN, Level.WARN, null, getNewMessage(format));
    }

    @Override
    public void warn(final String format, final Object o) {
        logger.logIfEnabled(FQCN, Level.WARN, null, getNewMessage(format), o);
    }

    @Override
    public void warn(final String format, final Object arg1, final Object arg2) {
        logger.logIfEnabled(FQCN, Level.WARN, null, getNewMessage(format), arg1, arg2);
    }

    @Override
    public void warn(final String format, final Object... args) {
        logger.logIfEnabled(FQCN, Level.WARN, null, getNewMessage(format), args);
    }

    @Override
    public void warn(final String format, final Throwable t) {
        logger.logIfEnabled(FQCN, Level.WARN, null, getNewMessage(format), t);
    }

    @Override
    public boolean isWarnEnabled() {
        return logger.isEnabled(Level.WARN, null, null);
    }

    @Override
    public boolean isWarnEnabled(final Marker marker) {
        return logger.isEnabled(Level.WARN, getMarker(marker), null);
    }

    @Override
    public void warn(final Marker marker, final String s) {
        logger.logIfEnabled(FQCN, Level.WARN, getMarker(marker), getNewMessage(s));
    }

    @Override
    public void warn(final Marker marker, final String s, final Object o) {
        logger.logIfEnabled(FQCN, Level.WARN, getMarker(marker), getNewMessage(s), o);
    }

    @Override
    public void warn(final Marker marker, final String s, final Object o, final Object o1) {
        logger.logIfEnabled(FQCN, Level.WARN, getMarker(marker), getNewMessage(s), o, o1);
    }

    @Override
    public void warn(final Marker marker, final String s, final Object... objects) {
        logger.logIfEnabled(FQCN, Level.WARN, getMarker(marker), getNewMessage(s), objects);
    }

    @Override
    public void warn(final Marker marker, final String s, final Throwable throwable) {
        logger.logIfEnabled(FQCN, Level.WARN, getMarker(marker), getNewMessage(s), throwable);
    }

    @Override
    public void error(final String format) {
        logger.logIfEnabled(FQCN, Level.ERROR, null, getNewMessage(format));
    }

    @Override
    public void error(final String format, final Object o) {
        logger.logIfEnabled(FQCN, Level.ERROR, null, getNewMessage(format), o);
    }

    @Override
    public void error(final String format, final Object arg1, final Object arg2) {
        logger.logIfEnabled(FQCN, Level.ERROR, null, getNewMessage(format), arg1, arg2);
    }

    @Override
    public void error(final String format, final Object... args) {
        logger.logIfEnabled(FQCN, Level.ERROR, null, getNewMessage(format), args);
    }

    @Override
    public void error(final String format, final Throwable t) {
        logger.logIfEnabled(FQCN, Level.ERROR, null, getNewMessage(format), t);
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isEnabled(Level.ERROR, null, null);
    }

    @Override
    public boolean isErrorEnabled(final Marker marker) {
        return logger.isEnabled(Level.ERROR, getMarker(marker), null);
    }

    @Override
    public void error(final Marker marker, final String s) {
        logger.logIfEnabled(FQCN, Level.ERROR, getMarker(marker), getNewMessage(s));
    }

    @Override
    public void error(final Marker marker, final String s, final Object o) {
        logger.logIfEnabled(FQCN, Level.ERROR, getMarker(marker), getNewMessage(s), o);
    }

    @Override
    public void error(final Marker marker, final String s, final Object o, final Object o1) {
        logger.logIfEnabled(FQCN, Level.ERROR, getMarker(marker), getNewMessage(s), o, o1);
    }

    @Override
    public void error(final Marker marker, final String s, final Object... objects) {
        logger.logIfEnabled(FQCN, Level.ERROR, getMarker(marker), getNewMessage(s), objects);
    }

    @Override
    public void error(final Marker marker, final String s, final Throwable throwable) {
        logger.logIfEnabled(FQCN, Level.ERROR, getMarker(marker), getNewMessage(s), throwable);
    }

    @Override
    public void log(final Marker marker, final String fqcn, final int level, final String message, final Object[] params, Throwable throwable) {
        final Level log4jLevel = getLevel(level);
        final org.apache.logging.log4j.Marker log4jMarker = getMarker(marker);

        if (!logger.isEnabled(log4jLevel, log4jMarker, message, params)) {
            return;
        }
        final Message msg;
        if (eventLogger && marker != null && marker.contains(EVENT_MARKER) && converter != null) {
            msg = converter.convertEvent(message, params, throwable);
        } else if (params == null) {
            msg = new SimpleMessage(message);
        } else {
            msg = new ParameterizedMessage(message, params, throwable);
            if (throwable != null) {
                throwable = msg.getThrowable();
            }
        }
        logger.logMessage(fqcn, log4jLevel, log4jMarker, msg, throwable);
    }

    private static org.apache.logging.log4j.Marker getMarker(final Marker marker) {
        if (marker == null) {
            return null;
        } else if (marker instanceof Log4jMarker) {
            return ((Log4jMarker) marker).getLog4jMarker();
        } else {
            final Log4jMarkerFactory factory = (Log4jMarkerFactory) StaticMarkerBinder.SINGLETON.getMarkerFactory();
            return ((Log4jMarker) factory.getMarker(marker)).getLog4jMarker();
        }
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Always treat de-serialization as a full-blown constructor, by validating the final state of
     * the de-serialized object.
     */
    private void readObject(final ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
        // always perform the default de-serialization first
        aInputStream.defaultReadObject();
        logger = LogManager.getContext().getLogger(name);
        converter = createConverter();
    }

    /**
     * This is the default implementation of writeObject. Customise if necessary.
     */
    private void writeObject(final ObjectOutputStream aOutputStream) throws IOException {
        // perform the default serialization for all non-transient, non-static fields
        aOutputStream.defaultWriteObject();
    }

    private static EventDataConverter createConverter() {
        try {
            LoaderUtil.loadClass("org.slf4j.ext.EventData");
            return new EventDataConverter();
        } catch (final ClassNotFoundException cnfe) {
            return null;
        }
    }

    private static Level getLevel(final int i) {
        switch (i) {
        case TRACE_INT:
            return Level.TRACE;
        case DEBUG_INT:
            return Level.DEBUG;
        case INFO_INT:
            return Level.INFO;
        case WARN_INT:
            return Level.WARN;
        case ERROR_INT:
            return Level.ERROR;
        }
        return Level.ERROR;
    }
}
