package com.blore.dev;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class IPTypeDeterminer {
	
	public static boolean isPrivateUsingRegex(String ip)
	{
		String privateIpRangePattern="(^(10\\.[0-9]{1,3}|172\\.(3[01]|2[0-9]|1[6-9])|192\\.168)\\.([0-9]{1,3})\\.([0-9]{1,3})$)";
		return MatchedPattern(ip, privateIpRangePattern) != null ;	
	}
	
	private static String MatchedPattern(String response, String respPattern) {
		String[] split = response.split("\n");
		String match = null;
		Pattern pattern = Pattern.compile(respPattern);
		if(split.length == 0)
			return "";
		else
		{
		for (String tempString : split) {
			tempString = tempString + "\r";
			Matcher matcher = pattern.matcher(tempString);
			if (matcher.find()) {
				if (matcher.groupCount() > 0){
					for(int index=1;index<=matcher.groupCount();index++)
						if(matcher.group(index)!=null){
							match = matcher.group(index);
							break;
						}
				}
				else {
					match = matcher.group();
				}
				if (match == null || match.length() == 0)
					continue;
				break;
			}
		}
		}
		if (match != null) {
			return	match.replace("\r", "");
		}
		//System.out.println("ParserUtil.MatchPattern() match is "+ match);
		return match;
	}
	
	private static final String IPADDRESS_PATTERN = 
			"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	
	public static boolean validate(final String ip){	
		  Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
		  Matcher matcher = pattern.matcher(ip);
		  return matcher.matches();	    	    
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args != null && args.length > 0 && args[0] != null && IPTypeDeterminer.validate(args[0])){
			System.out.println("The given IP ("+args[0]+")'s type is "+(IPTypeDeterminer.isPrivateUsingRegex(args[0])?"private":"public"));
		}
		else{
			System.err.println("Nil or invalid IP string as input");
			System.err.println("Sample inputs are 10.95.22.95,122.166.123.117 etc ");
		}
	}

}
