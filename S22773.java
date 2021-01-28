package project;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class S22773 {
    public static void main(String[] args) {

        Harbour port = new Harbour("Gdańsk", 200);
        port.showContainers();
        port.generateContainers(5);
        port.showContainers();
        port.generateContainers(5);
        port.showContainers();
        port.generateContainers(12);
        port.showContainers();
        port.saveToFile("C:\\2021Z\\containers.txt");

        Ship ship = new Ship("Maersk", 20);
        ship.readFromFile("C:\\2021Z\\containers.txt");
        ship.showLoad();
    }
}

class Harbour {
    private String cityName;
    protected int capacity;
    protected int numOfStoredContainers;
    Container[] containers;

    public Harbour(String cityName, int capacity) {
        this.cityName = cityName;
        this.capacity = capacity;
        this.numOfStoredContainers = 0;
        this.containers = new Container[capacity];
    }

    void generateContainers(int numOfContainers) {

        if(this.getNumOfStoredContainers() + numOfContainers > this.getCapacity()) {
            numOfContainers = this.getCapacity() - this.getNumOfStoredContainers();
            System.out.println("YOU TRY TO GENERATE TOO MUCH CONTAINERS! NUM OF LOADED CONTAINERS IS CUT TO THE MAX");
        }

        System.out.println("GENERATE " + numOfContainers + " CONTAINERS IN " + this.getCityName());
        for (int i=this.numOfStoredContainers; i<this.getNumOfStoredContainers()+numOfContainers; i++) {
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

        }
        this.numOfStoredContainers += numOfContainers;
    }

    void saveToFile(String path) {
        try {
            FileWriter file = new FileWriter(path);

            for(int i=0; i<this.numOfStoredContainers; i++) {
                file.write(this.containers[i].toString());
            }
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void showContainers() {
        System.out.println(
                '\n' + "PORT: " + this.getCityName() + ", CONTAINERS: " + this.getNumOfStoredContainers() + " / " + this.getCapacity()
        );

        for (int i=0; i<this.numOfStoredContainers; i++) {
            System.out.println(
                    i + 1 + ". " + this.containers[i]
            );
        }
        System.out.println();
    }

    String getCityName() {
        return this.cityName;
    }

    int getNumOfStoredContainers() {
        return this.numOfStoredContainers;
    }

    int getCapacity() {
        return this.capacity;
    }
}

class Ship {
    private String name;
    protected int maxCapacity;
    protected int currentLoad;
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

            System.out.println("SHIP LOADING IN PROGRESS...");
            int initialyNumOfContainers = this.getCurrentLoad();

            while(this.currentLoad < this.maxCapacity) {
                char temp = (char)file.read();

                if(temp == '\uFFFF') {
                    break;
                }

                String containerName = "";
                while(temp != '{') {
                    containerName += temp;
                    temp = (char)file.read();
                }

                while(temp != '=') {
                    temp = (char)file.read();
                }

                String massTemp = "";
                temp = (char)file.read();
                while(temp != ',') {
                    massTemp += temp;
                    temp = (char)file.read();
                }

                while(temp != '\'') {
                    temp = (char)file.read();
                }

                temp = (char)file.read();
                String contentTemp = "";
                while(temp != '\'') {
                    contentTemp += temp;
                    temp = (char)file.read();
                }

                while(temp != '}') {
                    temp = (char)file.read();
                }

                switch(containerName) {
                    case "GeneralPurposeContainer":
                        this.load[this.currentLoad] = new GeneralPurposeContainer(Float.parseFloat(massTemp), contentTemp);
                        break;
                    case "HardTopContainerShort":
                        this.load[this.currentLoad] = new HardTopContainerShort(Float.parseFloat(massTemp), contentTemp);
                        break;
                    case "HardTopContainerLong":
                        this.load[this.currentLoad] = new HardTopContainerLong(Float.parseFloat(massTemp), contentTemp);
                        break;
                    case "DoubleDoorContainer":
                        this.load[this.currentLoad] = new DoubleDoorContainer(Float.parseFloat(massTemp), contentTemp);
                        break;
                    case "FlatRackContainer":
                        this.load[this.currentLoad] = new FlatRackContainer(Float.parseFloat(massTemp), contentTemp);
                        break;
                    case "HighCubeContainer":
                        this.load[this.currentLoad] = new HighCubeContainer(Float.parseFloat(massTemp), contentTemp);
                        break;
                    case "InsulatedContainer":
                        this.load[this.currentLoad] = new InsulatedContainer(Float.parseFloat(massTemp), contentTemp);
                        break;
                    case "OpenTopContainer":
                        this.load[this.currentLoad] = new OpenTopContainer(Float.parseFloat(massTemp), contentTemp);
                        break;
                    case "PalletWideContainer":
                        this.load[this.currentLoad] = new PalletWideContainer(Float.parseFloat(massTemp), contentTemp);
                        break;
                    case "TankContainer":
                        this.load[this.currentLoad] = new TankContainer(Float.parseFloat(massTemp), contentTemp);
                        break;
                    default:
                        System.out.println("BLAD WCZYTU NA STATEK");
                }
                this.currentLoad++;
            }

            file.close();

            System.out.println("SHIP HAS BEEN LOADED BY " + (this.getCurrentLoad() - initialyNumOfContainers) + " CONTAINERS");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String getName() {
        return this.name;
    }

    int getCurrentLoad() {
        return this.currentLoad;
    }

    int getMaxCapacity() {
        return this.maxCapacity;
    }

    void showLoad() {
        System.out.println("\nSHIP: " + this.getName() + ", CONTAINERS: " + this.getCurrentLoad() + " / " + this.getMaxCapacity());
        for(int i=0; i<this.currentLoad; i++) {
            System.out.println(i+1 + ". " + this.load[i]);
        }
        System.out.println();
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
}

class HardTopContainerShort extends HardTopContainer {
    public HardTopContainerShort(float mass, String content) {
        super(mass,27.7f, 20, content);
    }

    @Override
    public String toString() {
        return "HardTopContainerShort{" +
                "mass=" + mass +
                ", maxLoad=" + maxLoad +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", content='" + content + '\'' +
                '}';
    }
}

class HardTopContainerLong extends HardTopContainer {
    public HardTopContainerLong(float mass, String content) {
        super(mass, 28.6f, 40, content);
    }

    @Override
    public String toString() {
        return "HardTopContainerLong{" +
                "mass=" + mass +
                ", maxLoad=" + maxLoad +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", content='" + content + '\'' +
                '}';
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