package Servlet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import Beans.checkFiles;
import Beans.emailToUser;
import Beans.getResults;
import Beans.runModel;


/**
 * Servlet implementation class uploadPDB
 */
@WebServlet("/uploadPDB")
public class uploadPDB extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
     
     
     private static String homeFolder = "/var/lib/tomcat7/webapps/ROOT/";
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public uploadPDB() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		String taskName = "";
	    String pdbName = "";
	    String chain = "";
	    String ion = "";
		
	    String folderName = "";
	    String path = "";
	    String email = "";
		email = (String)request.getParameter("emailex");
		
		long time = System.currentTimeMillis();
		Random ran = new Random(time);
		folderName = Long.toString(time)+Long.toString(ran.nextLong());
		File folder = new File(homeFolder + "users/" + folderName + "/");
		//System.out.println(folder.setWritable(true, false));
		
		if(!folder.exists())
		{
			if(folder.mkdir())
			{
				System.out.println(folder.getAbsolutePath());
				path = folder.getAbsolutePath();
				folder.setWritable(true, false);
				folder.setExecutable(true, false);
				folder.setReadable(true, false);
			}
			else
			{
				System.out.println(folder.getAbsolutePath());
				System.out.println("can not make folder");
				System.exit(1);
			}
		}
		else
		{
			while(folder.exists())
			{
				time = System.currentTimeMillis();
				ran = new Random(time);
				folderName = Long.toString(time)+Long.toString(ran.nextLong());
				folder = new File(homeFolder  + "users/" + folderName + "/"); 
				folder.setWritable(true, false);
				folder.setExecutable(true, false);
				folder.setReadable(true, false);
			}
			if(folder.mkdir())
			{
				System.out.println(folder.getAbsolutePath());
				path = folder.getAbsolutePath();
				folder.setWritable(true, false);
				folder.setExecutable(true, false);
				folder.setReadable(true, false);
			}
			else
			{
				System.out.println("can not make folder");
				System.exit(1);
			}
		}
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = (File)servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		try
		{
			List<FileItem> items = upload.parseRequest(request);
			Iterator<FileItem> iter = items.iterator();
			while(iter.hasNext())
			{
				FileItem item = iter.next();
				
				if(item.isFormField())
				{
					String name = item.getFieldName().trim();
					String value = item.getString().trim();
					
					System.out.println(name + ", " + value);
					
					if(name.equals("taskname"))
						taskName = value;
					else if(name.equals("email"))
						email = value;
					else if(name.equals("pdb") && !value.equals(""))
					{
						pdbName = value;
						try
						{
							downloadPDB(pdbName, path);
						}catch(Exception e)
						{
							e.printStackTrace();
						}
					}
					else if(name.equals("chain"))
						chain = value;
					else if(name.equals("ion"))
						ion = value;
					else
					{
						//do nothing
					}
				}
				else
				{
					System.out.println("here");
					String name = item.getName();
					if(name.endsWith(".pdb") || name.endsWith(".PDB"))
						pdbName  = name.split("\\.")[0].trim();
					String type = item.getContentType();
					long size = item.getSize();
					
					//rename the file in case users send files with wrong names
					if(item.getFieldName().equals("uploadseq"))
						name = pdbName + "_" + chain + ".fasta.txt";
					if(item.getFieldName().equals("uploaddssp"))
						name = pdbName + ".dssp";
					if(item.getFieldName().equals("uploadpssm"))
						name = pdbName + "_" + chain + ".fasta.pssm";
					if(item.getFieldName().equals("uploadnaccess"))
						name = pdbName + ".rsa";
					if(item.getFieldName().equals("uploaddisopred"))
						name = pdbName + "_" + chain + ".fasta.diso";
					if(item.getFieldName().equals("uploadse1"))
						name = "ex_" + pdbName + "_CN";
					if(item.getFieldName().equals("uploadse2"))
						name = "ex_" + pdbName + "_HSEAD";
					if(item.getFieldName().equals("uploadse3"))
						name = "ex_" + pdbName + "_HSEAU";
					if(item.getFieldName().equals("uploadse4"))
						name = "ex_" + pdbName + "_EBD";
					if(item.getFieldName().equals("uploadse5"))
						name = "ex_" + pdbName + "_EBU";
					if(item.getFieldName().equals("uploadse6"))
						name = "ex_" + pdbName + "_RD";
					if(item.getFieldName().equals("uploadse7"))
						name = "ex_" + pdbName + "_RDa";
					if(item.getFieldName().equals("uploadnetwork"))
						name = "net_param_" + pdbName + ".pdb_" + chain + "_6.5_1.txt";
					
					
					
					
					System.out.println(name + "; " + type + "; " + size);
					try
					{
						uploadFiles(name, path, item);
					}catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
			
		}catch(FileUploadException e)
		{
			
		}	
		
		//check the format of the files especially for the files only containing one chain
		checkFiles check =  new checkFiles();
		check.setParams(path, pdbName, chain);
		try
		{
			String results = check.check();
			
			System.out.println(results);
			String fasta = results.split(",")[0].split("===")[1].trim();
			String aa = results.split(",")[1].split("===")[1].trim();
			if(fasta.equals("1") && aa.equals("true"))
			{
				//then copy two table title files and program files to the current folder (diso_tital, net_tital, all_features.pl and metal_predict.R)
				try
				{
					
						File example = new File(homeFolder + "allNeeded/");
						if(example.isDirectory())
						{
							File files[] = example.listFiles();
							for(int i = 0 ; i < files.length; i++)
							{
								
								if(files[i].getName().equals("calc_network_parameter.sh"))
								{
									File out = new File("/var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/calc_network_parameter.sh");
									
									FileReader fr  = new FileReader(files[i]);
									BufferedReader br = new BufferedReader(fr);
									
									FileWriter fw = new FileWriter(out);
									BufferedWriter bw = new BufferedWriter(fw);
									
									String str = br.readLine();
									while(str!=null)
									{
										String tmp = str.replaceAll("\\./", "/var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/");
										tmp = tmp.replaceAll("/tmp/", "/var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/tmp/");
										bw.write(tmp + "\n");
										bw.flush();
										str = br.readLine();
									}
									out.setExecutable(true, false);
									out.setReadable(true, false);
									out.setWritable(true, false);
									
									bw.close();
									fw.close();
									br.close();
									fr.close();
									
								}
								else if(files[i].getName().equals("naccess"))
								{
									File out = new File("/var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/naccess");
									out.setExecutable(true, false);
									out.setReadable(true, false);
									out.setWritable(true, false);
									
									FileReader fr  = new FileReader(files[i]);
									BufferedReader br = new BufferedReader(fr);
									
									FileWriter fw = new FileWriter(out);
									BufferedWriter bw = new BufferedWriter(fw);
									
									String str = br.readLine();
									while(str!=null)
									{
										if(str.startsWith("set EXE_PATH ="))
										{
											String tmp = "set EXE_PATH = /var/lib/tomcat7/webapps/ROOT/users/" + folderName;
											bw.write(tmp + "\n");
											bw.flush();
										}
										else
										{
											bw.write(str + "\n");
											bw.flush();
										}
										str = br.readLine();
									}
									out.setExecutable(true, false);
									out.setReadable(true, false);
									out.setWritable(true, false);
									bw.close();
									fw.close();
									br.close();
									fr.close();
									
								}
								else
								{
									if(!files[i].isDirectory())
									{
										Process process = Runtime.getRuntime().exec("cp " + homeFolder + "allNeeded/" + files[i].getName() + " " + homeFolder + "users/"+ folderName + "/");
										System.out.println("cp " + homeFolder + "allNeeded/" + files[i].getName() + " " + homeFolder  + "user/"+ folderName + "/");
										process.waitFor();
									}
									else
									{
										String name = files[i].getName();
										Process process = Runtime.getRuntime().exec("mkdir /var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/" + name);
										process.waitFor();
										
										File ss[] = files[i].listFiles();
										for(int j = 0 ; j <ss.length; j++)
										{
											if(!ss[j].getName().equals("contact_network_from_PDB.rb"))
											{
												Process cp = Runtime.getRuntime().exec("cp " + homeFolder + "allNeeded/" + name + "/"+ ss[j].getName() + " " + homeFolder + "users/"+ folderName + "/" + name + "/");
												System.out.println("cp " + homeFolder + "allNeeded/" + name + "/"+ ss[j].getName() + " " + homeFolder + "users/"+ folderName + "/" + name + "/");
												cp.waitFor();
											}
											else
											{
												File out = new File("/var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/src/contact_network_from_PDB.rb");
												out.setExecutable(true, false);
												out.setReadable(true, false);
												out.setWritable(true, false);
												
												FileReader fr  = new FileReader(ss[j]);
												BufferedReader br = new BufferedReader(fr);
												
												FileWriter fw = new FileWriter(out);
												BufferedWriter bw = new BufferedWriter(fw);
												
												String str = br.readLine();
												while(str!=null)
												{
													if(str.contains("/tmp/"))
													{
														String tmp = str.replaceAll("/tmp/", "/var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/tmp/");
														bw.write(tmp + "\n");
														bw.flush();
													}
													else
													{
														bw.write(str + "\n");
														bw.flush();
													}
													str = br.readLine();
												}
												bw.close();
												fw.close();
												br.close();
												fr.close();
											}
										}
									}
								}
							}
						}
						
						Process d = Runtime.getRuntime().exec("mkdir /var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/tmp/");
						d.waitFor();
				}catch(Exception e)
				{
				
				}
				
				try
				{
					File file = new File(homeFolder + "users/" + folderName + "/output.log");
					FileWriter fw= new FileWriter(file, true);
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write("job ID: " + folderName + "\n");
					bw.write("email: " + email + "\n");
					bw.write("PDB:" + pdbName + "\n");
					bw.write("Chain:" + chain + "\n");
					bw.write("Ion:" + ion + "\n");
					bw.write("Task:" + taskName + "\n");
					bw.flush();
					bw.close();
					fw.close();
				}catch(Exception e)
				{}
				
				
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/accepted.jsp");
				request.setAttribute("id", folderName);
				request.setAttribute("email", email);
				dispatcher.forward(request, response);
				
				runThread run = new runThread();
				run.setFolder(folderName, path, email, ion, pdbName, chain, homeFolder);
				//run.run();
				new Thread(run).start();
			}
			else
			{
				int count = Integer.parseInt(fasta);
				String toTell1 = "";
				String toTell2 = "";
				if(count==0)
				{
					toTell1 = "<li>The fasta format of pdb sequence is not correct.</li>";
				}
				if(count >1)
				{
					toTell1 = "<li>There are more than one fasta sequences in the file you uploaded.</li>";
				}
				
				if(!aa.equals("true"))
				{
					toTell2 = "<li>There are invalid amino acids in the fasta sequence file you uploaded.</li>";
				}
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
				request.setAttribute("content", toTell1 +toTell2 );
				dispatcher.forward(request, response);
				
			}
			
		}catch(Exception e)
		{
		
		}
			
	}
	
	public static void downloadPDB(String pdbID, String path) throws Exception
	{
		File pdbfile = new File(path + "/" + pdbID + ".pdb");
		FileWriter fw = new FileWriter(pdbfile);
		BufferedWriter bw = new BufferedWriter(fw);
		
		String url = "http://www.rcsb.org/pdb/files/" + pdbID + ".pdb";
		URL U = new URL(url);
		HttpURLConnection connection = (HttpURLConnection)U.openConnection();
		connection.connect();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		while ((line = in.readLine())!= null)
		{
			bw.write(line + "\n");
			bw.flush();
		}
		
		
		bw.close();
		fw.close();
	}
	
	public static void uploadFiles(String fileName, String path, FileItem item) throws Exception
	{
		System.out.println("========" + fileName);
		File uploadFile = new File(path + "/" + fileName);
		FileWriter fw = new FileWriter(uploadFile);
		BufferedWriter bw = new BufferedWriter(fw);
		
		InputStream uploadStream = item.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(uploadStream));
		
		String line = br.readLine();
		while(line!=null)
		{
			//System.out.println(line);
			bw.write(line + "\n");
			bw.flush();
			line = br.readLine();
		}
		br.close();
		uploadStream.close();
		bw.close();
		fw.close();
		
		uploadFile.setExecutable(true, false);
		uploadFile.setReadable(true, false);
		uploadFile.setWritable(true, false);
	}
}

