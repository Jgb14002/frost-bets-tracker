package net.runelite.client.plugins.tradetracker;

import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.client.chat.ChatColorType;
import net.runelite.client.chat.ChatMessageBuilder;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;

@Singleton
@Slf4j
class ChatService
{
	private final ChatMessageManager chatMessageManager;

	@Inject
	ChatService(ChatMessageManager chatMessageManager)
	{
		this.chatMessageManager = chatMessageManager;
	}

	void sendGameMessage(String message)
	{
		final String builtMessage = new ChatMessageBuilder()
			.append(ChatColorType.HIGHLIGHT)
			.append(message)
			.build();

		chatMessageManager.queue(QueuedMessage.builder()
			.type(ChatMessageType.PLAYERRELATED)
			.runeLiteFormattedMessage(builtMessage)
			.build());
	}
}
