package micro_paint;

import java.util.ArrayList;

public class Operation {
	
	public Operation() {
		operationList = new ArrayList<String>();
		operandList = new ArrayList<Integer>();
	}
	
	public ArrayList<String> getOperationList() {
		return operationList;
	}
	public void setOperationList(ArrayList<String> operationList) {
		this.operationList = operationList;
	}
	public ArrayList<Integer> getOperandList() {
		return operandList;
	}
	public void setOperandList(ArrayList<Integer> operandList) {
		this.operandList = operandList;
	}

	ArrayList<String> operationList;
	ArrayList<Integer> operandList;
}
