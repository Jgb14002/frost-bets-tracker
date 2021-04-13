package net.runelite.client.plugins.tradetracker;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("tradetracker")
public interface TradeTrackerConfig extends Config
{
	@ConfigItem(
		name = "API Key",
		description = "API key used to send requests to the server. You can request one on Guilded",
		keyName = "apiKey"
	)
	default String getApiKey()
	{
		return "";
	}
}
