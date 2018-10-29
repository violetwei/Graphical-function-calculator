/*
 * A Basic 4-Function Calculator--created by init method
 *
 * The goal of this project is to design a graphical user interface (GUI) 
 * that allows the user to key in an expression and have the result appear on a display.*/

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import acm.gui.TableLayout;
import acm.program.Program;

public class calculatorGUI extends Program implements ChangeListener, ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	//operators
			private static final char ADD = '+', SUBTRACT = '-';
		    private static final char MULTIPLY = '*', DIVIDE = '/';
			private static final char POWER ='^';
			

			public boolean isOperator(char c) { // Tell whether c is an operator.

			     return c == '+'  ||  c == '-'  ||  c == '*'  ||  c == '/'  ||  c == '^'
			           || c=='(' || c==')';
			   
			   }//end isOperator

			 

			   public boolean isSpace(char c) {  // Tell whether c is a space.

			     return (c == ' ');
			   
			   }//end isSpace


			   public boolean lowerPrecedence(char op1, char op2) {
			      // Tell whether op1 has lower precedence than op2, where op1 is an
			      // operator on the left and op2 is an operator on the right.
			      // op1 and op2 are assumed to be operator characters (+,-,*,/,^).
			      
			      switch (op1) {

			         case '+':
			         case '-':
			            return !(op2=='+' || op2=='-') ;

			         case '*':
			         case '/':
			            return op2=='^' || op2=='(';

			         case '^':
			            return op2=='(';

			         case '(': return true;

			         default:  // (shouldn't happen)
			            return false;
			      }
			 
			   } // end lowerPrecedence
		   	  
			   
			   	   
			  // Method to convert infix to postfix:
		      /*A very well known algorithm for converting an infix notation to a postfix notation 
			   is Shunting Yard Algorithm by Edgar Dijkstra.*/
			 
			  //StringTokenizer class

			   public String convertToPostfix(String infix) {       
			     char c;       
			     
			  //the StringTokenizer class can parse the input string and separating it into operators and operands
			     StringTokenizer parser = new StringTokenizer(infix,"+-*/^() ",true);
			     
			     //new stack
			     Stack operatorStack = new Stack();
			     //new queue input and output
			     Queue input = new Queue();
			     Queue output = new Queue();
			    
			     
			     //fill in the input queue
			        while (parser.hasMoreTokens())
			        {
			        	  String token = parser.nextToken();
			        	  input.Enqueue(token);
			        }
			        
			        while (!input.isEmpty()) 
			        {
			        	  String token = input.Dequeue();
			           c = token.charAt(0);         
			           if ( (token.length() == 1) && isOperator(c) ) 
			           {    
			              while (!operatorStack.isEmpty() &&
			                  !lowerPrecedence(((String)operatorStack.peek()).charAt(0), c)) 
			              {
			            	    output.Enqueue(" ");
			            	    output.Enqueue(((String)operatorStack.pop()));
			              }
			              if (c==')') 
			              {
			                    String operator = (String)operatorStack.pop();
			                    while (operator.charAt(0)!='(') 
			                    {
			                       output.Enqueue(" ");
			                       output.Enqueue(operator);
			                       operator = (String)operatorStack.pop();
			                    }
			              } else {
			                 operatorStack.push(token);
			              }
			           }
			           else if ( (token.length() == 1) && isSpace(c) ) {
			        	   
			               }
			           else 
			           { 
			        	     output.Enqueue(" ");
			        	     output.Enqueue(token); 
			           }
			         }
			        
			        while (!operatorStack.isEmpty()) 
			        {
			        	  output.Enqueue(" ");
			        	  output.Enqueue((String)operatorStack.pop());
			        }
			        String result =  " ";
			        while(!output.isEmpty())
			        {
			        	result += output.Dequeue();
			        }
			        return result;
			        
			   }

		//evaluation expression part and return a numeric result
		   public double evaluate(String expression) {

			       Stack stack = new Stack();
			       
			        double op1, op2, result = 0;
			        String token;
			        
			        StringTokenizer tokenizer = new StringTokenizer(expression," ");

			        while (tokenizer.hasMoreTokens()) 
			        {
			            token = tokenizer.nextToken();
			            char c = token.charAt(0); 
			            
			            if (isOperator(c) )// && (token.length() == 1)) 
			            {
			                op2 = Double.parseDouble(stack.pop());
			                op1 = Double.parseDouble(stack.pop());
			                result = evalSingleOp(token.charAt(0), op1, op2);
			                stack.push(Double.toString(result));
			            }
			            else {
			                stack.push(token);
			            }
			        }

			        return result;
			    }
		   
		   

	//By default, Java uses double to represent its floating-point numerals (so a literal 3.14 is typed double).
		   //result displayed to the full precision
		   public double evalSingleOp(char operation, double op1, double op2) {
			        
			   double result = 0;  //initialize

			        switch (operation) 
			        {
			            case ADD :
			                result = op1 + op2;
			                break;
			            case SUBTRACT :
			                result = op1 - op2;
			                break;
			            case MULTIPLY :
			                result = op1 * op2;
			                break;
			            case DIVIDE :
			                result = op1 / op2;
			                break;
			            case POWER :
			                result = (int) Math.pow(op1,op2);
			                break;
			        }

			        return result;
			    }
		 
		   
		//Event-driven -> action events  	   
		JTextField input = new JTextField("");
		JTextField output = new JTextField("");
		JTextField prec_box = new JTextField();

		String write = "";	
		String precision = "";
		String printout = "";
		
		
		public void init() {
			setSize(500, 500);
			
			/*available TableLayout Constraints:
			 * gridwidth=columns  or  gridheight=rows
			 * indicates that this table cell should span the indicated number of columns or rows*/
			
			setLayout(new TableLayout(8,4));
			add(input, "gridwidth=4 height=25");
			
			output.setEditable(false);
			output.setBackground(Color.WHITE);
			add(output, "gridwidth=4 height=25");
			
			
			String BUTTON_SIZE ="70";
			String constraint = "width=" + BUTTON_SIZE + " height=" + BUTTON_SIZE;
			
			String[] ButtonLabels = {"C", "+/-", "%", "/", "7", "8", "9", "*", "4", "5", "6", "-", "1", "2", "3", "+", "0", ".", "^", "="};
			for(int i= 0; i< ButtonLabels.length; i++) 
			{	
				JButton cur_button = new JButton(ButtonLabels[i]);
				add(cur_button,constraint);
			}
			
			
			/*it's useful to include JLabel objects, which appear as text strings in the user interface
			 * but do not respond to any events*/		
			JLabel prec_label = new JLabel("Precision");
			add(prec_label, "gridwidth=1");
			
			JSlider prec_slider = new JSlider(0,10,6);
			add(prec_slider,"gridwidth=2");
			
			
			//if you concatenate string + numeric number -> string
			
			/*can accept keyboard input in a user interface by using the JTextField class
			 * which provides the user with an area in which it is possible to enter a single line of text.*/
			
			prec_slider.addChangeListener(this);
		    prec_box = new JTextField(prec_slider.getValue()+"");
			add(prec_box,"gridwidth=1");
			
			
		/*Before you can detect action events, you need to enable an action listener for the buttons on the screen.
		 * The easiest strategy if to call addActionListeners at the end of the init method.*/
			addActionListeners();
		}
		
			
		//GUI consists of buttons and other on screen controls
		//can create more GUIs by using Java's layout managers
		
			
		@Override
		public void stateChanged(ChangeEvent e) {
			int value = ((JSlider)e.getSource()).getValue();
			prec_box.setText(String.valueOf(value));
			}
			
		
		/*the response to the button appears in actionPerformed*/
		public void actionPerformed(ActionEvent e) {
			/*from addActionListeners.*/
			/*call getActionCommand on the event to get the action command string,
			 * which initially set to the button label.*/
			String cmd = e.getActionCommand();
			boolean compute = false;
			
			if( (cmd.equals("1")) || (cmd.equals("2")) ||(cmd.equals("3"))||(cmd.equals("4"))
					||(cmd.equals("5")) ||(cmd.equals("6"))||(cmd.equals("7"))||(cmd.equals("8")
							)||(cmd.equals("9"))||(cmd.equals("0")) ||(cmd.equals("+"))
					||(cmd.equals("-"))||(cmd.equals("*"))||(cmd.equals("/"))||(cmd.equals("."))
					||(cmd.equals("^"))  )
			{
				write += cmd;
				printout += cmd;
				input.setText(printout);
			}
			
			/*EQUALS key is not processed as a token, but is used to initiate conversion of expression 
			 *and its evaluation with the result being displayed on the second line of the display*/
			
			else if ( (cmd.equals("=")) )
			{
				compute = true;
				printout += cmd;
				input.setText(printout);
			}
			else if ( (cmd.equals("C")) )
			{
				write = "";
				printout = "";
				input.setText(null);
				output.setText(null);
			}
			
			//negative numbers are a special case; the +/-key is not treated as a delimiter but as a digit.
			//GUI enqueues each token as it is typed in.
			else if ((cmd.equals("+/-")) )
			{
				write += "0-";
				printout += "-";
				input.setText(printout);
			}
			if(compute == true)
			{
				String post = convertToPostfix(write);
				double result = evaluate(post);
				String out = String.valueOf(result);
				
				precision = prec_box.getText();
				String buffer = "";
				int num = Integer.parseInt(precision);
				int index = out.indexOf('.');
				for(int i = 0; i < num + index + 1; i++)
				{
					try {
						buffer += out.charAt(i);
					}
					catch(Exception ex) 
					{
						buffer += '0';
					}
					
				}
				
				output.setText(buffer);
				compute = false;
			}
			
		}
	
	

}
