package net.runelite.client.plugins.tradetracker.model;

import java.util.Collection;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(access = AccessLevel.PUBLIC)
public class TradeLog
{
	private final String hostRsn;
	private final String bettorRsn;
	private final Collection<TradeItem> tradeItems;
}
