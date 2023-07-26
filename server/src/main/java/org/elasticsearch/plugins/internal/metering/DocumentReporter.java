/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0 and the Server Side Public License, v 1; you may not use this file except
 * in compliance with, at your election, the Elastic License 2.0 or the Server
 * Side Public License, v 1.
 */

package org.elasticsearch.plugins.internal.metering;

import org.elasticsearch.xcontent.XContentParser;

/**
 * An interface to allow wrapping an XContentParser and report that a document was parsed
 * A default implementation returns a noop DocumentReporter - does not wrap a XContentParser and
 * does not do anything upon reporting.
 */
public interface DocumentReporter {
    /**
     * a default noop implementation
     */
    DocumentReporter EMPTY_INSTANCE = new DocumentReporter() {
    };

    /**
     * Decorates a provided xContentParser with a metering logic. The Decorator parser should use a state from DocumentReporter
     * in order to allow a reportDocumentParsed method to use that state (metric value)
     * @param xContentParser to be decorated
     * @return a decorator xContentParser
     */
    default XContentParser wrapParser(XContentParser xContentParser) {
        return xContentParser;
    }

    /**
     * Reports (perhaps to some external components) the state that was gathered by a decorated wrap
     * @param indexName an index name to be reported along the state (metric value)
     */
    default void reportDocumentParsed(String indexName) {}
}
