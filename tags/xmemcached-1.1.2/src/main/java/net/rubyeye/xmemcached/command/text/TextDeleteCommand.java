package net.rubyeye.xmemcached.command.text;

import java.nio.ByteBuffer;
import java.util.concurrent.CountDownLatch;

import net.rubyeye.xmemcached.buffer.BufferAllocator;
import net.rubyeye.xmemcached.codec.MemcachedDecoder;
import net.rubyeye.xmemcached.command.DeleteCommand;
import net.rubyeye.xmemcached.impl.MemcachedTCPSession;
import net.rubyeye.xmemcached.monitor.Constants;
import net.rubyeye.xmemcached.utils.ByteUtils;

public class TextDeleteCommand extends DeleteCommand {

	public static final byte[] DELETE = { 'd', 'e', 'l', 'e', 't', 'e' };

	public TextDeleteCommand(String key, byte[] keyBytes, int time,
			final CountDownLatch latch, boolean noreply) {
		super(key, keyBytes, time, latch, noreply);
	}

	@Override
	public final boolean decode(MemcachedTCPSession session, ByteBuffer buffer) {
		String line = MemcachedDecoder.nextLine(session, buffer);
		if (line != null) {
			if (line.equals("DELETED")) {
				setResult(Boolean.TRUE);
				countDownLatch();
				return true;
			} else if (line.equals("NOT_FOUND")) {
				setResult(Boolean.FALSE);
				countDownLatch();
				return true;
			} else
				decodeError(line);
		}
		return false;
	}

	@Override
	public final void encode(BufferAllocator bufferAllocator) {
		byte[] timeBytes = ByteUtils.getBytes(String.valueOf(time));
		int capacity = TextDeleteCommand.DELETE.length + 2 + keyBytes.length
				+ timeBytes.length + Constants.CRLF.length;
		if (isNoreply())
			capacity += 1 + Constants.NO_REPLY.length();
		this.ioBuffer = bufferAllocator.allocate(capacity);
		if (isNoreply())
			ByteUtils.setArguments(this.ioBuffer, TextDeleteCommand.DELETE,
					keyBytes, timeBytes, Constants.NO_REPLY);
		else
			ByteUtils.setArguments(this.ioBuffer, TextDeleteCommand.DELETE,
					keyBytes, timeBytes);
		this.ioBuffer.flip();
	}

}