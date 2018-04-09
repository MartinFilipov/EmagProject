
public class Laptop extends DeviceWithOperatingSystem implements ILaptop, Testable, IElectronics{

	private static final int MIN_MEMORY = 1024;
	private static final double MAX_PROCESSOR_SPEED = 4.0;
	private static final double MIN_PROCESSOR_SPEED = 1.4;
	
	private static final double CHANCE_FOR_LAPTOP_NOT_TO_TURN_ON_OFF = 0.9999998;
	private static final double CHANCE_FOR_KEYBOARD_NOT_TO_WORK = 0.998;
	private static final double CHANCE_FOR_TOUCHPAD_NOT_TO_WORK = 0.97;
	
	private double processorSpeed;
	private int memory;

	public Laptop(String name, double price, int quantity, String model, String operatingSystem, double processorSpeed, int memory) {
		super(name, price, quantity, model, operatingSystem);
		setProcessorSpeed(processorSpeed);
		setMemory(memory);
	}

	//----------------------------------------ILAPTOP METHODS----------------------------------------
	
	@Override
	public boolean moveMouseWithTouchpad() {
		boolean chance = Math.random() < CHANCE_FOR_TOUCHPAD_NOT_TO_WORK;
		
		if (chance) {
			System.out.println("Touchpad works");
		} else {
			System.out.println("Touchpad not responding");
		}
		
		return chance;
	}
	
	@Override
	public boolean typeSomeTextInNotepad() {
		boolean chance = Math.random() < CHANCE_FOR_KEYBOARD_NOT_TO_WORK;
		
		if (chance) {
			System.out.println("Keyboard works");
		} else {
			System.out.println("Keyboard buttons do not work");
		}
		
		return chance;
	}
	
	//----------------------------------------TESTABLE METHODS----------------------------------------
	
	@Override
	public boolean test() {
		return turnOn() && moveMouseWithTouchpad() && typeSomeTextInNotepad() && turnOff();
	}
	
	//----------------------------------------IELECTRONICS METHODS----------------------------------------
	
	@Override
	public boolean turnOn() {
		boolean chance = Math.random() < CHANCE_FOR_LAPTOP_NOT_TO_TURN_ON_OFF;
		
		if (chance) {
			System.out.println("Laptop turned on successfully");
		} else {
			System.out.println("Laptop could not turn on");
		}
		
		return chance;
	}

	@Override
	public boolean turnOff() {
		boolean chance = Math.random() < CHANCE_FOR_LAPTOP_NOT_TO_TURN_ON_OFF;
		
		if (chance) {
			System.out.println("Laptop turned off successfully");
		} else {
			System.out.println("Laptop froze");
		}
		
		return chance;
	}
	
	//----------------------------------------SETTERS----------------------------------------
	
	private void setProcessorSpeed(double processorSpeed) {
		this.processorSpeed = processorSpeed > MIN_PROCESSOR_SPEED && processorSpeed < MAX_PROCESSOR_SPEED ? processorSpeed : MIN_PROCESSOR_SPEED;
	}
	
	private void setMemory(int memory) {
		this.memory = memory > MIN_MEMORY ? memory : MIN_MEMORY;
	}
	
	//----------------------------------------GETTERS----------------------------------------
	
	public int getMemory() {
		return memory;
	}
	
	public double getProcessorSpeed() {
		return processorSpeed;
	}

	
	//----------------------------------------OTHER METHODS----------------------------------------

	@Override
	Laptop clone(int quantity) {
		return new Laptop(this.getName(), this.getPrice(), quantity, 
				this.getModel(), this.getOperatingSystem(), this.getProcessorSpeed(), this.getMemory());
	}
	
}
