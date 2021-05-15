package model;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ZombiesConfig {
    Properties properties = new Properties();
    private int row;
    private int column;
    private int width;
    private int height;
    private static ZombiesConfig zombiesConfig = null;
    public static ZombiesConfig instance() {
        if(zombiesConfig == null) {
            return new ZombiesConfig();
        }
        return zombiesConfig;
    }
    private ZombiesConfig() {
        FileReader inStream = null;
        try {
            inStream = new FileReader("game.properties");
            properties.load(inStream);
            row = Integer.parseInt(properties.getProperty("row"));
            column = Integer.parseInt(properties.getProperty("column"));
            width = Integer.parseInt(properties.getProperty("width"));
            height = Integer.parseInt(properties.getProperty("height"));

            //closeMove(height,width);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
}
