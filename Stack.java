public class Stack {

	public listNode top;
	
	// Push method
		public void push(String newstring)
		{
			listNode mynode = new listNode(newstring);
			
			if(top != null)
			{		
				top.next = mynode;
				mynode.previous = top;		
			}
			top = mynode;
			
		}
	
	// Pop method
	public String pop()
	{
		if(top == null)
		{
			System.out.println("Error: empty stack");
			return null;
		}
		
		String result_str = top.payload;		
		// we have one node in the stack
		if(top.previous == null)
		{
			top = null;  
		}else
		{
			// we have more than one node in the stack
			listNode pre_top = top.previous;
			pre_top.next = null;
			top = pre_top;
		}			
		return result_str;
	}	
	        
	
	//isEmpty()
	public boolean isEmpty() 
	{
	   return (top == null);
	}

	
	//peek method
	public String peek() 
	{
		return top.payload;
	}	
	
	
}
