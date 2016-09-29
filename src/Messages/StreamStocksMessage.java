package Messages;

import Utils.HelperFunctions;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.LinkedList;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static Utils.HelperFunctions.*;

/**
 * Created by swc on 9/20/16.
 */
public class StreamStocksMessage {
    private List<String> _symbols;

    public List<String> getSymbols() { return _symbols; }

    public short Count() { return (_symbols == null) ? (short) 0 :  (short) _symbols.size(); }

    public void Add(String symbol)
    {
        if (_symbols==null) _symbols = new LinkedList<>();

        if (symbol !=null && !symbol.isEmpty() &&  !symbol.trim().isEmpty())
            _symbols.add(symbol);
    }

    public byte[] Encode()
    {
        ByteBuffer buffer = ByteBuffer.allocate(2 + Count()*6).order(ByteOrder.BIG_ENDIAN);


        if (_symbols != null)
        {
            buffer.putShort(Count());

            for (String symbol : _symbols)
            {
                String paddedSymbol = HelperFunctions.padRight(symbol,6);
                byte[] bytes = paddedSymbol.getBytes(StandardCharsets.US_ASCII);
                buffer.put(bytes);
            }
        }

        return buffer.array();
    }

    public static StreamStocksMessage Decode(byte[] bytes)
    {
        StreamStocksMessage message = null;

        if (bytes != null && bytes.length >= 2)
        {
            message = new StreamStocksMessage();
            message._symbols = new LinkedList<>();

            ByteBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN);

            int count =  buffer.getShort();

            for (int i = 0; i < count; i++)
            {
                if (buffer.remaining()>=6)
                {
                    byte[] tmp = new byte[6];
                    buffer.get(tmp);
                    String symbol = new String(tmp, 0, 6, StandardCharsets.US_ASCII);
                    if (symbol != null && !symbol.trim().isEmpty()) {
                        message.Add(symbol.trim());
                    } else {
                        message = null;
                        break;
                    }
                }
                else {
                    message = null;
                    break;
                }
            }
        }

        return message;
    }

}
