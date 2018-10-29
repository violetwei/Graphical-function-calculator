public class listNode {
	String payload;
	listNode next;  //link to next node in the list
	listNode previous;	
//constructor

    public listNode(String pld) {
        payload = pld;
        next = null;
        previous = null; //initialized
    }

}