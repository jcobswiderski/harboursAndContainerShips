package project;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLOutput;

public class S22773 {
    public static void main(String[] args) {
        Harbour port = new Harbour("Gdańsk", 30000);
        port.showInfo();
        port.generateContainers(311);
        port.showInfo();
        port.generateContainers(27);
        port.showInfo();
        port.saveToFile("C:\\2021Z\\containers.txt");

        Ship ship = new Ship("Maersk", 15000);
        ship.readFromFile("C:\\2021Z\\containers.txt");
        ship.generateManifest("C:\\2021Z\\arrangement.manifest");
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
            String[] cargoType;
            int drawCargo;

            switch ((int)(Math.random() * 10)) {
                case 0:
                    generatedMass = (float)(Math.random() * 21.8);
                    cargoType = new String[]{"electronics", "tools", "toys", "clothes"};
                    drawCargo = (int)(Math.random() * cargoType.length);
                    this.containers[i] = new GeneralPurposeContainer(generatedMass, cargoType[drawCargo]);
                    break;
                case 1:
                    generatedMass = (float)(Math.random() * 27.7);
                    cargoType = new String[]{"autoParts", "agriculturalParts", "machinery", "materials"};
                    drawCargo = (int)(Math.random() * cargoType.length);
                    this.containers[i] = new HardTopContainerShort(generatedMass, cargoType[drawCargo]);
                    break;
                case 2:
                    generatedMass = (float)(Math.random() * 28.6);
                    cargoType = new String[]{"boatParts", "planeParts", "machinery", "oversizedMaterials", "railwayEquipment"};
                    drawCargo = (int)(Math.random() * cargoType.length);
                    this.containers[i] = new HardTopContainerLong(generatedMass, cargoType[drawCargo]);
                    break;
                case 3:
                    generatedMass = (float)(Math.random() * 28.2);
                    cargoType = new String[]{"motorbikes", "cars", "snowmobiles", "materials", "unsortedMaterials"};
                    drawCargo = (int)(Math.random() * cargoType.length);
                    this.containers[i] = new DoubleDoorContainer(generatedMass, cargoType[drawCargo]);
                    break;
                case 4:
                    generatedMass = (float)(Math.random() * 40.0);
                    cargoType = new String[]{"industrialEquipment", "oversizedMaterials", "engines", "railwayEquipment"};
                    drawCargo = (int)(Math.random() * cargoType.length);
                    this.containers[i] = new FlatRackContainer(generatedMass, cargoType[drawCargo]);
                    break;
                case 5:
                    generatedMass = (float)(Math.random() * 26.5);
                    cargoType = new String[]{"industrialEquipment", "oversizedMaterials", "trucks", "forestryEquipment"};
                    drawCargo = (int)(Math.random() * cargoType.length);
                    this.containers[i] = new HighCubeContainer(generatedMass, cargoType[drawCargo]);
                    break;
                case 6:
                    generatedMass = (float)(Math.random() * 22.0);
                    cargoType = new String[]{"food", "medicines", "cosmetics", "chemicalProducts"};
                    drawCargo = (int)(Math.random() * cargoType.length);
                    this.containers[i] = new InsulatedContainer(generatedMass, cargoType[drawCargo]);
                    break;
                case 7:
                    generatedMass = (float)(Math.random() * 28.2);
                    cargoType = new String[]{"looseProducts", "airCirculationProducts", "wood", "fossilResources"};
                    drawCargo = (int)(Math.random() * cargoType.length);
                    this.containers[i] = new OpenTopContainer(generatedMass, cargoType[drawCargo]);
                    break;
                case 8:
                    generatedMass = (float)(Math.random() * 30.0);
                    cargoType = new String[]{"euroPallets", "undersizedPallets"};
                    drawCargo = (int)(Math.random() * cargoType.length);
                    this.containers[i] = new PalletWideContainer(generatedMass, cargoType[drawCargo]);
                    break;
                case 9:
                    generatedMass = (float)(Math.random() * 36.0);
                    cargoType = new String[]{"oil", "diesel", "petrol", "liquids", "grease"};
                    drawCargo = (int)(Math.random() * cargoType.length);
                    this.containers[i] = new TankContainer(generatedMass, cargoType[drawCargo]);
                    break;
                default:
                    System.out.println("There's no such type of container!");
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

    void showInfo() {
        System.out.println("Port: " + this.getCityName() + ", CONTAINERS [" + this.getNumOfStoredContainers() + " / " + this.getCapacity() + "]");
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

                StringBuffer containerName = new StringBuffer();
                while(temp != '{') {
                    containerName.append(temp);
                    temp = (char)file.read();
                }

                while(temp != '=') {
                    temp = (char)file.read();
                }

                StringBuffer massTemp = new StringBuffer();
                temp = (char)file.read();
                while(temp != ',') {
                    massTemp.append(temp);
                    temp = (char)file.read();
                }

                while(temp != '\'') {
                    temp = (char)file.read();
                }

                temp = (char)file.read();
                StringBuffer contentTemp = new StringBuffer();
                while(temp != '\'') {
                    contentTemp.append(temp);
                    temp = (char)file.read();
                }

                while(temp != '}') {
                    temp = (char)file.read();
                }

                switch(containerName.toString()) {
                    case "GeneralPurposeContainer":
                        this.load[this.currentLoad] = new GeneralPurposeContainer(Float.parseFloat(massTemp.toString()), contentTemp.toString());
                        break;
                    case "HardTopContainerShort":
                        this.load[this.currentLoad] = new HardTopContainerShort(Float.parseFloat(massTemp.toString()), contentTemp.toString());
                        break;
                    case "HardTopContainerLong":
                        this.load[this.currentLoad] = new HardTopContainerLong(Float.parseFloat(massTemp.toString()), contentTemp.toString());
                        break;
                    case "DoubleDoorContainer":
                        this.load[this.currentLoad] = new DoubleDoorContainer(Float.parseFloat(massTemp.toString()), contentTemp.toString());
                        break;
                    case "FlatRackContainer":
                        this.load[this.currentLoad] = new FlatRackContainer(Float.parseFloat(massTemp.toString()), contentTemp.toString());
                        break;
                    case "HighCubeContainer":
                        this.load[this.currentLoad] = new HighCubeContainer(Float.parseFloat(massTemp.toString()), contentTemp.toString());
                        break;
                    case "InsulatedContainer":
                        this.load[this.currentLoad] = new InsulatedContainer(Float.parseFloat(massTemp.toString()), contentTemp.toString());
                        break;
                    case "OpenTopContainer":
                        this.load[this.currentLoad] = new OpenTopContainer(Float.parseFloat(massTemp.toString()), contentTemp.toString());
                        break;
                    case "PalletWideContainer":
                        this.load[this.currentLoad] = new PalletWideContainer(Float.parseFloat(massTemp.toString()), contentTemp.toString());
                        break;
                    case "TankContainer":
                        this.load[this.currentLoad] = new TankContainer(Float.parseFloat(massTemp.toString()), contentTemp.toString());
                        break;
                    default:
                        System.out.println("CONTAINER LOADING FAILED!");
                }
                this.currentLoad++;
            }

            file.close();

            System.out.println("SHIP HAS BEEN LOADED WITH " + (this.getCurrentLoad() - initialyNumOfContainers) + " CONTAINERS");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateManifest(String path) {
        int[] containerID = new int[this.getCurrentLoad()];
        for(int i=0; i<this.getCurrentLoad(); i++)
            containerID[i] = i;

        for (int i = 0; i < containerID.length-1; i++)
            for (int j = 0; j < containerID.length-i-1; j++)
                if (this.load[containerID[j]].getMass() < this.load[containerID[j+1]].getMass())
                {
                    int temp = containerID[j];
                    containerID[j] = containerID[j+1];
                    containerID[j+1] = temp;
                }

        Container[] sectionOne = new Container[this.getCurrentLoad() / 5 + 5];
        Container[] sectionTwo = new Container[this.getCurrentLoad() / 5 + 1];
        Container[] sectionThree = new Container[this.getCurrentLoad() / 5 + 1];
        Container[] sectionFour = new Container[this.getCurrentLoad() / 5 + 1];
        Container[] sectionFive = new Container[this.getCurrentLoad() / 5 + 1];

        int counterSectionOne = 0;
        int counterSectionTwo = 0;
        int counterSectionThree = 0;
        int counterSectionFour = 0;
        int counterSectionFive = 0;

        int tempLeftContainers = this.currentLoad;
        System.out.println("MANIFEST FILE WITH PROPER CONTAINERS ARRANGEMENT HAS BEEN GENERATED");
        for(int i=1; i<=tempLeftContainers; i++) {
            switch(i % 5) {
                case 1:
                    sectionOne[counterSectionOne++] = this.load[containerID[i-1]];
                    break;
                case 2:
                    sectionTwo[counterSectionTwo++] = this.load[containerID[i-1]];
                    break;
                case 3:
                    sectionThree[counterSectionThree++] = this.load[containerID[i-1]];
                    break;
                case 4:
                    sectionFour[counterSectionFour++] = this.load[containerID[i-1]];
                    break;
                default:
                    sectionFive[counterSectionFive++] = this.load[containerID[i-1]];
                    break;
            }
        }

        try {
            FileWriter file = new FileWriter(path);

            float sumOfMass = 0;

            file.write("SECTION ONE\n");
            for(int i=0; i<counterSectionOne; i++) {
                file.write("CON" + (containerID[i*5]+1) + "\t");
                file.write("pos{" + "1:" + i + "}\t");
                file.write(sectionOne[i].getMass() + "\t");
                file.write(sectionOne[i].getContent() + "\n");
                sumOfMass += sectionOne[i].getMass();
            }
            file.write("SUM OF CONTAINER WEIGHTS: " + sumOfMass + "t\n");
            sumOfMass = 0;

            file.write("\nSECTION TWO\n");
            for(int i=0; i<counterSectionTwo; i++) {
                file.write("CON" + (containerID[i*5+1]+1) + "\t");
                file.write("pos{" + "2:" + i + "}\t");
                file.write(sectionTwo[i].getMass() + "\t");
                file.write(sectionTwo[i].getContent() + "\n");
                sumOfMass += sectionTwo[i].getMass();
            }
            file.write("SUM OF CONTAINER WEIGHTS: " + sumOfMass + "t\n");
            sumOfMass = 0;

            file.write("\nSECTION THREE\n");
            for(int i=0; i<counterSectionThree; i++) {
                file.write("CON" + (containerID[i*5+2]+1) + "\t");
                file.write("pos{" + "3:" + i + "}\t");
                file.write(sectionThree[i].getMass() + "\t");
                file.write(sectionThree[i].getContent() + "\n");
                sumOfMass += sectionThree[i].getMass();
            }
            file.write("SUM OF CONTAINER WEIGHTS: " + sumOfMass + "t\n");
            sumOfMass = 0;

            file.write("\nSECTION FOUR\n");
            for(int i=0; i<counterSectionFour; i++) {
                file.write("CON" + (containerID[i*5+3]+1) + "\t");
                file.write("pos{" + "4:" + i + "}\t");
                file.write(sectionFour[i].getMass() + "\t");
                file.write(sectionFour[i].getContent() + "\n");
                sumOfMass += sectionFour[i].getMass();
            }
            file.write("SUM OF CONTAINER WEIGHTS: " + sumOfMass + "t\n");
            sumOfMass = 0;

            file.write("\nSECTION FIVE\n");
            for(int i=0; i<counterSectionFive; i++) {
                file.write("CON" + (containerID[i*5+4]+1) + "\t");
                file.write("pos{" + "5:" + i + "}\t");
                file.write(sectionFive[i].getMass() + "\t");
                file.write(sectionFive[i].getContent() + "\n");
                sumOfMass += sectionFive[i].getMass();
            }
            file.write("SUM OF CONTAINER WEIGHTS: " + sumOfMass + "t\n");

            file.close();
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
}

class Container {
    private float mass; //t
    float maxLoad; //t
    int length;
    int width; //ft
    int height; //ft
    String content;

    public Container(float mass, float maxLoad, int length, int width, int height, String content) {
        this.setMass(mass);
        this.maxLoad = maxLoad;
        this.length = length;
        this.width = width;
        this.height = height;
        this.content = content;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public String getContent() {
        return this.content;
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
                "mass=" + getMass() +
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
                "mass=" + getMass() +
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
                "mass=" + getMass() +
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
                "mass=" + getMass() +
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
                "mass=" + getMass() +
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
                "mass=" + getMass() +
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
                "mass=" + getMass() +
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
                "mass=" + getMass() +
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
                "mass=" + getMass() +
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
                "mass=" + getMass() +
                ", maxLoad=" + maxLoad +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", content='" + content + '\'' +
                ", maxPalletQuantity=" + maxPalletQuantity +
                '}';
    }
}