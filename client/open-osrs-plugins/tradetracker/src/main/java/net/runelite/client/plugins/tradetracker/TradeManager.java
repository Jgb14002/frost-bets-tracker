package net.runelite.client.plugins.tradetracker;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.widgets.Widget;
import net.runelite.client.plugins.tradetracker.api.TradeListener;
import net.runelite.client.plugins.tradetracker.model.TradeItem;
import net.runelite.client.plugins.tradetracker.model.TradeLog;
import net.runelite.client.util.Text;

@Singleton
@Slf4j
public class TradeManager implements TradeListener
{
	private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance(Locale.US);
	private static final Pattern TRADING_WITH_PATTERN = Pattern.compile("^(?:.*>)(.*)$");
	private static final Pattern TRADE_ITEM_PATTERN = Pattern.compile("(?<name>^[\\w\\s]+)(((?:.*>(?=[,\\d]+$))|(?:.*\\((?=[,\\d]+\\)$)))(?<amount>[,\\d]+)\\)?)?");
	private static final String GROUP_ITEM_NAME = "name";
	private static final String GROUP_ITEM_AMOUNT = "amount";

	private final Client client;
	private TradeLog.TradeLogBuilder tradeLogBuilder;

	@Inject
	TradeManager(Client client)
	{
		this.client = client;
	}

	@Override
	public void onTradeItemsLoaded(Widget itemContainer)
	{
		final Widget[] itemWidgets = itemContainer.getDynamicChildren();

		if (itemWidgets == null)
		{
			return;
		}

		List<TradeItem> tradeItems = Arrays.stream(itemWidgets)
			.map(this::parseItemWidget)
			.filter(Optional::isPresent)
			.map(Optional::get)
			.collect(Collectors.toList());

		if (!tradeItems.isEmpty())
		{
			tradeLogBuilder = TradeLog.builder().tradeItems(tradeItems);
		}
	}

	@Override
	public void onTradeReceiverLoaded(Widget textWidget)
	{
		final Player localPlayer = client.getLocalPlayer();

		if (localPlayer == null)
		{
			return;
		}

		final Optional<String> bettorRsn = parseTradingWithWidget(textWidget);
		final String hostName = localPlayer.getName();

		if (tradeLogBuilder != null && bettorRsn.isPresent())
		{
			tradeLogBuilder.hostRsn(hostName).bettorRsn(bettorRsn.get());
		}
	}

	@Override
	public void onTradeCompleted()
	{
		if (tradeLogBuilder != null)
		{
			log.info("{}", tradeLogBuilder.build());
			tradeLogBuilder = null;
		}
	}

	private Optional<TradeItem> parseItemWidget(Widget widget)
	{
		final String widgetText = widget.getText();
		final Matcher matcher = TRADE_ITEM_PATTERN.matcher(widgetText);

		if (matcher.matches())
		{
			final String itemName = matcher.group(GROUP_ITEM_NAME);
			final String itemAmount = matcher.group(GROUP_ITEM_AMOUNT);

			try
			{
				TradeItem item = new TradeItem(
					itemName,
					itemAmount == null ? 1 : NUMBER_FORMAT.parse(itemAmount).intValue()
				);
				return Optional.of(item);
			}
			catch (ParseException ignored)
			{
			}
		}
		return Optional.empty();
	}

	private Optional<String> parseTradingWithWidget(Widget widget)
	{
		final String widgetText = widget.getText();
		final Matcher matcher = TRADING_WITH_PATTERN.matcher(widgetText);

		if (matcher.matches())
		{
			final String playerName = matcher.group(1);
			return Optional.of(Text.sanitize(playerName));
		}
		return Optional.empty();
	}
}
