
public class Phone extends DeviceWithOperatingSystem implements IPhone, Testable, IElectronics {
	
	private static final double CHANCE_NOT_TO_TURN_ON_OFF = 0.95;
	private static final double CHANCE_FOR_SCREEN_NOT_TO_LOCK_UNLOCK = 0.96;
	private static final double CHANCE_FOR_SCREEN_NOT_TO_WORK = 0.998;

	private boolean isTouchScreen;

	public Phone(String name, double price, int quantity, String model, boolean isTouchScreen, String operatingSystem) {
		super(name, price, quantity, model, operatingSystem);
		this.isTouchScreen = isTouchScreen;
	}

	// ----------------------------------------IELECTRONICS METHODS----------------------------------------

	@Override
	public boolean turnOn() {
		boolean chance = Math.random() < CHANCE_NOT_TO_TURN_ON_OFF;

		if (chance) {
			System.out.println("The phone turned on successfully");
		} else {
			System.out.println("The phone did not turn on");
		}

		return chance;
	}

	@Override
	public boolean turnOff() {
		boolean chance = Math.random() < CHANCE_NOT_TO_TURN_ON_OFF;

		if (chance) {
			System.out.println("The phone turned off successfully");
		} else {
			System.out.println("The phone froze");
		}

		return chance;
	}

	// ----------------------------------------TESTABLE METHODS----------------------------------------

	@Override
	public boolean test() {
		return turnOn() && unlockScreen() && openAnApp() && lockScreen() && turnOff();
	}

	// ----------------------------------------IPHONE METHODS----------------------------------------

	@Override
	public boolean openAnApp() {
		boolean chance = Math.random() < CHANCE_FOR_SCREEN_NOT_TO_WORK;

		if (chance) {
			System.out.println("You opened an app successfully");
		} else {
			System.out.println("Screen does not work");
		}

		return chance;
	}

	@Override
	public boolean unlockScreen() {
		boolean chance = Math.random() < CHANCE_FOR_SCREEN_NOT_TO_LOCK_UNLOCK;

		if (chance) {
			System.out.println("Unlocked screen successfully");
		} else {
			System.out.println("Unlock button does not work");
		}

		return chance;
	}

	@Override
	public boolean lockScreen() {
		boolean chance = Math.random() < CHANCE_FOR_SCREEN_NOT_TO_LOCK_UNLOCK;

		if (chance) {
			System.out.println("Locked screen successfully");
		} else {
			System.out.println("Lock button does not work");
		}

		return chance;
	}
	
	// ----------------------------------------GETTERS----------------------------------------

	public boolean isTouchScreen() {
		return isTouchScreen;
	}

	@Override
	Phone clone(int quantity) {
		return new Phone(this.getName(), this.getPrice(), quantity, 
				this.getModel(), this.isTouchScreen(), this.getOperatingSystem());
	}
}
