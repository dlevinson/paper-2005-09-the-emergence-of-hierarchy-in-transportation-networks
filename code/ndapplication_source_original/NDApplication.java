/*    classes in this file are :	NDApplication
*						InfoDialog
*						VariablesDialog
*						miscellaneousVariables
*						textFile
*						myProjectHandler	(with inner classes)
*							targetFiles
*							DrawPanel
*							DrawArea
*						MyMenuHandler
*						MenuFrame
*
*
*		NDApplication launches instances of  <class>MenuFrame</class> 
*					and <class>myProjectHandler</class> 
*
*		infoDialog	used to give alert messages to the user
*		VariablesDialog	used to show the variables windows
*		miscellaneousVariables	used to show the miscellaneous variables window
*		textFile		not complete - can be modified to create and edit new network and landuse files
*		myProjectHandler	handles most of the meny items and project events
*		MyMenuHandler	handles menu items by invoking the corresponding functions is myProjectHandler clas
*		MenuFrame		Defined the menu structure 
*
*		@version 		1.00  January 18th 2003
*		@author		Bhanu Yerra	- yerr0005@tc.umn.edu
*		
*
*
*/


import java.io.*;
import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.net.*;
import java.lang.StringBuffer;



/* This class launches the instances of MenuFrame and myProjectHandler
*
*	to convert the application into an applet inherit this class as an applet
*
*/
public class NDApplication {
	
	
	public static void main(String args[]) {
		
		myProjectHandler MPH = new myProjectHandler();
		
		MenuFrame mf = new MenuFrame("Untitled", MPH);
		mf.setVisible(true);

	}
}

///////  End of NDApplication </class>


/*	This class is used to display error messages/ alerts to the user
*	can be modified to include pictures (like stop sign) to make it more good looking
*/
class InfoDialog extends Dialog implements ActionListener {
	
	/*
	*	adds a button to the Dialog that says <String>"Ok"</String> pressing which will dispose the Dialog box
	*
	*	@param	parent	Frame to which the Dialog  is assigned
	*	@param	msg		string that is displayed as the messages or warning
	*
	*/
	
	
	public InfoDialog(Frame parent,  String msg) {
		
		super(parent, "Attention", true);
		setSize(300, 60);
		
		Button ok = new Button("OK");
		setLayout( new BorderLayout() );
		Panel p = new Panel();
		
		ok.addActionListener(this);
		add( new Label(msg, Label.CENTER), "North");
		p.add( ok);
		add(p, "Center");
		
	}
	
	/*
	*	any action performed on this Dialog box will dispose it
	*	users usually press the Button "Ok" 
	*/
	
	public void actionPerformed( ActionEvent ae) {
		dispose();
	}
	
}

///////   End of InfoDialog class </class>


/*	Instances of this class are used to display the windows to edit the coefficients in the model
*
*/

class VariablesDialog extends Dialog implements ActionListener {
	
	/*
	*	@param	f[]	an array of float values that represent the coefficients	- 	these values are read once the user
								is finished with this editing of the variables
	*	@param	tFields[]	an array of TextFields that displays f[] and allows the user to edit the values
	*/
	
	float f[] ;
	
	TextField tField[];
	
	/*
	*	There are two constructors in this class to handle the needs of all variables dialogs
	*
	*	adds two buttons to the window "Ok" and "Cancel" 
	*
	*	@param	parent	the Frame to which the Dialog is attached
	*	@param	args[]	the array of strings that will be displayed to the left side 
							of the TextFields.  These are the strings that explains the coefficients
	*	@param	defaultValues[]		array of float values that will be displayed in the window
	*
	*/
	
	private void subConstructor(Frame parent, String args[], float defaultValues[]) {
		
		int rows = defaultValues.length; 
		f = defaultValues;
		
		setLayout( new BorderLayout() );
		setSize(300, 200);
				
		Panel p = new Panel();
		p.setLayout( new GridLayout(rows+1, 2) );
		
		tField = new TextField[rows];
		
		for(int i=0; i<defaultValues.length; i++) {
			p.add(new Label(args[1+i]) );
			tField[i] = new TextField(5);
			tField[i].setText(Float.toString(defaultValues[i]));	
			p.add(tField[i]);
		}
		
		Button b;
		b = new Button("Cancel") ;
		b.addActionListener(this);
		p.add(b );
		
		b = new Button("Ok") ;
		b.addActionListener(this);
		p.add(b);
		
		add(p, "Center");
				
	}
	
	/*
	*	default constructor that uses the previous function
	*/
	
	public VariablesDialog(Frame parent, String args[], float defaultValues[]) {
		super(parent, args[0], true);
		subConstructor(parent, args, defaultValues);
	}
	
	/*
	*	consturctor that helps in making certain fields un-editable
	*	used in cost function variables window
	*/
		
	public VariablesDialog(Frame parent, String args[], float defaultValues[], boolean disable[], String msg) {
		super(parent, args[0], true);
		subConstructor(parent, args, defaultValues);
		for(int i=0; i<disable.length; i++)
			if( disable[i] )
				tField[i].setEditable(false);
		Panel p1 = new Panel();
		p1.setLayout( new BorderLayout() );
		TextArea ta = new TextArea(msg, 3, 40);
		ta.setEditable(false);
		p1.add(ta, "Center");
		add(p1,  "North");
				
	}
	
	/*
	*	if "Ok" button is pressed  then read the values from the TextFields
	*	else just dispose the window
	*/
	
	public void actionPerformed( ActionEvent ae) {
		
		String arg = (String)ae.getActionCommand();
		
		if( arg == "Cancel") {
			dispose();
		} else if(arg == "Ok") {
			for(int i=0; i<f.length; i++)
				f[i] = new Float(tField[i].getText()).floatValue();
			
			dispose();
		}
			
	}
	
}

///////   End of VariablesDialog class </class>

/*
*	miscellaneousVariables class is created as the previous class (VariablesDialog) cannot handle
*	the necessary functions required for the miscellaneous variables window
*
*/
class miscellaneousVariables extends Dialog implements ActionListener {
	
	/*
	*	@param	f[]	array of float values 
	*	@param	b[]	array of boolean values	holds the status of "summetry?" and "average speeds"
	*	@param	tf[] and cb[] array of TextFields and Checkboxes used to display the variables
	*/
		
	TextField tf[];
	float f[];
	
	Checkbox  cb[];
	boolean b[];
		
	public miscellaneousVariables(Frame mf, String args[], float defaultFields[], boolean defaultCheckboxes[]) {
		
		super(mf, args[0], true);
		setLayout(new BorderLayout());
				
		int fields = defaultFields.length;
		int boxes = defaultCheckboxes.length;
		
		f = defaultFields;
		b = defaultCheckboxes;
		
		tf = new TextField[fields];
		cb = new Checkbox[boxes];
		
		Panel pBoxes = new Panel();
		pBoxes.setSize(300, 80);
		pBoxes.setLayout(new GridLayout(boxes, 1) );
		
		for(int i=0; i< boxes; i++) {
			cb[i] = new Checkbox( args[1+i]);
			cb[i].setState(  defaultCheckboxes[i] );
			pBoxes.add(cb[i]);
		}
		
		add(pBoxes, "North");
		
		Panel p = new Panel();
		p.setLayout(new GridLayout(fields+1, 2)) ;
				
		for(int i =0; i<fields-1; i++) {
			p.add(new Label(args[i+1+boxes]) );
			tf[i] = new TextField(5);
			tf[i].setText( Float.toString(defaultFields[i])  );
			p.add(tf[i]);
		}
		
		p.add(new Label(args[fields+boxes]) );
		tf[fields-1] = new TextField(5);
		tf[fields-1].setText( Integer.toString((int)defaultFields[fields-1])  );
		p.add(tf[fields-1]);
		
		
		
		Button b;
		
		b = new Button("Cancel");
		b.addActionListener(this);
		p.add(b);
		
		b = new Button("Ok");
		b.addActionListener(this);
		p.add(b);
		
		add(p, "Center");
		
		
		
	}
	
	/*
	*	if "Ok" button is pressed  then read the values from the TextFields and dispose the winmdow
	*	else just dispose the window
	*/
	
	public void actionPerformed( ActionEvent ae) {
		
		String arg = (String) ae.getActionCommand();
		
		if( arg == "Cancel") {
			dispose();
		} else if(arg == "Ok") {
			for(int i=0; i<f.length; i++) 
				f[i] = new Float(tf[i].getText()).floatValue();
			for(int i=0; i<b.length; i++)
				b[i] = cb[i].getState();
						
			dispose();
		
		}
			
	}
	
	
	
}
///////  End of miscellaneousVariables class  </class>






///////    End of tagetFiles class </class>

class textFile {
	Frame f;
	
	public textFile() {
		
		f = new Frame("untitled");
		f.setLayout(new BorderLayout() );
		f.setSize(400, 400);
		f.add( new TextArea("", 29, 100, 0), "North" );
		f.setVisible(true);
		
	}
	
	
}

///////  End of textFile class  </class>

/*
*	practically all functions of the application are performed by this class
*	responds to the actions of the user
*	
*/

class myProjectHandler  implements ActionListener {
	
	/*
	*	@param	network	is the directed graph that holds the info of the nodes and thier connectivity
	*						and attributes like link lenght and link initial speed	
	*	
	*	@param	landUse	is the class that holds the info on the trips attracted and produced from a
	*						land use cell.  This data is read from a file.  Since the land use is modeled
	*						as grid of land use cell, huge amount of info is required in the land use files
	*	@param	vd[]		is the array of dialogs that are used to display the info of coefficients
	*	@param	mV		is the dialog that is used to display the info of some, as the name indicates, miscellanoeus variables
	*
	*
	*	@param	da		on which the directed graph is displayed
	*	@param	dp		panel that provides the infrastructure (buttons :))  for browsing through the results
	*	@param	f[]		hold the info of the current link attributed that needs to be displayed	@see	DrawArea.drawLinks_Speed(Graphics g)
	*	@param	initialSpeeds[]	holds the info of the initial speeds of the links
	*	@param	Speed[][]	holds the info of the speeds at the end of every iteration
	*	@param	Flow[][]	holds the info of the volumes at the end of every iteration 
	*	@param	avgSpeed[]	holds the info of average link speed at the end of every iteration
	*	@param	avgFlow[]		holds the info of average volume at the end of every iteration
	*	@param	files[]		holds the info of directory name and other file males	please refer below to view the description of each array element
	*	@param	runnable		if true then the network and land use are compatible else incompatible 	
	*	@param	resultsAvailable	if true then the output files are read
	*	@param	drawSpeeds		used to decide if to draw speeds or volumes
	*	@param	graphRead		used to decide if its time to draw the initial network  "Set this to false in the code where a project can be closed"
	*	@param	defaultLinkCost[]	used to store the coefficients of the link cost function	
	*	@param	defaultRevenue[]	used to store the coefficients of the revenue function - related to the defaultLinkCost variables 
	*	@param	defaultCost[]		used to store the coefficients of the cost function
	*	@param	defaultMiscellaneous	used to store the coefficinets of float values in the miscellaneous window
	*	@param	defaultMiscellaneousBoxes[] used to store the status of "summetric assignment" and "average the speeds on opposite links"
	*	
	*
	*/
	
	
	DirectedGraph network = null;
	Automata landUse = null;
	VariablesDialog vd[] = new VariablesDialog[3];
	miscellaneousVariables mV;
	
	DrawArea da;
	DrawPanel dp;
	FloatStack  f[] = null;
	
	FloatStack initialSpeeds[] = null;
	///////////////////////////////////////////////////////////////    Output variables 	/////////////////////////////////////////////////////////////// 
	FloatStack  Speed[][];
	FloatStack  Flow[][];
	
	float avgSpeed[];
		
	float avgFlow[];
	///////////////////////////////////////////////////////////////    Output variables 	/////////////////////////////////////////////////////////////// 

	///////////////////////////////////////////////////////////////    Files 	/////////////////////////////////////////////////////////////// 
	String files[] = new String[6];
	
	//	directory		files[0]
	//	projectFile	files[1]
	//	networkFile	files[2]
	//	landuseFile	files[3]
	//	speedsFile	files[4]
	//	flowsFile		files[5]
	
	///////////////////////////////////////////////////////////////    Files 	/////////////////////////////////////////////////////////////// 
	
	boolean runnable = false;
	boolean resultsAvailable = false;
	boolean drawSpeeds = true;
	boolean graphRead = false;
	
	int Max;
	int currentYear = 0;
		
	/// Strings
	String lables[][] = { { "Change the Coefficients of Link Cost Function", "Value of Time", "Toll Rate", "Coefficient of Length", "Coefficient of Speed"} ,
			{"Change the Coefficients of Revenue Function",  "Tax Rate", "Coefficient of Length", "Coefficient of Flow", "Coefficient of Speed",
				"Scale to Account Annual Flow"},
			{"Coefficients of Cost Function", "Cost Rate", "Coefficient of Length", "Coefficient of Flow", "Coefficeint of Speed"},
			{"Miscellaneous Variables", "Average Speeds in Opposite Links?", "Symmetric Assignment?", "Speed Dynamics Coefficient",
				 "Coefficient in Trip Distribution", "Number of Iterations"}
			};
				     
	
	///Default values
	
	float defaultLinkCost[] = {1, 1, 1, 0};
	float defaultRevenue[] = {defaultLinkCost[1],  defaultLinkCost[2],  1, defaultLinkCost[3], 365};
	float defaultCost[] = {365, 1, (float)0.75, (float)0.75};
	float defaultMiscellaneous[] = {1, (float)0.01, 30};
	boolean defaultMiscellaneousBoxes[] = {true, true};
	
	/*
	*	default construnctor - initiates the drawarea and drawpanel
	*/
	
	public myProjectHandler() {
			
		da = new DrawArea();
		dp = new DrawPanel(this);
				
	}
	
	/*
	*	used to reset the coefficinets ot the base case
	*/
	
	public void resetDefaultValues() {
		defaultLinkCost[0] = defaultLinkCost[1] = defaultLinkCost[2] = 1;
		defaultLinkCost[3] = 0;
		
		defaultRevenue[0] = defaultLinkCost[1];
		defaultRevenue[1] =  defaultLinkCost[2];
		defaultRevenue[2] = 1; 
		defaultRevenue[3] = defaultLinkCost[3];
		defaultRevenue[4] = 365;
		
		defaultCost[0] = 365;
		defaultCost[1] = (float)1.0;
		defaultCost[2] = defaultCost[3] = (float)0.75;
		
		defaultMiscellaneous[0] = 1;
		defaultMiscellaneous[1] = (float)0.01;
		defaultMiscellaneous[2] = 30;
		
		defaultMiscellaneousBoxes[0] = defaultMiscellaneousBoxes[1] = true;
	
	}
	
	/*
	*	opens the project and reads the data.
	*	reads the directory first and then reads the file names of network file and landuse file 
	*	
	*	refere documentation for the formats of the project file, network file and land use file
	*
	*	reads the output files only if  results are available ie. if resultsAvailable is true
	*/
	
	public void openProject(MenuFrame mf) throws IOException {
		
		FileDialog fd = new FileDialog(mf, "Open Network Dynamics Project", FileDialog.LOAD);
		fd.setVisible(true);
		files[0] = fd.getDirectory();
		files[1] = fd.getFile();
		
		files[4] = "";
		files[5] = "";
			
		
		ReadAFile r = new ReadAFile(files[0], files[1]);
		String dump = new String();
						
		dump = r.readLine();	///// reads the first line == <String>NetworkDynmaics</String>
		dump = new String();
		dump = r.readLine();	///// reads the second line == <String>Basics:</String>
		dump = new String();
		dump = r.readFileLn();	///// reads the directory path
		dump = new String();
		dump = r.readFileLn();	///// reads the project file name
		dump = new String();
		
		files[2] = r.readFileLn();	///// reads the network file name
		files[3] = r.readFileLn();	///// reads the land use file name
		
		runnable = (r.readFileLn().charAt(0) == 'y'  )? true: false;
		resultsAvailable = (   r.readFileLn().charAt(0) == 'y' )? true: false ;
		
				
		if( resultsAvailable) {
			System.out.println("Before resultsAvailable code block");
			files[4] = r.readFileLn();
			files[5] = r.readFileLn();
			dump = r.readFileLn();
			dump = new String();
			defaultLinkCost[0] =  r.readfloat();
			defaultLinkCost[1] =  r.readfloat();
			defaultLinkCost[2] =  r.readfloat();
			defaultLinkCost[3] =  r.readfloat();
		
			float temp = r.readfloat();
		
			defaultRevenue[0] = defaultLinkCost[1];
		
			temp = r.readfloat();
			defaultRevenue[1] = defaultLinkCost[2];
		
			defaultRevenue[2] =  r.readfloat();
		
			temp = r.readfloat();
			defaultRevenue[3] = defaultLinkCost[3];
		
			defaultRevenue[4] = r.readfloat();
		
			defaultCost[0] = r.readfloat();
			defaultCost[1] = r.readfloat();
			defaultCost[2] = r.readfloat();
			defaultCost[3] = r.readfloat();
		
			defaultMiscellaneous[0] = r.readfloat();
			defaultMiscellaneous[1] = r.readfloat();
			defaultMiscellaneous[2] = r.readfloat();
		
			defaultMiscellaneousBoxes[0] = (r.readFileLn().charAt(0) == 'y' )?  true: false;
			defaultMiscellaneousBoxes[1] = (r.readFileLn().charAt(0) == 'y' )?  true: false;
			
						
		} 				
			
		r.close();
		
		network = new DirectedGraph(files[0], files[2]);
		System.out.println("Opened the network file!!!!");
		landUse = new Automata(files[0], files[3]);
		System.out.println("Opened the landuse file!!!!");
		
		drawGraph();
				
		if( runnable && resultsAvailable) {
			try {
				readOutputFiles();
			} catch( IOException e) {
			}
		}
					
	}
	
	/*
	*	calculates the minimum width of the underlying land use
	*	then calculates the scale and other draw variables used for mapping the network
	*	also initializes the initialSpeeds array
	*	is used in openProject() function (previous function) 
	*/
	
	private void drawGraph() {
		
		{
			int MaxX, MaxY;
				int MinX, MinY;
								
				MaxX = network.XCoordinate(1);
				MinX = network.XCoordinate(1);
				for(int i=2; i<=network.Vertices(); i++) {
					if(network.XCoordinate(i) < MinX)
						MinX = network.XCoordinate(i);
					if(network.XCoordinate(i) > MaxX)
						MaxX = network.XCoordinate(i);
				}
		
				MaxY = network.YCoordinate(1);
				MinY = network.YCoordinate(1);
				for(int i=2; i<=network.Vertices(); i++) {
					if(network.YCoordinate(i) < MinY) 
						MinY = network.YCoordinate(i);
					if(network.YCoordinate(i) > MaxY)
					MaxY = network.YCoordinate(i);
				}
		
				// The distance between farthest points in the graph along x or y direction(we dont care which axis) 
				Max = ((MaxX+MinX)>(MaxY+MinY)) ? (MaxX+MinX) : (MaxY+MinY);
				Max++;
		}
		
		graphRead = true;
		/////    Save the initial speeds as <var> initialSpeeds</var>
		f = new FloatStack[network.Vertices() ];
		initialSpeeds = new FloatStack[network.Vertices() ];
		
		for(int i=0; i< network.Vertices(); i++) {
			initialSpeeds[i] = new FloatStack(network.NoofLinks(i+1) );
			for(int j=0; j< network.NoofLinks(i+1); j++)
				initialSpeeds[i].push( network.Speed[i].access( j) );
		}
		
		f = initialSpeeds ;
		da.setMapVariables();
		da.repaint();
		
	}
	
	/*
	*	helps in changing either network file or land use file
	*	if it is a network file then it redraws the network on the DrawArea using the previous "drawGraph" function
	*/
	
	public void changeFile(String file, MenuFrame mf) {
		
		if( files[1].length() != 0 && files[0].length() != 0 ) {
									
				FileDialog fd = new FileDialog(mf, "Replace the exisitng"+file+" file", FileDialog.LOAD);
				fd.setVisible(true);
				if( !files[0].equals(fd.getDirectory()) ) {
					if( fd.getDirectory() == null ) {
					} else {
						InfoDialog id = new InfoDialog(mf,  "New "+file+" file should be in the same directory as the project file.");
						id.setSize(450, 75);
						id.setVisible(true);
					}
				} else
					if( file == "network" ) {
						if( !files[2].equals( fd.getFile() ) ) {
							files[2] = fd.getFile();
							try {
								network = new DirectedGraph(files[0], files[2]);
							} catch( IOException e) {
								InfoDialog id =new InfoDialog( mf, "Unable to open the new network file");
							}
							drawGraph();
							runnable = false;
							resultsAvailable = false;
						
						}
					} else {
						if( !files[3].equals( fd.getFile() ) ) {
							files[3] = fd.getFile();
							try {
								landUse = new Automata(files[0], files[3]);
							} catch( IOException e) {
								InfoDialog id =new InfoDialog( mf, "Unable to open the new network file");
							}
							drawGraph();
							runnable = false;
							resultsAvailable = false;
						}
					}
			}
	}
	
	/*
	*	checks if the land use file is bigger than the minimum land use width required by the network file
	*	if the land use area is too small than the network then the files are not compatible
	*/
	
	
	public void checkCompatibility(MenuFrame mf) {
		
		
		if( files[1].length() != 0 ) {
							
				String message = new String();
				if(Max <= landUse.size() ) {
					message = "Network and Land Use are compatible.";
					runnable = true;
				} else {
					message = "Network and Land Use are incompaiblt......"+"\n"+"Select a compatible network or land use file!!!";
					runnable = false;
					System.out.println("Max ="+ Max+"  LandUseSize ="+landUse.size());
				}
				
				InfoDialog id = new InfoDialog(mf, message);
				id.setSize(450, 100);
				id.setVisible(true);
							
		}else {
			InfoDialog id = new InfoDialog(mf, "No project file is opened!!!");
			id.setSize(300, 75);
			id.setVisible(true);	
		}
		
	}
	
	/*
	*	depending on the kind of variables window requested the following function helps in opening the varibales window
	*/
	
	public void openVariablesWindow( int i, MenuFrame mf) {
		
		switch( i) {
			case 0:
				vd[0] = new VariablesDialog(mf, lables[0], defaultLinkCost);
				vd[0].setSize(300, 100);
				vd[0].setVisible(true);
				defaultRevenue[0] = vd[0].f[1];
				defaultRevenue[1] = vd[0].f[2];
				defaultRevenue[3] = vd[0].f[3];
				break;
			case 1:
				boolean b[] = {true, true, false, true, false};
				String s = "Fields \""+lables[1][1]+"\",  \""+lables[1][2]+"\",  and \""+lables[1][4]+"\" are not editable.\n"
						+"To change these values please use the Link Cost Function. ";
				vd[1] = new VariablesDialog(mf, lables[1], defaultRevenue,  b, s);
				vd[1].setSize(400, 200);
				vd[1].setVisible(true);
				break;
			case 2:
				vd[2] = new VariablesDialog(mf, lables[2], defaultCost);
				vd[2].setSize(300,  150);
				vd[2].setVisible(true);
				break;
			case 3:
				mV = new miscellaneousVariables(mf, lables[3], defaultMiscellaneous, defaultMiscellaneousBoxes);
				mV.setSize(300, 150);
				mV.setVisible(true);
				break;
			
		}
	}
	
	/*
	*	used in selecting the names of the output file
	*/
	
	public void selectOutputFiles( MenuFrame mf) {
			
		targetFiles outputFiles = new targetFiles(mf);
		outputFiles.setSize(350, 100);
		outputFiles.setVisible(true);
			
	}
	
	/*
	*	runs the network dynamics program
	*	@see	<class>NetworkDynamics</class>
	*/
	
	public void run() {
		
		System.out.println("Inside run code block");
		
		float vars[] = new float[18];
		
		///////////  
		vars[0] = defaultLinkCost[0] ;
		vars[1] = defaultLinkCost[1] ;
		vars[2] = defaultLinkCost[2] ;
		vars[3] = defaultLinkCost[3] ;
		
		vars[4] = defaultRevenue[0] ;
		vars[5] = defaultRevenue[1] ;
		vars[6] = defaultRevenue[2] ; 
		vars[7] = defaultRevenue[3] ;
		vars[8] = defaultRevenue[4] ;
		
		vars[9] = defaultCost[0] ;
		vars[10] = defaultCost[1] ;
		vars[11] = defaultCost[2] ;
		vars[12] = defaultCost[3] ;
		
		vars[13] = defaultMiscellaneous[0] ;
		vars[14] = defaultMiscellaneous[1] ;
		vars[15] = defaultMiscellaneous[2] ;
		
		vars[16] = ( defaultMiscellaneousBoxes[0] == true)? 1: 0;
		vars[17] = ( defaultMiscellaneousBoxes[1] == true)? 1:0;
		///////////  
		
		NetworkDynamics nd = new NetworkDynamics( vars );
		try{
			nd.runNetworkDynamix( network, landUse, files);
		} catch( IOException ie) {
			
		}
		
		resultsAvailable = true;
		
		try {
			readOutputFiles();
		} catch( IOException e) {
		}
			
	}
	
	/*
	*	helps in reading the output files.
	*/
	
	private void readOutputFiles() throws IOException{
		
		System.out.println("Reading output Files");
		
		int iterations = (int) defaultMiscellaneous[2];
		int vertices = network.Vertices();
		
		Speed = new FloatStack[iterations][vertices];
		Flow = new FloatStack[iterations][vertices];
		avgSpeed = new float[iterations];
		avgFlow = new float[iterations];
		
		ReadAFile readSpeeds = null;
		ReadAFile readFlows = null;
		
		try {
			readSpeeds = new ReadAFile( files[0], files[4]);
			readFlows = new ReadAFile( files[0], files[5]);
		} catch( FileNotFoundException e) {
		}
		/// read deafult stuff at the beginning of the output files
		for(int i=0; i<6; i++) {
			String dump = readSpeeds.readLine();
			dump = readFlows.readLine();
		}
		for(int i=0; i<iterations; i++){	
			for(int p=0; p<vertices; p++) {
				Speed[i][p] = new FloatStack( network.NoofLinks(p+1) );
				Flow[i][p] = new FloatStack( network.NoofLinks(p+1) );
				for(int q=0; q<network.NoofLinks(p+1); q++) {
					Speed[i][p].push( readSpeeds.readfloat() );
					Flow[i][p].push( readFlows.readfloat() );
				}
			}
		}
		
		for(int i=0; i< iterations; i++) {
			avgSpeed[i] = readSpeeds.readfloat();
			avgFlow[i] = readFlows.readfloat();
		}
		
		readSpeeds.close();
		readFlows.close();
		
		for(int i=0; i<iterations; i++)
			System.out.println((i+1)+"  "+avgSpeed[i]);
		System.out.println("  ");
		for(int i=0; i<iterations; i++)
			System.out.println((i+1)+"  "+avgFlow[i]);
		
	}
		
	/*
	*	helps in saving the file with a new name: uses the function "saveFile()"
	*
	*/
	
	public void saveAsFile( MenuFrame mf) {
		
		FileDialog fd = new FileDialog(mf, "Open Network Dynamics Project", FileDialog.SAVE);
		fd.setDirectory(files[0]);
		fd.setVisible(true);
		
		String rootDir = fd.getDirectory();
		
		if(  !rootDir.equals( files[0] )   ){
			InfoDialog id = new InfoDialog( mf, "Unable to save the file as the selected directory is not same as the project directory!!!!");
		} else {
			files[1] = fd.getFile();
			try{
				saveFile();	
			} catch(IOException e) {
			}
		}
		
		
	}
	
	/*
	*	helps in saving a file.
	*/
	
	public void saveFile() throws IOException{
		
		WriteAFile  savingProject = new WriteAFile( files[0], files[1]);
		
		String yes_no = new String();
		
		savingProject.writeMe("NetworkDynamics");
		savingProject.writeMe("Basics:"); 
		savingProject.writeMe("	path:			"+files[0]);
		savingProject.writeMe("	projectFile: 	"+files[1]);
		savingProject.writeMe("	networkFile: 	"+files[2]);
		savingProject.writeMe("	landuseFile:	"+files[3]);
		yes_no = (runnable == true)? "y": "n" ; 
		savingProject.writeMe("Compatibility:		"+yes_no);
		yes_no = new String( (resultsAvailable == true)?"y" : "n" );
		savingProject.writeMe("OutputFile:		"+yes_no);
		savingProject.writeMe("	speeds:		"+files[4]);
		savingProject.writeMe("	flows:		"+files[5]);
		savingProject.writeMe("Coefficients:");
		String variables = new String();
		for(int i=0; i< defaultLinkCost.length; i++)
			variables += " "+Float.toString( defaultLinkCost[i]   );
		savingProject.writeMe("	link cost:		"+ variables);
		variables = new String();
		for(int i=0; i< defaultRevenue.length; i++)
			variables += " "+Float.toString(defaultRevenue[i]);
		savingProject.writeMe("	revenue:		"+variables);
		variables = new String();
		for(int i=0; i< defaultCost.length; i++)
			variables += " "+Float.toString(defaultCost[i]);
		savingProject.writeMe("	cost:			"+variables);
		variables = new String();
		for(int i=0; i< defaultMiscellaneous.length; i++)
			variables += " "+Float.toString(defaultMiscellaneous[i]);
		savingProject.writeMe("	miscellaneous:	"+variables);
		yes_no = new String(  ( defaultMiscellaneousBoxes[0] == true)? "yeah": "nah");
		savingProject.writeMe("	symmetry:	"+yes_no);
		yes_no = new String( (defaultMiscellaneousBoxes[1] == true)? "yeah": "nah");
		savingProject.writeMe("	averageSpeeds?:	"+yes_no);
		savingProject.writeMe("Preferences:");
		savingProject.writeMe("Draw:	");
		savingProject.writeMe("Comments:	");
		
		savingProject.close();
				
	}
	
	/*
	*	provides the functions necessary for browsing through the results
	*/
	
	public void actionPerformed(ActionEvent ae) {
		String arg = (String) ae.getActionCommand();
		if( resultsAvailable) {
				
			if(arg.equals("<<")) {
				currentYear = 1;
				if( drawSpeeds)
					f = Speed[0];
				else 
					f = Flow[0];
				da.repaint();
			} else if( arg.equals("<") ) {
				if( currentYear >= 2 & currentYear <= defaultMiscellaneous[2] ) {
					currentYear--;
					if( drawSpeeds)
						f = Speed[currentYear-1];
					else 
						f = Flow[currentYear-1];
					da.repaint();
				}  else {
					currentYear = (int) defaultMiscellaneous[2];
					if( drawSpeeds)
						f = Speed[currentYear-1];
					else 
						f = Flow[currentYear-1];
					da.repaint();	
				}
			} else if(arg.equals(">") ) {
				if( currentYear >=1 && currentYear <defaultMiscellaneous[2] ) {
					currentYear++;
					if( drawSpeeds)
						f = Speed[currentYear-1];
					else 
						f = Flow[currentYear-1];
					da.repaint();
				} else  {
					currentYear = 1;
					if( drawSpeeds)
						f = Speed[currentYear-1];
					else 
						f = Flow[currentYear-1];
					da.repaint();
				} 
				
			} else if(arg.equals(">>") ) {
				currentYear = (int) defaultMiscellaneous[2];
				if( drawSpeeds)
					f = Speed[currentYear-1];
				else 
					f = Flow[currentYear-1];
				da.repaint();
			} else if(arg.equals("Speed") ) {
				dp.whichAttribute.setLabel("Volume");
				drawSpeeds = false;
				
				if( currentYear == 0 )
					currentYear = 1;
				
				f = Flow[currentYear-1];
				da.repaint();
				
			} else if( arg.equals("Volume") ) {
				dp.whichAttribute.setLabel("Speed");
				drawSpeeds = true;
				if( currentYear == 0)
					f = initialSpeeds;
				else
					f = Speed[currentYear-1];
				da.repaint();
			} else if( arg.equals("Initial Speeds") ) {
				dp.whichAttribute.setLabel("Speed");
				currentYear = 0;
				f = initialSpeeds;
				da.repaint();
			}
			
			dp.year.setText("  "+Integer.toString(currentYear)+"  ");
		
		}
	
	}

////************************************************* Inner class targetFiles *************************************************////
class targetFiles extends Dialog implements ActionListener {
	
	TextField tf[] = new TextField[2];
	
	MenuFrame mf;
	
	public targetFiles(MenuFrame mf) {
		super(mf, "Ouptput File Names");
		setLayout(new BorderLayout());
		
		this.mf = mf;
			
		
		Panel p = new Panel();
		p.setLayout( new GridLayout(3, 2) );
		p.setSize(300, 100);
		
		p.add( new Label("Output File name for Speeds") );
		p.add( tf[0] = new TextField(files[4]) );
		p.add( new Label("Output File name for Flows") );
		p.add( tf[1] = new TextField(files[5]) );
		
		Button  b;
		p.add( b = new Button("Cancel") ); 
		b.addActionListener(this);
		
		p.add( b = new Button("Ok") );
		b.addActionListener(this);
		
		add(p, "Center");
		
	}
	
	
	public void actionPerformed( ActionEvent ae) {
		String arg = (String) ae.getActionCommand();
		
		if( arg == "Cancel") {
			dispose();
		} else if(arg == "Ok") {
			
			files[4] = (String) tf[0].getText();
			files[5] = (String) tf[1].getText();
			
			if( !files[4].equals(files[5])   )									
				dispose();
			else {
				InfoDialog id = new InfoDialog(mf, "File names should be distinct!!!!!");
				id.show();
			}
		}
		
		
		
	}
	
	
}

////************************************************* Enf of inner class targetFiles *************************************************////


////************************************************* Inner class  DrawPanel   *************************************************////
	class DrawPanel extends Panel {
		
		Label year = new Label("  0  ", Label.CENTER);
		Button whichAttribute = new Button("");
		
		public DrawPanel(myProjectHandler mph) {
	
			whichAttribute = new Button ("Speed");
			Button first = new Button("<<");
			Button previous = new Button("<");
			year = new Label(  "  0   " , Label.CENTER );
			Button next = new Button(">");
			Button last = new Button(">>");
			Button initial = new Button("Initial Speeds");
	
	
			add(whichAttribute);
			add( first);
			add( previous );
			add(year);
			add( next );
			add( last);
			add(initial);
			
			whichAttribute.addActionListener(mph);
			first.addActionListener(mph);
			previous.addActionListener(mph);
			next.addActionListener(mph);
			last.addActionListener(mph);
			initial.addActionListener( mph);
		}
				
	}
////************************************************* End of  Inner class  DrawPanel  *************************************************////

////************************************************* Inner class  *************************************************////
	class DrawArea extends Panel {
		
		int Scale;	// Scale of magnification or diminision	
		int Trans;	// translation 
		int dim;      // size of the DrawArea
		int radius;   //Radius of circle that represents a node
		Dimension d;  //Current Dimension of the DrawArea (dynamic variable)
		
		// default constructor
						
		public DrawArea() {
			super();
			setSize(700, 700);
		}
		
		// sets the variables required for drawing the network
		
		void setMapVariables() {
								
			d = getSize();
						
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
		
		
		//// will be drawn for the current atributes (speed or flow) specified by f  for the year <param>currentYear</param> of the
		//	outer class 
		
		private void drawLinks_Speed(Graphics g) {
			
			if( f != null) {
			
			//// calculates the minimum and maximum values of f[]
			
			float min, max;
			
			float temp = 0;
			min = max =  f[0].access(0) ;
			for(int i=0; i<network.Vertices(); i++) {
				for(int j=0; j<network.NoofLinks(i+1); j++) {
					temp = f[i].access(j);
					if( max < temp)
						max = temp;
					if( min > temp)
						min = temp; 
				}
			}
		
			//	int count1 = 0, count2 = 0, count3 = 0, count4 = 0;
		
			int xcoord[] = new int[5];
			int ycoord[] = new int[5];
			float factor;
			
			/*
			*	draws a rectangle beside the link depending on where the link attribute falls
			*/
			
			
			for(int i =0; i<network.Vertices(); i++) {
				for(int j=0; j<network.NoofLinks(i+1); j++) {
					factor  = (float)(0.5*f[i].access(j) );
					int startx, starty, endx, endy;
					startx = (int)(Scale/2)+Trans + (int)(network.XCoordinate(i+1)*Scale);
					starty =  Trans - (int)(Scale/2)+ (int)(Scale*Max) - (int)(network.YCoordinate(i+1)*Scale);
					int k = network.EndNodeNumbers(i+1, j+1);
					endx = (int)(Scale/2)+Trans + (int)(network.XCoordinate(k)*Scale);
					endy =  Trans - (int)(Scale/2)+(int) (Scale* Max) - (int)(network.YCoordinate(k)*Scale);
								
						
					float step = (max-min)/4;
				
					{
						 if( f[i].access( j ) <=  (  min+step)  ) {
						 	g.setColor(new Color(8, 140, 14) );  /////Green
							//g.setColor(new Color(150, 150, 150) );
							factor = (float) (0.5*Scale);
							//count1++;
						}
						else if ( f[i].access( j ) <=  (min+2*step)  ) {
						 	g.setColor(new Color(60, 100, 250) );   ////Blue
							//g.setColor(new Color( 115, 115, 115) );
							factor = (float) (0.75*Scale);
							//count2++;
						}
						else if ( f[i].access( j ) <=  ( min+3*step )  ) {
						 	g.setColor(new Color(250, 125, 0)  );    ////// Orange
							//g.setColor(new Color(70, 70, 70) );
							factor = (float) (Scale);
							//count3++;
						}
						else {
						 	g.setColor(new Color (200, 20, 20) );   //// Red
							//g.setColor(new Color(25, 25, 25) );
							factor = (float) (1.25*Scale);
							// count4++;
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
		
			
			}
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
			for(int i = 0; i< network.Vertices(); i++) {
				g.setColor(Color.black);
				int newx, newy;
				newx = (int)(Scale/2)+Trans + (int)(network.XCoordinate(i+1)*Scale);
				newy  = (int)(Scale*Max)-(int)(Scale/2) - (int)(network.YCoordinate(i+1)*Scale) + Trans;
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
////************************************************* End of Inner class   *************************************************////
	

	
}

///////  End of myProjectHandler class  </class>


class MyMenuHandler implements ActionListener, ItemListener {
	
	MenuFrame mf;
	myProjectHandler mph;
		
	boolean projectOpened = false;
	boolean savedProject = false;
	
	
	public MyMenuHandler( MenuFrame mf, myProjectHandler mph ) {
			this.mf = mf;
			this.mph = mph;
					
	}
	
	public void actionPerformed( ActionEvent ae) {
		String arg = (String) ae.getActionCommand();
	
		if( arg == "Preferences") {
			InfoDialog id = new InfoDialog(mf, "You cannot change my prefernces");
			id.setVisible(true);
		} else if( arg == "New Text File") {
			textFile tf = new textFile();
		} else if (arg == "New Project...") {
			//mph = new myProjectHandler(mf);
			
		} else if( arg == "Open Project..." ) {
			//mph = new myProjectHandler(mf);
			projectOpened = true;
			savedProject = true;
			try {
				mph.openProject(mf );
			} catch (IOException e) {
			}
			mf.setTitle( mph.files[1]);
			
		} else if( arg == "Close Project" ) {
			//mph.setVisible(false);
		} else if(arg == "Save") {
			if( projectOpened  & !savedProject) {
				try{
					mph.saveFile();
				} catch(IOException e) {
				} 
				
			}
		} else if(arg == "Save as ...") {
			if( projectOpened) {
				mph.saveAsFile(mf);
				mf.setTitle( mph.files[1] );
			}
			
			
		} else if( arg == "Change Network" ||  arg == "Change Land Use") {
				
			String file = ( arg == "Change Network")?  "network" : "land use";
			if( projectOpened )
				mph.changeFile( file, this.mf);
			
			
		}else if (arg == "Check Compatibility" ) {
			if( projectOpened)
				mph.checkCompatibility(mf);
				
		} else if(arg == "Link Cost Function") {
			if( projectOpened )
				mph.openVariablesWindow(0, mf);
			
		} else if(arg == "Revenue Function") {
			if( projectOpened)
				mph.openVariablesWindow(1, mf);
		
		} else if(arg == "Cost Function") {
			if( projectOpened)
				mph.openVariablesWindow(2, mf);
		
		} else if(arg == "Miscellaneous") {
			if( projectOpened)
				mph.openVariablesWindow(3, mf);
		} else if(arg == "Reset Default Coefficients") {
			mph.resetDefaultValues();
		}else if( arg == "Network") {
			
		} else if ( arg ==  "Quit") {
			System.exit(0);
		} else if( arg == "Set Target Files") {
			if(projectOpened & mph.runnable ) {
				mph.selectOutputFiles(mf);
			}
		} else if (arg == "Run") {
			System.out.println(mph.files[4].length() != 0 & mph.files[5].length() != 0 );
			System.out.println("Speeds:"+mph.files[4]+"---Flows:"+mph.files[5]);
			if(projectOpened & mph.runnable & mph.files[4].length() != 0 & mph.files[5].length() != 0 ){
				mph.run();				
			} else {
			}
		} 
		
	}
	
	

	
	static void writeMe(int intVal, FileOutputStream fout) throws IOException {
		String s = Integer.toString(intVal);
		
		for(int i=0; i<s.length(); i++)
			fout.write( (int)(    s.charAt(i)   )   );
				
	}
	
	static void writeMe(float floatVal, FileOutputStream fout) throws IOException {
		String s = Float.toString(floatVal);
		
		for(int i=0; i<s.length(); i++)
			fout.write( (int)(    s.charAt(i)   )   );
				
	}
	
	
	public void itemStateChanged(ItemEvent ie ) {
	}
	
	
	
	private String open(String path) throws IOException {
		Reader fin = null;
		File f = new File(path);
		String s = "";
		try {
				fin = new FileReader( f  );
			} catch( FileNotFoundException e) {
				System.out.println("File Not Found");
			}
			int i=0;
			do{
				try {
					i = fin.read();
				} catch(IOException ie) {
					System.out.println("IOException");
				}
				if (i != -1)
					s += (char)i;
			} while(i != -1);
		
		return s;
		
		
	}
	
	
}


///////   End of  MyMenuHandler class  </class>



class MenuFrame extends Frame {

	public MenuFrame(String title, myProjectHandler mph) {
		super(title);
		
		setLayout( new BorderLayout() );
		setSize(700, 700);
		
		add("Center", mph.da);
		add("South", mph.dp);
		
		
		
		MenuBar mbar = new MenuBar();
		setMenuBar(mbar);
		
		Menu file = new Menu("File");
		
		MenuItem newTextFile = new MenuItem("New Text File", new MenuShortcut(KeyEvent.VK_N) );
		MenuItem newProject = new MenuItem("New Project...", new MenuShortcut(KeyEvent.VK_N, true) );
		MenuItem  openProject = new MenuItem("Open Project...", new MenuShortcut( KeyEvent.VK_O, true)  );
		MenuItem closeProject = new MenuItem("Close Project", new MenuShortcut( KeyEvent.VK_W) );
		MenuItem dash1 = new MenuItem("-");
		MenuItem  saveProject = new MenuItem("Save", new MenuShortcut( KeyEvent.VK_S)  );
		MenuItem  saveasProject = new MenuItem("Save as ...");
		MenuItem dash2 = new MenuItem("-");
		MenuItem quit = new MenuItem("Quit", new MenuShortcut( KeyEvent.VK_Q)  );
			
		file.add(newTextFile);
		file.add(newProject);
		file.add(openProject);
		file.add(closeProject);
		file.add(dash1);
		file.add(saveProject);
		file.add(saveasProject);
		file.add(dash2);		
		file.add(quit);
		
		mbar.add(file);
		
		Menu project = new Menu("Project");
		
		MenuItem changeNetwork = new MenuItem( "Change Network");
		MenuItem changeLandUse = new MenuItem("Change Land Use");
		Menu changeCoefficients = new Menu("Change Coefficients");
		MenuItem dash3 = new MenuItem("-");
		MenuItem debug = new MenuItem("Check Compatibility");
		MenuItem run = new MenuItem("Run", new MenuShortcut(KeyEvent.VK_R, true) );
		MenuItem dash4 = new MenuItem("-");
		MenuItem  setTarget = new MenuItem("Set Target Files");
		MenuItem preferences = new MenuItem("Preferences");
		
		MenuItem linkCost = new MenuItem("Link Cost Function");
		MenuItem revenueFunction  = new MenuItem("Revenue Function");
		MenuItem costFunction = new MenuItem("Cost Function");
		MenuItem miscellaneous = new MenuItem("Miscellaneous");
		MenuItem dash5 = new MenuItem("-");
		MenuItem reset = new MenuItem("Reset Default Coefficients");
		
		changeCoefficients.add(linkCost);
		changeCoefficients.add(revenueFunction);
		changeCoefficients.add(costFunction);
		changeCoefficients.add(miscellaneous);
		changeCoefficients.add(dash5);
		changeCoefficients.add(reset);
		
		////////   add additional menu items to  changeCoefficients, setTarget ...... what about preferences?
		
		project.add(changeNetwork);
		project.add(changeLandUse);
		project.add(changeCoefficients);
		project.add(dash3);
		project.add(debug);
		project.add(run);
		project.add(dash4);
		project.add(setTarget);
		project.add(preferences);
		
		mbar.add(project);
		
		
		
		
		MenuItem  circular, grid1, grid2, grid3, grid4, grid5;
		/*
		//openFile.add(circular = new MenuItem("Circular Network"));
		openFile.add(grid1 = new MenuItem("Twin Cities"));
		openFile.add(grid2 = new MenuItem("10X10 nodes Grid Network"));
		openFile.add(grid3 = new MenuItem("15X15 nodes Grid Network")); 
		openFile.add(grid4 = new MenuItem("20X20 nodes Grid Network")); 
		openFile.add(grid5 = new MenuItem("100X100 nodes Grid Network")); 
		*/		
		
		
		//file.add( openFile);
		
		
		Menu view = new Menu("View");
		
		MenuItem variablesSummary = new MenuItem("Variables Summary");
		MenuItem network = new MenuItem("Network");
		MenuItem graph = new MenuItem("Graph");
		MenuItem speedDynamix = new MenuItem("Speed Dynamics");
		
		view.add(variablesSummary);
		view.add(network);
		view.add(graph);
		view.add(speedDynamix);
		mbar.add(view);
		/*
		//circular.addActionListener(this.demo);
		grid1.addActionListener(this.demo);
		grid2.addActionListener(this.demo);
		grid3.addActionListener(this.demo);
		grid4.addActionListener(this.demo);
		*/
		//openFile.addActionListener(this.demo);
		//quit.addActionListener(this.demo);
		
		//graph.addActionListener(this.demo);
		//speedDynamix.addActionListener(this.demo);
		
		MyMenuHandler mmh = new MyMenuHandler(this, mph);
		
		newTextFile.addActionListener(mmh);
		newProject.addActionListener(mmh);
		openProject.addActionListener(mmh);
		closeProject.addActionListener(mmh);
		saveProject.addActionListener(mmh);
		saveasProject.addActionListener(mmh);
		quit.addActionListener(mmh);
		
		changeNetwork.addActionListener(mmh);
		changeLandUse.addActionListener(mmh);
		debug.addActionListener(mmh);
		run.addActionListener(mmh);
		setTarget.addActionListener(mmh);
		preferences.addActionListener(mmh);
		
		linkCost.addActionListener(mmh);
		revenueFunction.addActionListener(mmh);
		costFunction.addActionListener(mmh);
		miscellaneous.addActionListener(mmh);
		
		variablesSummary.addActionListener(mmh);
		network.addActionListener(mmh);
		
		
		
		
		
	}
	
		
}
///// End of MenuFrame class
