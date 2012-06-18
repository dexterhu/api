/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2012 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package javax.ws.rs.container;

import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyWriter;

/**
 * Container response filter context.
 *
 * A mutable class that provides response-specific information for the filter,
 * such as message headers, message entity or request-scoped properties.
 * The exposed setters allow modification of the exposed response-specific
 * information.
 *
 * @author Marek Potociar (marek.potociar at oracle.com)
 * @since 2.0
 */
public interface ContainerResponseContext {

    /**
     * Get the status code associated with the response.
     *
     * @return the response status code or -1 if the status was not set.
     */
    public int getStatus();

    /**
     * Set a new response status code.
     *
     * @param code new status code.
     */
    public void setStatus(int code);

    /**
     * Get the complete status information associated with the response.
     *
     * @return the response status information or {@code null} if the status was
     *         not set.
     */
    public Response.StatusType getStatusInfo();

    /**
     * Set the complete status information associated with the response.
     *
     * @param statusInfo the response status information.
     */
    public void setStatusInfo(Response.StatusType statusInfo);

    /**
     * Get the mutable response headers multivalued map.
     *
     * @return mutable multivalued map of response headers.
     */
    public MultivaluedMap<String, Object> getHeaders();

    /**
     * Get the allowed HTTP methods from the Allow HTTP header.
     *
     * @return the allowed HTTP methods, all methods will returned as upper case
     *         strings.
     */
    public Set<String> getAllowedMethods();

    /**
     * Get message date.
     *
     * @return the message date, otherwise {@code null} if not present.
     */
    public Date getDate();

    /**
     * Get the language of the entity.
     *
     * @return the language of the entity or {@code null} if not specified
     */
    public Locale getLanguage();

    /**
     * Get Content-Length value.
     *
     * @return Content-Length as integer if present and valid number. In other
     *         cases returns -1.
     */
    public int getLength();

    /**
     * Get the media type of the entity.
     *
     * @return the media type or {@code null} if not specified (e.g. there's no
     *         response entity).
     */
    public MediaType getMediaType();

    /**
     * Get any new cookies set on the response message.
     *
     * @return a read-only map of cookie name (String) to a {@link NewCookie new cookie}.
     */
    public Map<String, NewCookie> getCookies();

    /**
     * Get the entity tag.
     *
     * @return the entity tag, otherwise {@code null} if not present.
     */
    public EntityTag getEntityTag();

    /**
     * Get the last modified date.
     *
     * @return the last modified date, otherwise {@code null} if not present.
     */
    public Date getLastModified();

    /**
     * Get the location.
     *
     * @return the location URI, otherwise {@code null} if not present.
     */
    public URI getLocation();

    /**
     * Get the links attached to the message as header.
     *
     * @return links, may return empty {@link Set} if no links are present. Never
     *         returns {@code null}.
     */
    public Set<Link> getLinks();

    /**
     * Check if link for relation exists.
     *
     * @param relation link relation.
     * @return {@code true} if the for the relation link exists, {@code false}
     *         otherwise.
     */
    boolean hasLink(String relation);

    /**
     * Get the link for the relation.
     *
     * @param relation link relation.
     * @return the link for the relation, otherwise {@code null} if not present.
     */
    public Link getLink(String relation);

    /**
     * Convenience method that returns a {@link javax.ws.rs.core.Link.Builder Link.Builder}
     * for the relation.
     *
     * @param relation link relation.
     * @return the link builder for the relation, otherwise {@code null} if not
     *         present.
     */
    public Link.Builder getLinkBuilder(String relation);

    /**
     * Check if there is an entity available in the response.
     *
     * The method returns {@code true} if the entity is present, returns
     * {@code false} otherwise.
     *
     * @return {@code true} if there is an entity present in the message,
     *         {@code false} otherwise.
     */
    public boolean hasEntity();

    /**
     * Get the message entity Java instance.
     *
     * Returns {@code null} if the message does not contain an entity.
     *
     * @return the message entity or {@code null} if message does not contain an
     *         entity body.
     */
    public Object getEntity();

    /**
     * Get the raw entity type information.
     *
     * @return raw entity type.
     */
    public Class<?> getEntityClass();

    /**
     * Get the generic entity type information.
     *
     * @return declared generic entity type.
     */
    public Type getEntityType();

    /**
     * Set a new response message entity.
     *
     * It is the callers responsibility to wrap the actual entity with
     * {@link javax.ws.rs.core.GenericEntity} if preservation of its generic
     * type is required.
     *
     * @param entity      entity object.
     * @param annotations annotations attached to the entity.
     * @param mediaType   entity media type.
     * @see MessageBodyWriter
     */
    public void setEntity(
            final Object entity,
            final Annotation[] annotations,
            final MediaType mediaType);

    /**
     * Set a new response message entity.
     *
     * @param entity      entity object.
     * @param type        declared generic entity type.
     * @param annotations annotations attached to the entity.
     * @param mediaType   entity media type.
     * @see MessageBodyWriter
     */
    public void setEntity(
            final Object entity,
            final Type type,
            final Annotation[] annotations,
            final MediaType mediaType);

    /**
     * Get the annotations attached to the entity.
     *
     * @return entity annotations.
     */
    public Annotation[] getEntityAnnotations();

    /**
     * Get the entity output stream.
     *
     * @return entity output stream.
     */
    public OutputStream getEntityStream();

    /**
     * Set a new entity output stream.
     *
     * @param outputStream new entity output stream.
     */
    public void setEntityStream(OutputStream outputStream);
}
