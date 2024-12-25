package net.runelite.client.plugins.privateserver;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("privateserver")

public interface PrivateServerConfig extends Config
{

	@ConfigItem(
		keyName = "codebase",
		name = "URL (codebase)",
		description = "IP address or URL of RSPS you wish to connect to.",
		position = 0
	)
	default String codebase()
	{
		return "http://127.0.0.1";
	}

	@ConfigItem(
		keyName = "modulus",
		name = "Key (modulus)",
		description = "RSA key used by the RSPS you wish to connect to.",
		position = 1
	)
	default String modulus()
	{
		return "94210824259843347324509385276594109263523823612210415282840685497179394322370180677069205378760490069724955139827325518162089726630921395369270393801925644637806226306156731189625154078707248525519618118185550146216513714101970726787284175941436804270501308516733103597242337227056455402809871503542425244523";
	}

}