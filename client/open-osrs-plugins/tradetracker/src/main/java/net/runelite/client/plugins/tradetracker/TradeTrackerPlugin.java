package net.runelite.client.plugins.tradetracker;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetID;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import org.pf4j.Extension;

@PluginDescriptor(
	name = "Trade Tracker",
	description = "Logs host trades with bettors to automate Guilded ticket distribution",
	tags = {"guilded", "frost", "bets", "ticket"},
	enabledByDefault = false
)
@Extension
@Slf4j
public class TradeTrackerPlugin extends Plugin
{
	private static final String TRADE_ACCEPTED_MESSAGE = "Accepted trade.";
	private static final String CONFIG_GROUP = "tradetracker";
	private static final String CONFIG_KEY = "apiKey";

	@Inject
	private Client client;

	@Inject
	private TradeManager tradeManager;

	@Inject
	private ChatService chatService;

	@Inject
	private TradeTrackerConfig config;

	@Provides
	TradeTrackerConfig getConfig(ConfigManager manager)
	{
		return manager.getConfig(TradeTrackerConfig.class);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		final String group = event.getGroup();
		final String key = event.getKey();

		if (!CONFIG_GROUP.equals(group) || !CONFIG_KEY.equals(key))
		{
			return;
		}
		chatService.sendGameMessage(String.format("API Key: %s", config.getApiKey()));
	}

	@Subscribe
	public void onChatMessage(ChatMessage event)
	{
		final ChatMessageType type = event.getType();

		if (ChatMessageType.TRADE != type)
		{
			return;
		}

		final String message = event.getMessage();

		if (TRADE_ACCEPTED_MESSAGE.equals(message))
		{
			tradeManager.onTradeCompleted();
		}
	}

	@Subscribe
	public void onWidgetLoaded(WidgetLoaded event)
	{
		final int groupId = event.getGroupId();

		if (WidgetID.PLAYER_TRADE_CONFIRM_GROUP_ID == groupId)
		{
			final Widget offerWidget = client.getWidget(WidgetInfo.SECOND_TRADING_WITH_THEIR_ITEMS);
			final Widget tradingWithWidget = client.getWidget(WidgetInfo.SECOND_TRADING_WITH);

			if (offerWidget != null && tradingWithWidget != null)
			{
				tradeManager.onTradeItemsLoaded(offerWidget);
				tradeManager.onTradeReceiverLoaded(tradingWithWidget);
			}
		}
	}
}
