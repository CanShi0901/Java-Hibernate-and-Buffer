/**********************************************************************w***a******l******r*******us***********
 * File: LoggingOutputStream.java
 * Course materials (19W) CST 8277
 * @author Mike Norman, derived from the work of Robert Elliot (see license below)
 */
package com.algonquincollege.cst8277.assignment2;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.slf4j.Logger;

/**
 *
 * <b>Description</b></br></br>
 * Helper class that wraps an (ByteArray)OutputStream for SLF4J output </br>
 * When the {@link #line()} method is called, the baos is flushed to the logger
 *
 * @date 2019 02
 *
 * @author mwnorman
 *
 */
public class LoggingOutputStream extends OutputStream {

    private static final int BUF_SIZE = 1000;

    public enum LogLevel {
        TRACE, DEBUG, INFO, WARN, ERROR,
    }

    protected ByteArrayOutputStream baos = new ByteArrayOutputStream(BUF_SIZE);
    protected Logger logger;
    protected LogLevel level;

    public LoggingOutputStream(Logger logger, LogLevel level) {
        this.logger = logger;
        this.level = level;
    }

    public void line() {
        String line = baos.toString();
        baos.reset();
        switch (level) {
            case TRACE:
                logger.trace(line);
                break;
            case DEBUG:
                logger.debug(line);
                break;
            case ERROR:
                logger.error(line);
                break;
            case INFO:
                logger.info(line);
                break;
            case WARN:
                logger.warn(line);
                break;
        }
    }
    @Override
    public void write(int b) {
        baos.write(b);
    }
}
/*
 * Copyright (c) 2009-2012 Robert Elliot
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */