import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;

public class ColorSensor {
  public I2CBus bus;
  public I2CDevice device;

  public ColorSensor() throws Exception {
    bus = I2CFactory.getInstance(I2CBus.BUS_1);
    device = bus.getDevice(0x29);
    device.write(0x80, (byte) 0x03);
    device.write(0x81, (byte) 0x00);
    device.write(0x83, (byte) 0xFF);
    device.write(0x8F, (byte)0x00);
		Thread.sleep(800);
  }

  public int[] readColors() throws Exception {
    byte[] data = new byte[8];
    int[] colors = new int[4];
		device.read(0x94, data, 0, 8);
    colors[0] = ((data[1] & 0xFF) * 256) + (data[0] & 0xFF); //Clear
		colors[1] = ((data[3] & 0xFF) * 256) + (data[2] & 0xFF); //Red
		colors[2] = ((data[5] & 0xFF) * 256) + (data[4] & 0xFF); //Green
		colors[3] = ((data[7] & 0xFF) * 256) + (data[6] & 0xFF); //Blue
    return colors;
  }
}