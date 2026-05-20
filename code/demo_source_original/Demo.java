import java.io.*;
import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.net.*;


public class Demo extends Applet  implements ActionListener
{
	URL url = null;
	String currentInputFile;
	Frame menuframe;
	
	TopPanel tp;
	
	VariablesPanel vp;
	DrawArea da;
	DrawPanel dp;
	NetworkDynamics nd;
	
	boolean  graphRead = false;
	boolean  evolved = false;	
	boolean  drawSpeeds = true;
	
	public void init() {
	
		url = getCodeBase();
		
		tp = new TopPanel ( getParameter ("logo"));
		
		vp = new VariablesPanel();
		dp = new DrawPanel(this);
		da = new DrawArea( dp );
		 
		menuframe = new MenuFrame("Network Dynamix",  this )  ;
		menuframe.setSize(1000, 700);
		menuframe.add("West", vp);
		menuframe.add("Center", da);
		//menuframe.add("North", tp);
		/*
		FileDialog fd = new FileDialog(menuframe, "Network Dynamix");
		fd.setVisible(true);
		*/
		
		//da.show();
		menuframe.show();
		
		
				
	}
	/*
	public void mouseMoved( MouseEvent me) {
	}
	
	public void mouseDragged( MouseEvent me) {
	}
	*/
	public void actionPerformed( ActionEvent ae) {
		String arg = (String) ae.getActionCommand();
		Object obj = ae.getSource();
		
		if(arg.equals("Circular Network")) {
			
		} else if (arg.equals("Twin Cities")) {
			currentInputFile = "tc.txt"; 
			try {
				nd = new NetworkDynamics( vp.variables,  url, currentInputFile);
			} catch (IOException ie) {
			}
			da.setMapVariables();
			graphRead = true;
			evolved = false;
			da.currentYear = 0;
			da.repaint();
		} else if (arg.equals("10X10 nodes Grid Network")) {
			currentInputFile = "Grid10.txt"; 
			try {
				nd = new NetworkDynamics( vp.variables,  url, currentInputFile);
			} catch (IOException ie) {
			}
			graphRead = true;
			da.setMapVariables();
			evolved = false;
			da.currentYear = 0;
			da.repaint();
		}  else if (arg.equals("15X15 nodes Grid Network")) {
			currentInputFile = "Grid15.txt"; 
			try {
				nd = new NetworkDynamics( vp.variables,  url,  currentInputFile);
			} catch (IOException ie) {
			}
			graphRead = true;
			da.setMapVariables();
			evolved = false;
			da.currentYear = 0;
			da.repaint();
		} else if (arg.equals("20X20 nodes Grid Network")) {
			currentInputFile = "Grid20.txt"; 
			try {
				nd = new NetworkDynamics( vp.variables,  url,  currentInputFile);
			} catch (IOException ie) {
			}
			graphRead = true;
			da.setMapVariables();
			evolved = false;
			da.currentYear = 0;
			da.repaint();
		} else if (arg.equals("100X100 nodes Grid Network")) {
			currentInputFile = "Grid100.txt"; 
			try {
				nd = new NetworkDynamics( vp.variables,  url,  currentInputFile);
			} catch (IOException ie) {
			}
			graphRead = true;
			da.setMapVariables();
			evolved = false;
			da.currentYear = 0;
			da.repaint();
		} else if(arg.equals("Quit")) {
			menuframe.hide();
		} 
			
		
		if( obj == dp.whichAttribute ) {
			if( dp.whichAttribute.getLabel() == "Speed") {
				dp.whichAttribute.setLabel("Volume");
				drawSpeeds = false;
				da.repaint();
			} else {
				dp.whichAttribute.setLabel("Speed");
				drawSpeeds = true;
				da.repaint();
			}
		}
		
		if(arg.equals("<<")) {
			//System.out.println("************************");
			da.currentYear = 0;
			da.repaint();
		} else if( arg.equals("<") ) {
			if( da.currentYear > 0 ) {
				da.currentYear--;
				da.repaint();
			}  else {
				da.currentYear = da.n -1;
				da.repaint();	
			}
		} else if(arg.equals(">") ) {
			if(da.currentYear < da.n-1 ) {
				da.currentYear ++;
				da.repaint();
			} else {
				da.currentYear = 0;
				da.repaint();
			}
		} else if(arg.equals(">>") ) {
			da.currentYear = da.n-1;
			da.repaint();
		}
		
		dp.year.setText( "   "+ Integer.toString( da.currentYear ) + "   " ); 
		
		
		
		
		
		
	
	}
	
	
	
	public void paint( Graphics g ) {
		
	}
	
	
	class TopPanel extends Panel {
		
		Image img;
		
		public TopPanel (String imgName) {
			img = getImage(getDocumentBase(), imgName);		
		}
		
		public void paint(Graphics g) {
			
			setBackground(Color.blue);
			
			//g.drawImage(img, 0, 0, this);
			
		}
		
		
		
	}
	
	class DrawPanel extends Panel {
	
		Demo sd;
		
			
		Button whichAttribute = new Button ("Speed");
		Button first = new Button("<<");
		Button previous = new Button("<");
		Label year = new Label(  "  1   " , Label.CENTER );
		Button next = new Button(">");
		Button last = new Button(">>");
	
		public DrawPanel( Demo sd) {
				
			
			this.sd = sd;
			add(whichAttribute);
			add( first);
			add( previous );
			add(year);
			add( next );
			add( last);
		
			whichAttribute.addActionListener(this.sd);
			first.addActionListener(this.sd);
			previous.addActionListener(this.sd);
			next.addActionListener(this.sd);
			last.addActionListener(this.sd);
		
		}	
	}

	class DrawArea extends Panel {
		
		DrawPanel dp;
		
		int Scale;	// Scale of magnification or diminision	
		int Trans;	// translation 
		int dim;      // size of the DrawArea
		int radius;   //Radius of circle that represents a node
		Dimension d;  //Current Dimension of the DrawArea (dynamic variable)
		int Max;   // Maximum number of cells

		int n;
		int currentYear = 1;
		
		
		public DrawArea(DrawPanel dp) {
			this.dp = dp;
			
			setLayout(new BorderLayout() );
			setSize(1000, 700);
			add("South", dp);
		}
		
		
		void setMapVariables() {
			
			n = (int)vp.variables[22] +1;
			Max = nd.Max;
		
			d = getSize();
			//System.out.println(" Dimension of the DrawArea: width =  "+d.width + "  height = " + d.height );
			
			dim = (int)    (      (d.width<d.height) ? (0.90*d.width) : (0.90*d.height)        );
			
			System.out.println("dim = "+ dim);
			
			if(Max != 0){	
				Scale = (int)(dim/Max);
			} else {
				System.out.println("From DrawArea class Max variable is 0. Erorr!!!!!");
				Scale = 2;
			}
			if(Scale == 0)
				Scale = 1;
			
			Trans = (int) (0.05*dim);
			radius = (int) (Scale);
			
			if(radius == 0)
				radius = 1;
			System.out.println("Trans = "+Trans+";  radius = "+ radius);
			System.out.println("End of setScale()!!!!!");
		
		}
		
		
			//// will be drawn for the current year
		private void drawLinks_Speed(Graphics g) {
		
			float min, max;
		
			FloatStack  f[] = null;
			if(evolved) {
				if(  drawSpeeds)
					f = nd.Speed[currentYear];
				else {
					if( currentYear == n-1 )
						f = nd.Volume[n-2];
					else
						f = nd.Volume[currentYear];
				}
			} else
		
				f = nd.dg.Speed;
							
			float temp = 0;
			min = max =  f[0].access(0) ;
			for(int i=0; i<nd.dg.Vertices(); i++) {
				for(int j=0; j<nd.dg.NoofLinks(i+1); j++) {
					temp = f[i].access(j);
					if( max < temp)
						max = temp;
					if( min > temp)
						min = temp; 
				}
			}
		
			int count1 = 0, count2 = 0, count3 = 0, count4 = 0;
		
			int xcoord[] = new int[5];
			int ycoord[] = new int[5];
			float factor;
		
			for(int i =0; i<nd.dg.Vertices(); i++) {
				for(int j=0; j<nd.dg.NoofLinks(i+1); j++) {
					factor  = (float)(0.5*f[i].access(j) );
					int startx, starty, endx, endy;
					startx = (int)(Scale/2)+Trans + (int)(nd.dg.XCoordinate(i+1)*Scale);
					starty =  Trans - (int)(Scale/2)+ (int)(Scale*Max) - (int)(nd.dg.YCoordinate(i+1)*Scale);
					int k = nd.dg.EndNodeNumbers(i+1, j+1);
					endx = (int)(Scale/2)+Trans + (int)(nd.dg.XCoordinate(k)*Scale);
					endy =  Trans - (int)(Scale/2)+(int) (Scale* Max) - (int)(nd.dg.YCoordinate(k)*Scale);
								
					/*
				{
					 if( Speed[currentYear][i].access( j ) <=  (  avgSpeed[currentYear]-varianceSpeed[currentYear] )  ) {
					 	g.setColor(new Color(80, 255, 65) );  /////Green
						factor = (float) (0.5*Scale);
						count1++;
					}
					else if ( Speed[currentYear][i].access( j ) <=    avgSpeed[currentYear]   ) {
					 	g.setColor(new Color(250, 250, 65) );   ////Yellow
						factor = (float) (0.75*Scale);
						count2++;
					}
					else if ( Speed[currentYear][i].access( j ) <=  (  avgSpeed[currentYear]+varianceSpeed[currentYear] )  ) {
					 	g.setColor(new Color(250, 125, 0)  );    ////// Orange
						factor = (float) (Scale);
						count3++;
					}
					else {
					 	g.setColor(new Color (200, 20, 20) );   //// Red
						factor = (float) (1.25*Scale);
						count4++;
					}
				}
				//////    using average and variance
				*/
				
				
					float step = (max-min)/4;
				
					{
						 if( f[i].access( j ) <=  (  min+step)  ) {
						 	g.setColor(new Color(8, 140, 14) );  /////Green
							//g.setColor(new Color(150, 150, 150) );
							factor = (float) (0.5*Scale);
							count1++;
						}
						else if ( f[i].access( j ) <=  (min+2*step)  ) {
						 	g.setColor(new Color(60, 100, 250) );   ////Blue
							//g.setColor(new Color( 115, 115, 115) );
							factor = (float) (0.75*Scale);
							count2++;
						}
						else if ( f[i].access( j ) <=  ( min+3*step )  ) {
						 	g.setColor(new Color(250, 125, 0)  );    ////// Orange
							//g.setColor(new Color(70, 70, 70) );
							factor = (float) (Scale);
							count3++;
						}
						else {
						 	g.setColor(new Color (200, 20, 20) );   //// Red
							//g.setColor(new Color(25, 25, 25) );
							factor = (float) (1.25*Scale);
							count4++;
						}
					}	
				
				
					int xerror, yerror;	
					int x = endx - startx;
					int y = endy - starty;
			
				
					xerror = (int) (factor*y/Math.sqrt(x*x+y*y)); 
					yerror = (int)(-factor*x/Math.sqrt(x*x+y*y));
			
					int endxadd = endx+xerror, startxadd = startx+xerror;
					int endyadd = endy+yerror, startyadd = starty+yerror;
				
					xcoord[0] = startx-1;
					xcoord[1] = endx-1;
					xcoord[2] = endxadd;
					xcoord[3] = startxadd;
					xcoord[4] = startx-1;
					
					ycoord[0] = starty-1;
					ycoord[1] = endy-1;
					ycoord[2] = endyadd;
					ycoord[3] = startyadd;
					ycoord[4] = starty-1;
				
			
					g.fillPolygon(xcoord, ycoord, 5);
					g.setColor(Color.white);
					g.drawLine(startx, starty, endx, endy);
					
				}
			}
	
			//System.out.println("Current Year = "+currentYear +"*****Count = "+count1 + "  " + count2+ "  " + count3+ "  " + count4);
	
		}
		
				
		private void paintCells(Graphics g) {
			//int noOfLines;
			float sizeofcell;
			int sizeOfGrid;
			
			g.setColor(new Color(220, 220, 220) );
		
			sizeofcell = (Scale);
			sizeOfGrid = (int) (Scale * Max);
			for(int i=1; i<=Max+1; i++) {
				g.drawLine(Trans, sizeOfGrid+(int)(Trans-(i-1)*sizeofcell), Trans+sizeOfGrid, sizeOfGrid+(int) (Trans-(i-1)*sizeofcell) );   /// draw lines parallel to x-axis
				g.drawLine((int)(Trans+(i-1)*sizeofcell),  Trans,  (int)(Trans+(i-1)*sizeofcell),  sizeOfGrid+Trans);
			}
				
		}
		
		private void paintDG(Graphics g) {
			////  Draw Speed boxes
			g.setColor(Color.black);	
			drawLinks_Speed(g);
		
			///// Draw Nodes
			for(int i = 0; i< nd.dg.Vertices(); i++) {
				g.setColor(Color.black);
				int newx, newy;
				newx = (int)(Scale/2)+Trans + (int)(nd.dg.XCoordinate(i+1)*Scale);
				newy  = (int)(Scale*Max)-(int)(Scale/2) - (int)(nd.dg.YCoordinate(i+1)*Scale) + Trans;
				g.fillOval(newx-(int)(radius/2) , newy-(int)(radius/2), radius, radius);
			}
				
		}
		
		
		
		public void paint(Graphics g) {
			
			if  (graphRead) {
				paintCells(g);
				paintDG(g);
			}
					
		}
		
			
		
	}

	class VariablesPanel extends Panel implements ActionListener, ItemListener  {
		
		float variables[] = new float[23];
		
		//Demo demo;
		
		TextField speed1 = new TextField("", 5);
		TextField speed2 = new TextField("", 5);
		TextField field1 = new TextField(5);
		TextField field2 = new TextField(5);
		TextField field3 = new TextField(5);
		TextField field4 = new TextField(5);
		TextField field5 = new TextField(5);
		TextField field6 = new TextField(5);
		TextField field7 = new TextField(5);
		TextField field8 = new TextField(5);
		TextField field9 = new TextField(5);
		TextField field10 = new TextField(5);
		TextField field11 = new TextField(5);
		TextField field12 = new TextField(5);
		TextField field13 = new TextField(5);
		TextField field14 = new TextField(5);
		
		Checkbox  downtown = new Checkbox("Downtown?", false ); 		
		Checkbox   symmRouteAssg = new Checkbox("Symmetric Asst.?", false);
		Checkbox   avgSpeeds = new Checkbox("Average Speeds?", false );
		
		Button evolve = new Button("Evolve");
		
		Label tax = new Label("  ");
		Label  lcoeff = new Label("  ");
		Label fcoeff = new Label("1.0   ", Label.LEFT);
		Label scoeff = new Label("   ");
		
		
		//// Constructor
		public VariablesPanel() {
						
			defaultVars();
			
			setSize(100, 680);
			setLayout( new GridLayout( 29, 2 ) );
					
			Label  label1;
			Label  label2;
			Label  label3;
			Label  label4;
			Label  label5;
			Label  label6;
			Label  label7;
			Label  label8;
			Label  label9;
			Label  label10;
			Label  label11;
			Label  label12;
			Label  label13;
			Label  label14;
			Label  label15;
			Label  label16;
			Label  label17;
			Label  label18;
			Label  label19;
			Label  label20;
			Label  label21;
			Label  label22;
			Label  label23;
			Label  label24;
			Label  label25;
			Label  label26;
			Label  label27;
			Label  label28;
			
			
			
			
			Label dummy0 = new Label(" ");
			Label dummy1 = new Label(" ");
			Label dummy2 = new Label(" ");
			Label dummy3 = new Label(" ");
			Label dummy4 = new Label(" ");
			Label dummy5 = new Label(" ");
			
			add(label1 = new Label("Speed Distribution:")  );
			add(dummy0);
			add(label2 = new Label("      Minimum Speed") );
			add(speed1);
			add(label3 = new Label("      Maximum Speed") );
			add(speed2);
			
			add(label4 = new Label("Land use distribution:")  );
			add(dummy1);
			add( label5 = new Label("      Minimum land use") );
			add(field1);
			add( label6 = new Label("      Maximum alnd use") );
			add(field2);
			add(dummy2);
			add( downtown);
			
			add( label7 = new Label("Generalized cost of Travelling;")  );
			add(dummy3);
			add( label8 = new Label("      Value of time")  );
			add( field3 );
			add( label9 = new Label("      Tax rate")  );
			add(field4);
			add( label10 = new Label("      Coeff. of length") );
			add( field5 );
			add( label11 = new Label("     Coeff. of speed")  );
			add( field6);
			
			add( label12 = new Label("Route Assignment variable: ") );
			add(symmRouteAssg);
			add( label13 = new Label("      Coeff in friction factor:") );
			add(field7);
			
			add(label14 = new Label("Average speed on opposite links: ")  );
			add(avgSpeeds);
			
			add( label15 = new Label("Revenue variables: ")  );
			add(dummy4);
			add(label16 = new Label("      Tax rate")  );
			add(tax);
			add(label17 = new Label("      Coeff. of length ")  );
			add(lcoeff);
			add(label18 = new Label("      Coeff. of flow")  );
			add( fcoeff );
			add(label19 = new Label("      Coeff. of speed")  );
			add(scoeff );
			
			add(label20 = new Label("Investment variables:  ")  );
			add(dummy5);
			add(label21 = new Label("      Cost rate")  );
			add(field8);
			add(label22 = new Label("      Coeff. of length")  );
			add(field9);
			add(label23 = new Label("     Coeff. of flow")  );
			add(field10);
			add(label24 = new Label("      Coeff. of speed")  );
			add(field11);
			
			add( label25 = new Label("Speed Reduction factor:  ") );
			add( field12);
			add(label26 = new Label(" X - Default Investment  ")   );
			add(field13);
			add(label27 = new Label("Evolution time period")  );
			add(field14);
			add( label28 = new Label("Evolve") );
			add(evolve);
		
			speed1.addActionListener(this);
			speed2.addActionListener(this);
			field1.addActionListener(this);
			field2.addActionListener(this);
			field3.addActionListener(this);
			field4.addActionListener(this);
			field5.addActionListener(this);
			field6.addActionListener(this);
			field7.addActionListener(this);
			field8.addActionListener(this);
			field9.addActionListener(this);
			field10.addActionListener(this);
			field11.addActionListener(this);
			field12.addActionListener(this);
			field13.addActionListener(this);
			field14.addActionListener(this);
			
			downtown.addItemListener(this);
			symmRouteAssg.addItemListener(this);
			avgSpeeds.addItemListener(this);
			
			evolve.addActionListener(this);
			
			showValuesinTextFields();
			
		}
		
		void defaultVars() {
			
			variables[0] = (float) 1;
			variables[1] = (float) 1;
			variables[2] = (float) 0;  /// dummy
			variables[3] = (float) 10;
			variables[4] = (float) 10;
			variables[5] = (float) 0.0;   //// downtown
			variables[6] = (float) 1.0;
			variables[7] = (float) 1.0;
			variables[8] = (float) 1.0;
			variables[9] = (float) 0.0;
			variables[10] = (float) 0.01;
			variables[11] = (float) 1;
			variables[12] = (float) 1;
			variables[13] = (float) 1.0;
			variables[14] = (float) 1.0;
			variables[15] = (float) 0;
			variables[16] = (float) 365;
			variables[17] = (float) 1.0;
			variables[18] = (float) 0.75;
			variables[19] = (float) 0.75;
			variables[20] = (float) 1.0;
			variables[21] = (float) 0;
			variables[22] = (float) 15;
			
		}
		
		void  showValuesinTextFields() {
			String name = null;
			
			name =  Integer.toString((int)variables[0]);
			speed1.setText(name);
			name = Integer.toString((int)variables[1]);
			speed2.setText(name);
			name = Integer.toString((int)variables[3]);
			field1.setText(name);
			name = Integer.toString((int)variables[4]);
			field2.setText(name);
			name = Float.toString(variables[6]);
			field3.setText(name);
			name = Float.toString(variables[7]);
			field4.setText(name);
			name = Float.toString(variables[8]);
			field5.setText(name);
			name = Float.toString(variables[9]);
			field6.setText(name);
			name = Float.toString(variables[10]);
			field7.setText(name);
			name = Float.toString(variables[16]);
			field8.setText(name);
			name = Float.toString(variables[17]);
			field9.setText(name);
			name = Float.toString(variables[18]);
			field10.setText(name);
			name = Float.toString(variables[19]);
			field11.setText(name);
			name = Float.toString(variables[20]);
			field12.setText(name);
			name = Float.toString(variables[21]);
			field13.setText(name);
			name = Integer.toString((int)variables[22]);
			field14.setText(name);
			
			tax.setText( field4.getText());
			lcoeff.setText( field5.getText() );
			scoeff.setText(field6.getText() );
			
			downtown.setState( false);
			symmRouteAssg.setState(true);
			avgSpeeds.setState(true);
			
		}
		
		public void actionPerformed( ActionEvent ae) {
			Object obj = ae.getSource();
			if(obj == speed1)
				variables[0] = (float) Integer.parseInt(speed1.getText());
			else if (obj == speed2)
				variables[1] = (float) Integer.parseInt(speed2.getText() );
			else if ( obj == field1 )
				variables[3] = (float) Integer.parseInt(field1.getText() );
			else if( obj == field2)
				variables[4] = (float) Integer.parseInt(field2.getText() );
			else if( obj == field3)
				variables[6] = new Float( field3.getText( )  ).floatValue(); 
			else if( obj == field4) {
				variables[7] = variables[13] = new Float( field4.getText() ).floatValue() ;
				tax.setText( field4.getText() );
			} else if( obj == field5) {
				variables[8] = variables[14] = new Float( field5.getText() ). floatValue();
				lcoeff.setText( field5.getText() );
			} else if( obj == field6) {
				variables[9] = variables[15] = new Float( field6.getText() ).floatValue();
				scoeff.setText( field6.getText() );	
			} else if( obj == field7)
				variables[10] = new Float( field7.getText() ).floatValue();
			else if( obj == field8)
				variables[16] = new Float( field8.getText() ).floatValue();
			else if( obj == field9)
				variables[17] = new Float( field9.getText() ).floatValue();
			else if( obj == field10)
				variables[18] = new Float( field10.getText() ).floatValue();
			else if( obj == field11)
				variables[19] = new Float( field11.getText() ).floatValue();
			else if( obj == field12)
				variables[20] = new Float( field12.getText() ).floatValue();
			else if( obj == field13)
				variables[21] = new Float( field13.getText() ).floatValue();
			else if( obj == field14) {
				variables[22] = new Float( field14.getText() ).floatValue();
				da.n = (int) variables[22] + 1;
			} else if ( obj == evolve) {
				try {
					nd = new NetworkDynamics( variables, url, currentInputFile);
				}  catch(IOException ie) {
				}
				evolved = false;
				da.currentYear = 0;
				da.repaint();
				evolved = true;
				nd.NetworkDynamix( variables );
				da.dp.show();
				da.repaint();					
			}
			
		}
		
		public void itemStateChanged( ItemEvent ie) {
			Object obj = ie.getSource();
			if( obj == downtown) {
				if( downtown.getState() == true)
					variables[5] = (float)1.0;
				else
					variables[5] = (float) 0;
			} else if ( obj == symmRouteAssg ) {
				if( symmRouteAssg.getState() == true )
					variables[11] = (float) 1.0;
				else
					variables[11] = (float) 0;
			} else if ( obj == avgSpeeds ) {
				if( avgSpeeds.getState() ==  true )
					variables[12] = (float) 1.0;
				else
					variables[12] = (float) 0;
			}
		}
				
		
		
	}
	
	
	////// End of class VariablesPanel
	

}

///////  End of Demo Class


class MenuFrame extends Frame {
	Demo demo;
	
	public MenuFrame(String title,  Demo demo) {
		super(title);
		this.demo = demo;
		
		setLayout( new BorderLayout() );
		
		MenuBar mbar = new MenuBar();
		setMenuBar(mbar);
		
		Menu file = new Menu("File");
		
		Menu openFile = new Menu("New...");
		
		MenuItem  circular, grid1, grid2, grid3, grid4, grid5;
		//openFile.add(circular = new MenuItem("Circular Network"));
		openFile.add(grid1 = new MenuItem("Twin Cities"));
		openFile.add(grid2 = new MenuItem("10X10 nodes Grid Network"));
		openFile.add(grid3 = new MenuItem("15X15 nodes Grid Network")); 
		openFile.add(grid4 = new MenuItem("20X20 nodes Grid Network")); 
		openFile.add(grid5 = new MenuItem("100X100 nodes Grid Network")); 
				
		MenuItem item1 = new MenuItem("-");
		MenuItem quit = new MenuItem("Quit");
		
		file.add( openFile);
		file.add(item1);
		file.add(quit);
		mbar.add(file);
		
		Menu view = new Menu("View");
		
		MenuItem graph = new MenuItem("Graph");
		MenuItem speedDynamix = new MenuItem("Speed Dynamics");
		
		view.add(graph);
		view.add(speedDynamix);
		mbar.add(view);
		
		//circular.addActionListener(this.demo);
		grid1.addActionListener(this.demo);
		grid2.addActionListener(this.demo);
		grid3.addActionListener(this.demo);
		grid4.addActionListener(this.demo);
		
		openFile.addActionListener(this.demo);
		quit.addActionListener(this.demo);
		
		graph.addActionListener(this.demo);
		speedDynamix.addActionListener(this.demo);
		
	}
	
		
	
}
///// End of MenyFrame class
