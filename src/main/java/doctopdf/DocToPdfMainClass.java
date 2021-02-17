package doctopdf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.docx4j.Docx4J;
import org.docx4j.convert.out.pdf.PdfConversion;
import org.docx4j.convert.out.pdf.viaXSLFO.PdfSettings;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.fonts.Mapper;
import org.docx4j.fonts.PhysicalFont;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;


import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
public class DocToPdfMainClass {

	/**
	 * @param args the command line arguments
	 * @throws java.io.FileNotFoundException
	 * @throws InvalidFormatException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException, ExecutionException, InvalidFormatException {

		/*
		 * FileInputStream fis = new FileInputStream("F:\\samples\\example1.docx");
		 * XWPFDocument document = new XWPFDocument(OPCPackage.open(fis)); File outFile
		 * = new File("F:\\samples\\file.pdf"); OutputStream out = new
		 * FileOutputStream(outFile); PdfOptions options =
		 * PdfOptions.create().fontEncoding("windows-1250");
		 * PdfConverter.getInstance().convert(document, out, options);
		 */
		 ConvertToPDF("F:\\samples\\example1.docx", "F:\\samples\\Test.pdf");
	    }

	    public static void ConvertToPDF(String docPath, String pdfPath) {
	        try {
	            InputStream doc = new FileInputStream(new File(docPath));
	            XWPFDocument document = new XWPFDocument(doc);
	            PdfOptions options = PdfOptions.getDefault();
	            
	            OutputStream out = new FileOutputStream(new File(pdfPath));
	            PdfConverter.getInstance().convert(document, out, options);
	        } catch (IOException ex) {
	            System.out.println(ex.getMessage());
	        }
	    }



private static void documents4j(String[] args) {
	try {
		String inputPath=null;
		String outputPath=null;
		CommandLineValues commandLineValues=CommandLineValues.getInstance();
		/*if(!commandLineValues.handleCommandLineArgs(args)) {
				return;
			}
			else {*/
		inputPath=commandLineValues.getInputPath();
		outputPath=commandLineValues.getOutputPath();

		System.out.println("Input File path:"+ inputPath);
		System.out.println("Output File path:"+outputPath);
		//File wordFile = new File( inputPath); 
		//File target = new File(outputPath);

		/*IConverter converter = LocalConverter.builder()
						.baseFolder(new File("C:\\temp"))
						.workerPool(20, 25, 2, TimeUnit.SECONDS)
						.processTimeout(30, TimeUnit.SECONDS)
						.build();		

				System.out.println("Starting Conversion...");
				System.out.println("Converting.Please Wait...!");

				Future<Boolean> conversion = converter
						.convert(wordFile).as(DocumentType.MS_WORD)
						.to(target).as(DocumentType.PDF)
						.prioritizeWith(1000) // optional
						.schedule();
		 */
		File wordFile = new File("F:\\samples\\example1.docx");
		InputStream targetStream = new FileInputStream(wordFile);

		URL url = new URL("http://localhost:9998");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/vnd.com.documents4j.any-msword");
		conn.setRequestProperty("Accept", "application/pdf");
		conn.setRequestProperty("Converter-Job-Priority", "1000");


		OutputStream os = conn.getOutputStream();
		os.write(IOUtils.toByteArray(targetStream));
		os.flush();

		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
		FileWriter ostream = new FileWriter("F:\\samples\\test.pdf");
		BufferedWriter out = new BufferedWriter(ostream);
		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
			out.write(output+"\n");
		}
		br.close();
		out.close();
		os.close();
		conn.disconnect();

		try {
			Thread.sleep(2000);
		}catch(Exception ex) {

		}
		System.out.println("Conversion Done!");

		System.out.println("Exiting converter...");
		//converter.shutDown();
		//	}
	}
	catch(IllegalStateException ex) {
		System.out.println("Trying to Shutdown!");
	}
	catch(Exception ex) {
		System.out.println("Trying to Shutdown!");
	}

}



}
