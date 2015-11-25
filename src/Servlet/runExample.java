package Servlet;

import java.io.*;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Beans.emailToUser;
import Beans.getResults;
import Beans.runModel;

/**
 * Servlet implementation class runExample
 */
@WebServlet("/runExample")
public class runExample extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;
    
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public runExample() {
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
		System.out.println("running example...");
		
		String folderName = "";
	    String path = "";
	    String email = "";
	    String homeFolder = "/var/lib/tomcat7/webapps/ROOT/";
	    String ion = "Ni";
	    String pdb = "1APN";
	    String chain = "A";
		
		email = (String)request.getParameter("emailex");
		
		long time = System.currentTimeMillis();
		Random ran = new Random(time);
		folderName = Long.toString(time)+Long.toString(ran.nextLong());
		File folder = new File(homeFolder  + "users/" + folderName + "/");
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
			}
			else
			{
				System.out.println("can not make folder");
				System.exit(1);
			}
		}
		
		//copy all files from example folder to current folder
		try
		{
			File example = new File(homeFolder + "Example/");
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
							Process process = Runtime.getRuntime().exec("cp " + homeFolder + "Example/" + files[i].getName() + " " + homeFolder + "users/"+ folderName + "/");
							System.out.println("cp " + homeFolder + "Example/" + files[i].getName() + " " + homeFolder  + "user/"+ folderName + "/");
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
									Process cp = Runtime.getRuntime().exec("cp " + homeFolder + "Example/" + name + "/"+ ss[j].getName() + " " + homeFolder + "users/"+ folderName + "/" + name + "/");
									System.out.println("cp " + homeFolder + "Example/" + name + "/"+ ss[j].getName() + " " + homeFolder + "users/"+ folderName + "/" + name + "/");
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
			File file = new File(homeFolder + "users/" +folderName + "/output.log");
			FileWriter fw= new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("job ID: " + folderName + "\n");
			bw.write("email: " + email + "\n");
			bw.write("PDB:1APN\n");
			bw.write("Chain:A\n");
			bw.write("Ion:Ni\n");
			bw.write("task: example prediction for Ni ion");
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
		run.setFolder(folderName, path, email, ion, pdb, chain, homeFolder);
		//run.run();
		new Thread(run).start();
		
	}

}
