package innocenti.luca.com.surfacemines;

import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import com.google.android.gms.maps.model.Tile;
import com.google.android.gms.maps.model.TileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CustomMapTileProvider implements TileProvider {
    private static final int TILE_WIDTH = 256;
    private static final int TILE_HEIGHT = 256;
    private static final int BUFFER_SIZE = 16 * 1024;

    private AssetManager mAssets;
    private File card;

    public CustomMapTileProvider() {
        //mAssets = assets;
    }

    @Override
    public Tile getTile(int x, int y, int zoom) {
        byte[] image = readTileImage(x, y, zoom);
        return image == null ? null : new Tile(TILE_WIDTH, TILE_HEIGHT, image);
    }

    private byte[] readTileImage(int x, int y, int zoom) {
        InputStream in = null;
        ByteArrayOutputStream buffer = null;

        try {

            //in = mAssets.open("/storage/emulated/0/OpenRisk/Map/" + getTileFilename(x, y, zoom));
            card = Environment.getExternalStorageDirectory();
            File f = new File(Environment.getExternalStorageDirectory().getPath()+"/OpenRisk/Map/"+getTileFilename(x, y, zoom)+".png");
            Log.d("OpenRisk", f.toString());
            in = new FileInputStream(f);

            //in = open("/storage/emulated/0/OpenRisk/Map/" + getTileFilename(x, y, zoom));
            Log.d("MapAssets",in.toString());

            if (in != null)
            {
                Log.d("OpenRisk","ERRORE SU ASSETS");
            }
            buffer = new ByteArrayOutputStream();

            int nRead;
            byte[] data = new byte[BUFFER_SIZE];

            while ((nRead = in.read(data, 0, BUFFER_SIZE)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();

            return buffer.toByteArray();
        } catch (IOException e) {
            Log.d("OpenRisk","ERRORE SU ASSETS");

            e.printStackTrace();
            return null;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        } finally {
            if (in != null) try { in.close(); } catch (Exception ignored) {}
            if (buffer != null) try { buffer.close(); } catch (Exception ignored) {}
        }
    }

    private String getTileFilename(int x, int y, int zoom) {
        Log.d("OpenRisk", "" + zoom + '/' + x + '/' + y + "");
        return "" + zoom + '/' + x + '/' + y + "";

    }
}