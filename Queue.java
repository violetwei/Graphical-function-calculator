public class Queue {
	
	listNode rear;
    listNode front;
    int numberofnodes =0;

	
    //Enqueue method
	public void Enqueue(String newstring)
	{
		listNode mynode = new listNode(newstring);
		// First check if the queue is empty
		if(rear == null)
		{
			rear = mynode;
			front = mynode;
			
		}else
		{
			// If the queue is not empty
			mynode.next = rear;
			rear.previous = mynode;
			rear = mynode;
		}
		numberofnodes++;
	}
	
	
	//Dequeue method
	public String Dequeue()
	{
		// This checks whether have front node or not
		if(front == null)
		{
			System.out.println("Error: the queue is empty");
			return null;
		}
		numberofnodes--;	
		// Take the result string 
		String result_str = front.payload;
		
		if(front.previous == null) //the case one node on the queue
		{			
			front = null;
			rear = null;
			return result_str;
		}else
		{
			listNode pre_front = front.previous;
			front.previous = null;
			pre_front.next = null;
			front = pre_front;
			return result_str;
		}
			
	}
	
	
	//empty
	public boolean isEmpty() {
		if(front == null) 
		{
			return true;
		}
		else {
			return false;
		}
	}
	

}