package doctopdf;


public class CommandLineValues {

	private static CommandLineValues commandLineValues;
	//Store input file path
	public String inputFile=null;
	//Store output file path
	public String outputFile=null;
	private CommandLineValues() {}
	
	public static CommandLineValues getInstance() {
		if(commandLineValues==null)
			commandLineValues=new  CommandLineValues();
		return commandLineValues;
	}
	
	public boolean handleCommandLineArgs(String[] args) {
		int length=args.length;
		//Check arguments length should be between 2 to 4
		if(!(length>=2 && length<=4)) {
			System.out.println("Input data not complete.\nPlease use any of following formats:\n"
					+ "input<space><input_file_path_with_name><space><output_file_name>\n"+"OR\n"
					+"input<space><input_file_path_with_name><space>output<output_file_path_with_name>\n"+"OR\n"+
					"<input_file_path_with_name><space>output<output_file_path_with_name>");
			return false;
			}
		
		//as per number of arguments, the application will process input and output
		switch (length) {
		case 2:
			return handleTwoArgs(length,args);
			
		case 3:
			return handleThreeArgs(length,args);
			
		case 4:
			return handleFourArgs(length,args);

		}
		
		return false;
		
	}



	//Two arguments means jar is being used from user directory where the files are also present
	//So the path will be take from user directory
	private boolean handleTwoArgs(int length,String[] args) {
		String infile=args[0].toLowerCase();
		String outfile=args[1].toLowerCase();
		if(!checkFormatOfInput(infile) || !checkOutFileFormat(outfile))
		{
			return false;
		}
		
		inputFile = System.getProperty("user.dir")+"\\"+args[0];
		outputFile = System.getProperty("user.dir")+"\\"+args[1];
		
		return true;
		
	}
	//three arguments means input path or output path is provided 
	private boolean handleThreeArgs(int length,String[] args) {

		String arg1=args[0].toLowerCase();
		String arg2=args[1].toLowerCase();
		String arg3=args[2].toLowerCase();
		//check if input keyword is there properly
		boolean res=checkarg1ShouldBeInputPathString(arg1);
		//check if input path and output file format is correct
		if(!res || (!checkFormatOfInput(arg2) || !checkOutFileFormat(arg3))) {
			return false;
		}
		//input path will be complete path of input file
		inputFile=args[1];
		//output file path will be user directory
		outputFile=System.getProperty("user.dir")+"\\"+args[2];
		return true;
	}
	

	//four arguments means input path and output path is provided 
	private boolean handleFourArgs(int length,String[] args) {
		//input
		String arg1=args[0].toLowerCase();
		//path 
		String arg2=args[1].toLowerCase();
		//output
		String arg3=args[2].toLowerCase();
		//output path
		String arg4=args[3].toLowerCase();
		boolean res=checkarg1ShouldBeInputPathString(arg1);
		//check input and output formats
		if(!res || (!checkFormatOfInput(arg2) || !checkarg3ShouldBeInputPathString(arg3)|| !checkOutFileFormat(arg4))) {
			return false;
		}
		//input path will be complete path of input file
		inputFile=args[1];
		//output file path will be the path given
		outputFile=args[3];
		return true;
		
	}
	
	private boolean checkarg3ShouldBeInputPathString(String arg3) {
		if( arg3.equalsIgnoreCase("output"))
			return true;
		System.out.println("Please provide correct format: input <input file path> output <output file path>");
		return false;
	}

	private boolean checkarg1ShouldBeInputPathString(String arg1) {	
		if (arg1.equalsIgnoreCase("input"))
			return true;
		System.out.println("Please provide correct format: input <input file path> outputfilename");
		return false;
	}

	
	private boolean checkOutFileFormat(String outFile) {
		// TODO Auto-generated method stub
		if(outFile.endsWith("pdf"))
			return true;
					
		System.out.println("Output file format not correct. File should be  .pdf");
		return false;
	}

	private boolean checkFormatOfInput(String inputFile2) {
	if(inputFile2.endsWith("doc") || inputFile2.endsWith("docx")){
		return true;
	}
		System.out.println("Input file format not correct. File should be either .doc or .docx");
		return false;
	}

	
	public String getInputPath() {
		return inputFile;
	}
	
	public String getOutputPath() {
		return outputFile;
	}

}
