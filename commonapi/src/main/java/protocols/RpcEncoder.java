package protocols;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import modes.Constants;
import utils.SerializationUtil;

public class RpcEncoder extends MessageToByteEncoder {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object requestBoday, ByteBuf out) throws Exception {
        byte[] data = SerializationUtil.serialize(requestBoday);
        out.writeBytes(Constants.SERVIE_HEARD.getBytes());
        out.writeInt(data.length);
        out.writeBytes(data);
    }
}
