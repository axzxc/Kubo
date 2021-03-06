package com.github.ar3s3ru.kubo.backend.net;

/**
 * Copyright (C) 2016  Danilo Cianfrone
 * <p>
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */

/**
 * Containers for all the private intents to use within the application,
 * as requests directed to the IntentService.
 */
class KuboRESTIntents {
    /** getBoards() request */
    final static String GET_BOARDS  = "com.github.ar3s3ru.kubo.api.getboards";

    /** getCatalog(path) request */
    final static String GET_CATALOG      = "com.github.ar3s3ru.kubo.api.getcatalog";
    final static String GET_CATALOG_PATH = "com.github.ar3s3ru.kubo.api.getcatalog.path";

    /** getReplies(path, number) request */
    final static String GET_REPLIES        = "com.github.ar3s3ru.kubo.api.getreplies";
    final static String GET_REPLIES_PATH   = "com.github.ar3s3ru.kubo.api.getreplies.path";
    final static String GET_REPLIES_NUMBER = "com.github.ar3s3ru.kubo.api.getreplies.number";
}
