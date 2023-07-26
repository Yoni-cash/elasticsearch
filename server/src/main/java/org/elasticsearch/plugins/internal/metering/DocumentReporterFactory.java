/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0 and the Server Side Public License, v 1; you may not use this file except
 * in compliance with, at your election, the Elastic License 2.0 or the Server
 * Side Public License, v 1.
 */

package org.elasticsearch.plugins.internal.metering;

/**
 * An interface of a factory to return a DocumentReporter.
 * A default implementation is returning a noop DocumentReporter
 */
public interface DocumentReporterFactory {
    DocumentReporterFactory EMPTY_INSTANCE = () -> DocumentReporter.EMPTY_INSTANCE;

    /**
     * creates an instance of a DocumentReporter which will be used to report upon xcontent parsing
     * @return a new instance of DocumentReporter
     */
    DocumentReporter createDocumentReporter();
}
