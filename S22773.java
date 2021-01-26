package project;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class S22773 {
    public static void main(String[] args) {

        Harbour port = new Harbour("Gdańsk", 50000);
        port.generateContainers(50);

        Ship ship = new Ship("Maersk", 10);

        for (int i=0; i<port.numOfContainers; i++) {
            System.out.print(i+1 + ". ");
            System.out.println(port.containers[i]);
        }

        port.saveToFile("C:\\2021Z\\containers.txt");
        ship.readFromFile("C:\\2021Z\\containers.txt");

        System.out.println(ship.load[0]);
    }
}

class Harbour {
    String cityName;
    int capacity;
    int numOfContainers;
    Container[] containers;

    public Harbour(String cityName, int capacity) {
        this.cityName = cityName;
        this.capacity = capacity;
        this.numOfContainers = 0;
        this.containers = new Container[capacity];
    }

    void generateContainers(int numOfContainers) {
        for (int i=this.numOfContainers; i<numOfContainers; i++) {
            float generatedMass;
            switch ((int)(Math.random() * 10)) {
                case 0:
                    generatedMass = (float)(Math.random() * 21.8);
                    this.containers[i] = new GeneralPurposeContainer(generatedMass, "general");
                    break;
                case 1:
                    generatedMass = (float)(Math.random() * 27.7);
                    this.containers[i] = new HardTopContainerShort(generatedMass, "short");
                    break;
                case 2:
                    generatedMass = (float)(Math.random() * 28.6);
                    this.containers[i] = new HardTopContainerLong(generatedMass, "long");
                    break;
                case 3:
                    generatedMass = (float)(Math.random() * 28.2);
                    this.containers[i] = new DoubleDoorContainer(generatedMass, "double");
                    break;
                case 4:
                    generatedMass = (float)(Math.random() * 40.0);
                    this.containers[i] = new FlatRackContainer(generatedMass, "flat");
                    break;
                case 5:
                    generatedMass = (float)(Math.random() * 26.5);
                    this.containers[i] = new HighCubeContainer(generatedMass, "high");
                    break;
                case 6:
                    generatedMass = (float)(Math.random() * 22.0);
                    this.containers[i] = new InsulatedContainer(generatedMass, "insulated");
                    break;
                case 7:
                    generatedMass = (float)(Math.random() * 28.2);
                    this.containers[i] = new OpenTopContainer(generatedMass, "open");
                    break;
                case 8:
                    generatedMass = (float)(Math.random() * 30.0);
                    this.containers[i] = new PalletWideContainer(generatedMass, "pallet");
                    break;
                case 9:
                    generatedMass = (float)(Math.random() * 36.0);
                    this.containers[i] = new TankContainer(generatedMass, "tank");
                    break;
                default:
                    System.out.println("BLAD PETLI!!!!"); // Make throw error
            }
            this.numOfContainers++;
        }
    }

    void saveToFile(String path) {
        try {
            FileWriter file = new FileWriter(path);

            for(int i=0; i<this.numOfContainers; i++) {
                file.write(this.containers[i].toString());
            }
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Ship {
    String name;
    int maxCapacity;
    int currentLoad;
    Container[] load;

    public Ship(String name, int maxCapacity) {
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.currentLoad = 0;
        this.load = new Container[maxCapacity];
    }

    public void readFromFile(String path) {
        try {
            FileReader file = new FileReader(path);

            int readedContainers = 0;

            while(readedContainers < this.maxCapacity) {
                char temp = (char)file.read();
                String concatenate = "";
                while(temp != '{') {
                    concatenate += temp;
                    temp = (char)file.read();
                }
                System.out.println(concatenate);

                while(temp != '=') {
                    temp = (char)file.read();
                }

                String massTemp = "";
                temp = (char)file.read();
                while(temp != ',') {
                    massTemp += temp;
                    temp = (char)file.read();

                }
                System.out.println(massTemp);

                while(temp != '\'') {
                    temp = (char)file.read();
                }

                temp = (char)file.read();
                String contentTemp = "";
                while(temp != '\'') {

                    contentTemp += temp;
                    temp = (char)file.read();
                }
                System.out.println(contentTemp);

                while(temp != '}') {
                    temp = (char)file.read();
                }



                switch(concatenate) {
                    case "GeneralPurposeContainer":
                        this.load[readedContainers] = new GeneralPurposeContainer(Float.parseFloat(massTemp), contentTemp);
                        break;
                    default:
                        break;

                }
                readedContainers++;
            }

            file.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Container {
    float mass; //t
    float maxLoad; //t
    int length;
    int width; //ft
    int height; //ft
    String content;

    public Container(float mass, float maxLoad, int length, int width, int height, String content) {
        this.mass = mass;
        this.maxLoad = maxLoad;
        this.length = length;
        this.width = width;
        this.height = height;
        this.content = content;
    }
}

class GeneralPurposeContainer extends Container {
    boolean weatherProof;

    public GeneralPurposeContainer(float mass, String content) {
        super(mass, 21.8f, 20, 8, 8, content);
        this.weatherProof = true;
    }

    @Override
    public String toString() {
        return "GeneralPurposeContainer{" +
                "mass=" + mass +
                ", maxLoad=" + maxLoad +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", content='" + content + '\'' +
                ", weatherProof=" + weatherProof +
                '}';
    }
}

class HardTopContainer extends Container {
    public HardTopContainer(float mass, float maxLoad, int length, String content) {
        super(mass, maxLoad, length, 8, 8, content);
    }

    @Override
    public String toString() {
        return "HardTopContainer{" +
                "mass=" + mass +
                ", maxLoad=" + maxLoad +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", content='" + content + '\'' +
                '}';
    }
}

class HardTopContainerShort extends HardTopContainer {
    public HardTopContainerShort(float mass, String content) {
        super(mass,27.7f, 20, content);
    }
}

class HardTopContainerLong extends HardTopContainer {
    public HardTopContainerLong(float mass, String content) {
        super(mass, 28.6f, 40, content);
    }
}

class DoubleDoorContainer extends Container {
    public DoubleDoorContainer(float mass, String content) {
        super(mass, 28.2f, 20, 8, 8, content);
    }

    @Override
    public String toString() {
        return "DoubleDoorContainer{" +
                "mass=" + mass +
                ", maxLoad=" + maxLoad +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", content='" + content + '\'' +
                '}';
    }
}

class FlatRackContainer extends Container {
    public FlatRackContainer(float mass, String content) {
        super(mass, 40f, 20, 8, 8, content);
    }

    @Override
    public String toString() {
        return "FlatRackContainer{" +
                "mass=" + mass +
                ", maxLoad=" + maxLoad +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", content='" + content + '\'' +
                '}';
    }
}

class OpenTopContainer extends Container {
    public OpenTopContainer(float mass, String content) {
        super(mass, 28.2f, 20, 8, 8, content);
    }

    @Override
    public String toString() {
        return "OpenTopContainer{" +
                "mass=" + mass +
                ", maxLoad=" + maxLoad +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", content='" + content + '\'' +
                '}';
    }
}

class HighCubeContainer extends Container {
    public HighCubeContainer(float mass, String content) {
        super(mass, 26.5f, 40, 8, 9, content);
    }

    @Override
    public String toString() {
        return "HighCubeContainer{" +
                "mass=" + mass +
                ", maxLoad=" + maxLoad +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", content='" + content + '\'' +
                '}';
    }
}

class InsulatedContainer extends Container {
    public InsulatedContainer(float mass, String content) {
        super(mass, 22f, 20, 8, 8, content);
    }

    @Override
    public String toString() {
        return "InsulatedContainer{" +
                "mass=" + mass +
                ", maxLoad=" + maxLoad +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", content='" + content + '\'' +
                '}';
    }
}

class TankContainer extends Container {
    int fluidCapacity;

    public TankContainer(float mass, String content) {
        super(mass, 36f, 20, 8, 8, content);
        this.fluidCapacity = 26000;
    }

    @Override
    public String toString() {
        return "TankContainer{" +
                "mass=" + mass +
                ", maxLoad=" + maxLoad +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", content='" + content + '\'' +
                ", fluidCapacity=" + fluidCapacity +
                '}';
    }
}

class PalletWideContainer extends Container {
    short maxPalletQuantity;

    public PalletWideContainer(float mass, String content) {
        super(mass, 30f, 40, 8, 9, content);
        this.maxPalletQuantity = (short)30;
    }

    @Override
    public String toString() {
        return "PalletWideContainer{" +
                "mass=" + mass +
                ", maxLoad=" + maxLoad +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", content='" + content + '\'' +
                ", maxPalletQuantity=" + maxPalletQuantity +
                '}';
    }
}