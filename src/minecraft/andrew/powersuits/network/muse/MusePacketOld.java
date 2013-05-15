package andrew.powersuits.network.muse;

import andrew.powersuits.common.AddonLogger;
import andrew.powersuits.network.AndrewPacketHandler;
import cpw.mods.fml.common.network.Player;
import net.machinemuse.powersuits.common.Config;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by User: Andrew2448
 * 7:40 PM 5/15/13
 */
public abstract class MusePacketOld {
    protected static final int READ_ERROR = -150;

    protected Player player;

    protected ByteArrayOutputStream bytes;

    protected Packet250CustomPayload packet;
    protected DataOutputStream dataout;
    protected DataInputStream datain;
    protected int id;

    protected MusePacketOld(Player player) {
        this.player = player;
        this.bytes = new ByteArrayOutputStream();
        this.dataout = new DataOutputStream(bytes);
        try {
            int id = AndrewPacketHandler.getTypeID(this);
            writeInt(id);
        } catch (NullPointerException e) {
        }
    }

    protected MusePacketOld(DataInputStream data, Player player) {
        this.player = player;
        this.datain = data;
    }

    /**
     * Gets the MC packet associated with this MusePacket
     *
     * @return Packet250CustomPayload
     */
    public Packet250CustomPayload getPacket250() {
        return new Packet250CustomPayload(Config.getNetworkChannelName(),
                bytes.toByteArray());
    }

    /**
     * Called by the network manager since it does all the packet mapping
     *
     * @param player
     */
    public abstract void handleClient(EntityClientPlayerMP player);

    public abstract void handleServer(EntityPlayerMP player);

    public int readInt() {
        try {
            int read = datain.readInt();
            return read;
        } catch (IOException e) {
            AddonLogger.logError("PROBLEM READING INT FROM PACKET D:");
            e.printStackTrace();
            return READ_ERROR;
        }
    }

    public void writeInt(int i) {
        try {
            dataout.writeInt(i);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public int[] readIntArray() {
        try {
            int length = datain.readInt();
            int[] read = new int[length];
            for(int k=0;k<read.length;k++) {
                read[k] = datain.readInt();
            }
            return read;
        } catch (IOException e) {
            AddonLogger.logError("PROBLEM READING INT FROM PACKET D:");
            e.printStackTrace();
            return null;
        }
    }

    public void writeIntArray(int[] data) {
        try {
            dataout.writeInt(data.length);
            for(int k=0;k<data.length;k++) {
                dataout.writeInt(data[k]);
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public boolean readBoolean() {
        try {
            boolean read = datain.readBoolean();
            return read;
        } catch (IOException e) {
            AddonLogger.logError("PROBLEM READING DOUBLE FROM PACKET D:");
            e.printStackTrace();
            return false;
        }
    }

    public void writeBoolean(boolean val) {
        try {
            dataout.writeBoolean(val);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double readDouble() {
        try {
            double read = datain.readDouble();
            return read;
        } catch (IOException e) {
            AddonLogger.logError("PROBLEM READING DOUBLE FROM PACKET D:");
            e.printStackTrace();
            return READ_ERROR;
        }
    }

    public void writeDouble(double i) {
        try {
            dataout.writeDouble(i);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    /**
     * Reads a IC2ItemTest from the InputStream
     */
    public ItemStack readItemStack() {
        ItemStack stack = null;
        try {
            short itemID = datain.readShort();

            if (itemID >= 0) {
                byte stackSize = datain.readByte();
                short damageAmount = datain.readShort();
                stack = new ItemStack(itemID, stackSize, damageAmount);
                stack.stackTagCompound = readNBTTagCompound();
            }

        } catch (IOException e) {
            AddonLogger.logError("Problem reading itemstack D:");
            e.printStackTrace();
        }
        return stack;
    }

    /**
     * Writes the IC2ItemTest's ID (short), then size (byte), then damage. (short)
     */
    public void writeItemStack(ItemStack stack) {
        try {
            if (stack == null) {
                dataout.writeShort(-1);
            } else {
                dataout.writeShort(stack.itemID);
                dataout.writeByte(stack.stackSize);
                dataout.writeShort(stack.getItemDamage());
                NBTTagCompound nbt = null;

                if (stack.getItem().isDamageable()
                        || stack.getItem().getShareTag()) {
                    nbt = stack.stackTagCompound;
                }

                writeNBTTagCompound(nbt);
            }
        } catch (IOException e) {
            AddonLogger.logError("Problem writing itemstack D:");
            e.printStackTrace();
        }
    }

    /**
     * Reads a compressed NBTTagCompound from the InputStream
     */
    public NBTTagCompound readNBTTagCompound() throws IOException {
        short length = datain.readShort();

        if (length < 0) {
            return null;
        } else {
            byte[] fullData = new byte[length];
            datain.readFully(fullData);
            return CompressedStreamTools.decompress(fullData);
        }
    }

    /**
     * Writes a compressed NBTTagCompound to the OutputStream
     */
    protected void writeNBTTagCompound(
            NBTTagCompound nbt) throws IOException {
        if (nbt == null) {
            dataout.writeShort(-1);
        } else {
            byte[] compressednbt = CompressedStreamTools.compress(nbt);
            dataout.writeShort((short) compressednbt.length);
            dataout.write(compressednbt);
        }
    }

    /**
     * Writes a String to the DataOutputStream
     */
    public void writeString(String string) {
        try {
            dataout.writeShort(string.length());
            dataout.writeChars(string);
        } catch (IOException e) {
            AddonLogger.logError("String too big D:");
            e.printStackTrace();
        }
    }

    /**
     * Reads a string from a packet
     */
    public String readString(int maxlength) {
        String read = null;
        try {
            short length = datain.readShort();

            if (length > maxlength) {
                throw new IOException(
                        "Received string length longer than maximum allowed ("
                                + length + " > " + maxlength + ')');
            } else if (length < 0) {
                throw new IOException(
                        "Received string length is less than zero! Weird string!");
            } else {
                StringBuilder builder = new StringBuilder();

                for (int i = 0; i < length; ++i) {
                    builder.append(datain.readChar());
                }

                read = builder.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return read;
    }

}
