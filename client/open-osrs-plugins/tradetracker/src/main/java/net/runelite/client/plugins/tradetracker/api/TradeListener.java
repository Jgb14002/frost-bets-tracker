package net.runelite.client.plugins.tradetracker.api;

import com.google.inject.ImplementedBy;
import net.runelite.api.widgets.Widget;
import net.runelite.client.plugins.tradetracker.TradeManager;

@ImplementedBy(TradeManager.class)
public interface TradeListener
{
	void onTradeItemsLoaded(Widget itemContainer);

	void onTradeReceiverLoaded(Widget textWidget);

	void onTradeCompleted();
}
