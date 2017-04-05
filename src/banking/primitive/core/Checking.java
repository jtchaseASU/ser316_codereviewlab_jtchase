package banking.primitive.core;

public class Checking extends Account {

	private static final long serialVersionUID = 11L;
	private int numWithdraws = 0;
	
	private Checking(String name) {
		super(name);
	}

    public static Checking createChecking(String name) {
        return new Checking(name);
    }

	public Checking(String name, float balance) {
		super(name, balance);
	}

	/**
	 * A deposit may be made unless the Checking account is closed
	 * @param float is the deposit amount
	 */
	public boolean deposit(float amount) {
		if (getState() != State.CLOSED && amount > _ZERO_ACCOUNT_BALANCE) {
			balance = balance + amount;
			if (balance >= _ZERO_ACCOUNT_BALANCE) {
				setState(State.OPEN);
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Withdrawal. After 10 withdrawals a fee of $2 is charged per transaction You may 
	 * continue to withdraw an overdrawn account until the balance is below -$100
	 */
	public boolean withdraw(float amount) {
		if (amount > 0.0f) {		
			// KG: incorrect, last balance check should be >=
			if (getState() == State.OPEN || (getState() == State.OVERDRAWN && balance > _MAX_NEGATIVE_BALANCE)) {
				balance = balance - amount;
				numWithdraws++;
				if (numWithdraws > _MAX_FREE_WITHDRAWLS)
					balance = balance - _WITHDRAWL_FEE;
				if (balance < _ZERO_ACCOUNT_BALANCE) {
					setState(State.OVERDRAWN);
				}
				return true;
			}
		}
		return false;
	}

	public String getType() { return "Checking"; }
	
	public String toString() {
		return "Checking: " + getName() + ": " + getBalance();
	}
	
	private final float _WITHDRAWL_FEE=2.0f;
	private final int _MAX_FREE_WITHDRAWLS = 10;
	private final float _ZERO_ACCOUNT_BALANCE=0.0f;
	private final float _MAX_NEGATIVE_BALANCE=-100.0f;
}
