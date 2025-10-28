class Device {

    String deviceName;
    String status;
    String location;
    static int deviceCount = 0;

    public Device() {
        this.deviceName = "Unknown";
        this.status = "OFF";
        this.location = "Unassigned";
    }

    public Device(String deviceName, String status, String location) {
        this.deviceName = deviceName;
        this.status = status;
        this.location = location;
    }

    public void turnOn() {
        if (!status.equals("ON")) {
            status = "ON";
            System.out.println("-> " + deviceName + " is turning ON.\n");
        }
    }

    public void turnOff() {
        if (!status.equals("OFF")) {
            status = "OFF";
            System.out.println("-> " + deviceName + " is turning OFF.\n");
        }
    }

    public void displayInfo() {
        System.out.println("Device name: " + deviceName);
        System.out.println("Status: " + status);
        System.out.println("Location: " + location);
    }

    public static void showDeviceCount() {
        System.out.println("\nDevice Count: " + deviceCount);
    }
}

class LightBulb extends Device {

    int brightnessLevel;
    String color;

    public LightBulb(String deviceName, String status, String location, int brightnessLevel, String color) {
        super(deviceName, status, location);
        this.brightnessLevel = brightnessLevel;
        this.color = color;
    }

    public LightBulb(String deviceName, String location) {
        super(deviceName, "OFF", location);
        this.brightnessLevel = 50;
        this.color = "Warm White";
    }

    @Override
    public void turnOn() {
        super.turnOn();
        if (this.brightnessLevel == 0) {
            this.brightnessLevel = 50;
        }
    }

    @Override
    public void turnOff() {
        super.turnOff();
        this.brightnessLevel = 0;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Brightness Level: " + brightnessLevel + "%");
        System.out.println("Color: " + color);
        System.out.println("Type: LightBulb\n");

    }

    public void adjustBrightness(int level) {
        if (level >= 0 && level <= 100) {
            this.brightnessLevel = level;
            if (level > 0 && this.status.equals("OFF")) {
                turnOn();
            } else if (level == 0 && this.status.equals("ON")) {
                turnOff();
            }
        } else {
            System.out.println("Invalid brightness level. Must be between 0 and 100.");
        }
        System.out.println(deviceName + " brightness level set to " + level);
    }
}

class Thermostat extends Device {

    double temperature;
    String mode;

    public Thermostat(String deviceName, String location, double temperature, String mode) {
        super(deviceName, "OFF", location);
        this.temperature = temperature;
        this.mode = mode;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();

        System.out.println("Temperature: " + temperature + "°C");
        System.out.println("Mode: " + mode);
        System.out.println("Type: Thermostat\n");
    }

    public void setTemperature(double temp) {
        this.temperature = temp;
        System.out.println(deviceName + " temperature set to " + temp + "°C.");
    }

    public void setTemperature(double temp, String mode) {
        this.temperature = temp;
        this.mode = mode;
        System.out.println(deviceName + " temperature set to " + temp + "°C.\nMode: " + mode);
    }
}

class SmartHomeController {

    private Device[] controlledDevices;

    public SmartHomeController(Device[] devices) {
        this.controlledDevices = devices;
        System.out.println("\n*** Smart Home Controller Initialized. Managing " + devices.length + " devices. ***");
    }

    public void turnAllDevicesOn() {
        System.out.println("\n--- CONTROLLER ACTION: TURNING ALL DEVICES ON ---\n");
        for (Device device : controlledDevices) {
            device.turnOn();
        }
    }

    public void turnAllDevicesOff() {
        System.out.println("\n--- CONTROLLER ACTION: TURNING ALL DEVICES OFF ---\n");
        for (Device device : controlledDevices) {
            device.turnOff();
        }
    }
}

class SmartHomeMain {

    public static void main(String[] args) {
        System.out.println("\n--- Smart Home System Start ---\n");

        LightBulb livingRoomLight = new LightBulb("Living Room Light", "Living Room");
        Device.deviceCount++;
        Thermostat kitchenThermostat = new Thermostat("Kitchen Thermostat", "Kitchen", 25.0, "HEATING");
        Device.deviceCount++;
        Thermostat bedroomThermostat = new Thermostat("Bedroom Thermostat", "Bedroom", 20.0, "COOLING");
        Device.deviceCount++;

        Device.showDeviceCount();

        Device[] smartDevices = {
            livingRoomLight,
            kitchenThermostat,
            bedroomThermostat
        };

        System.out.println("\n==================================");
        System.out.println("    INITIAL DEVICE STATUS REPORT    ");
        System.out.println("==================================\b");
        System.out.println("DEVICES:");
        System.out.println("----------------------------------");
        for (Device device : smartDevices) {
            device.displayInfo();
            System.out.println("----------------------------------\n");
        }

        livingRoomLight.adjustBrightness(80);
        kitchenThermostat.turnOn();
        kitchenThermostat.setTemperature(20, "COOLING");

        System.out.println("\n==================================");
        System.out.println("    UPDATED DEVICE STATUS REPORT    ");
        System.out.println("==================================\b");
        System.out.println("DEVICES:");
        System.out.println("----------------------------------\n");
        for (Device device : smartDevices) {
            device.displayInfo();
            System.out.println("----------------------------------");
        }

        SmartHomeController controller = new SmartHomeController(smartDevices);

        controller.turnAllDevicesOn();
        bedroomThermostat.setTemperature(16);
        System.out.println("\n==================================");
        System.out.println("    STATUS AFTER ALL ON ACTION    ");
        System.out.println("==================================\n");
        System.out.println("DEVICES:");
        for (Device device : smartDevices) {
            device.displayInfo();
            System.out.println("----------------------------------");
        }

        controller.turnAllDevicesOff();

        System.out.println("\n==================================");
        System.out.println("    FINAL STATUS AFTER ALL OFF    ");
        System.out.println("==================================\n");
        System.out.println("DEVICES:");
        for (Device device : smartDevices) {
            device.displayInfo();
            System.out.println("----------------------------------");
        }
    }
}
