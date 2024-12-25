/*
 * Copyright (c) 2016-2017, Adam <Adam@sigterm.info>
 * Copyright (c) 2018, Tomas Slusny <slusnucky@gmail.com>
 * Copyright (c) 2018 Abex
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.rs;

import com.google.common.io.ByteStreams;
import java.applet.Applet;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.RuneLite;
import net.runelite.client.ui.RuneLiteSplashScreen;
import net.runelite.standalone.Client;

@Slf4j
public class ClientLoader implements Supplier<Applet>
{
	private ClientUpdateCheckMode updateCheckMode;
	private Object client = null;

	public ClientLoader(ClientUpdateCheckMode updateCheckMode)
	{
		this.updateCheckMode = updateCheckMode;
	}

	@Override
	public synchronized Applet get()
	{
		if (client == null)
		{
			client = doLoad();
		}

		if (client instanceof Throwable)
		{
			throw new RuntimeException((Throwable) client);
		}
		return (Applet) client;
	}

	private Object doLoad()
	{
		try
		{
			RuneLiteSplashScreen.stage(.2, "Fetching applet viewer config");

			final RSConfig config = ClientConfigLoader.fetch().blockingGet();

			return loadFromClass(config, Client.class);
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			log.error("Error loading RS!", e);
			return null;
		}
	}

	private static Applet loadFromClass(final RSConfig config, final Class<?> clientClass)
	throws IllegalAccessException, InstantiationException
	{
		final Applet rs = (Applet) clientClass.newInstance();
		rs.setStub(new RSAppletStub(config));
		return rs;
	}
}
