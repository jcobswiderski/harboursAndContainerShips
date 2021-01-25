package project;

import java.util.Random;

public class S22773 {
    public static void main(String[] args) {

        Harbour port = new Harbour("Gdańsk", 25000);
        port.generateContainers(100);

        Ship ship = new Ship("Maersk", 1500);

        for (int i=0; i<port.numOfContainers; i++) {
            System.out.print(i+1 + ". ");
            System.out.println(port.containers[i]);
        }
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
                    this.containers[i] = new PalletWideContainer(generatedMass, "pallet", 30);
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
        return "GeneralPurposeContainer { "+
                mass + "t / " + maxLoad + "t, " +
                length + "ft x " + width + "ft x " + height + "ft, " +
                content + ", weatherProof=" +
                weatherProof + "}";
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
    public FlatRackContainer(float massLoad, String content) {
        super(massLoad, 40f, 20, 8, 8, content);
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
    public OpenTopContainer(float massLoad, String content) {
        super(massLoad, 28.2f, 20, 8, 8, content);
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
    public InsulatedContainer(float massLoad, String content) {
        super(massLoad, 22f, 20, 8, 8, content);
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

    public TankContainer(float massLoad, String content) {
        super(massLoad, 36f, 20, 8, 8, content);
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
    public short currentPalletQuantity;

    public PalletWideContainer(float massLoad, String content, int currentPalletQuantity) {
        super(massLoad, 30f, 40, 8, 9, content);
        this.maxPalletQuantity = (short)30;
        this.currentPalletQuantity = (short)currentPalletQuantity;
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
                ", currentPalletQuantity=" + currentPalletQuantity +
                '}';
    }
}