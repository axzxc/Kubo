package com.github.ar3s3ru.kubo.components;

import com.github.ar3s3ru.kubo.backend.net.KuboPushService;
import com.github.ar3s3ru.kubo.backend.net.KuboRESTService;
import com.github.ar3s3ru.kubo.modules.KuboAppModule;
import com.github.ar3s3ru.kubo.modules.KuboDBModule;
import com.github.ar3s3ru.kubo.modules.KuboNetModule;

import javax.inject.Singleton;

import dagger.Component;

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

@Singleton
@Component(modules = {KuboAppModule.class, KuboNetModule.class, KuboDBModule.class})
public interface KuboNetComponent {
    void inject(KuboRESTService service);
    void inject(KuboPushService service);
}
