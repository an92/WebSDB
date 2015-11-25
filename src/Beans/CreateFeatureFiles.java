package Beans;

public class CreateFeatureFiles 
{
	private String folderName = "";
	
	public void setParams(String name)
	{
		folderName = name;
	}
	
	public String getParams()
	{
		return folderName;
	}
	
	public void generateFeatureFiles() throws Exception
	{
		Process process = Runtime.getRuntime().exec("perl ./all_features.pl");
		process.waitFor();
	}
}
