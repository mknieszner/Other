package ipcalc;

class Subnet {
private static final String SUBNET_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\/" + "(\\d|[1-2]\\d|3[0-2])$";

  private static final String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

  private Pattern ipPattern = Pattern.compile(IPADDRESS_PATTERN);
  private Pattern subnetPattern = Pattern.compile(SUBNET_PATTERN);
  private Matcher matcher;

//Calculation of first address and last address

  public String getSubnetIPs(final String bip, final int mask, final boolean firstOrLast) {
    final int bits = 32 - mask;
    //System.out.println("The subnet mask is = " + mask);
    //System.out.println("Number of bits required for address = " + bits);

    if (firstOrLast) {
      int fbip[] = new int[32];
      for (int i = 0; i < 32; i++) fbip[i] = (int) bip.charAt(i) - 48; //convert cahracter 0,1 to integer 0,1
      for (int i = 31; i > 31 - bits; --i)//Get first address by ANDing last n bits with 0
        fbip[i] &= 0;
      String fip[] = {"", "", "", ""};
      for (int i = 0; i < 32; i++)
        fip[i / 8] = new String(fip[i / 8] + fbip[i]);
      //System.out.print("First address is = ");
      StringBuilder firstIP = new StringBuilder();
      for (int i = 0; i < 4; i++) {
        firstIP.append(Integer.parseInt(fip[i], 2));
        //System.out.print(Integer.parseInt(fip[i], 2));
        if (i != 3) {
          firstIP.append('.');
          //System.out.print(".");
        }
      }
      return firstIP.toString();
    }
    System.out.println();

    int lbip[] = new int[32];
    for (int i = 0; i < 32; i++) lbip[i] = (int) bip.charAt(i) - 48; //convert cahracter 0,1 to integer 0,1
    for (int i = 31; i > 31 - bits; i--)//Get last address by ORing last n bits with 1
      lbip[i] |= 1;
    String lip[] = {"", "", "", ""};
    for (int i = 0; i < 32; i++)
      lip[i / 8] = new String(lip[i / 8] + lbip[i]);
    //System.out.print("Last address is = ");
    StringBuilder lastIP = new StringBuilder();
    for (int i = 0; i < 4; i++) {
      lastIP.append(Integer.parseInt(lip[i], 2));
      //System.out.print(Integer.parseInt(lip[i], 2));
      if (i != 3) {
        lastIP.append('.');
        //System.out.print(".");
      }
    }
    System.out.println();
    return lastIP.toString();
  }

  public Long getLongIP(final String ip) {
    return Long.parseLong(getBinaryIP(ip),2);
  }

  public String getBinaryIP(final String ip) {
    String split_ip[] = ip.split("\\."); //SPlit the string after every .
    String split_bip[] = new String[4]; //split binary ip
    String bip = "";
    for (int i = 0; i < 4; i++) {
      split_bip[i] = appendZeros(Integer.toBinaryString(Integer.parseInt(split_ip[i]))); // “18” => 18 => 10010 => 00010010
      bip += split_bip[i];
    }
    //System.out.println("IP in binary is " + bip);
    return bip;
  }

  private static String appendZeros(String s) {
    String temp = "00000000";
    return temp.substring(s.length()) + s;
  }

  public Subnet() {
    ipPattern = Pattern.compile(IPADDRESS_PATTERN);
    subnetPattern = Pattern.compile(SUBNET_PATTERN);
  }

  public boolean validateIP(final String ip) {
    matcher = ipPattern.matcher(ip);
    return matcher.matches();
  }

  public boolean validateSubnet(final String subnet) {
    matcher = subnetPattern.matcher(subnet);
    return matcher.matches();
  }

  public boolean belongsToSubnetInRow(final String wiersz, final String ip) {
    //System.out.println("wiersz" + wiersz);
    if (wiersz.contains(";")) {
      final String ipRange = wiersz.substring(0, wiersz.indexOf(";"));
      //System.out.println("ipRange" + ipRange);
      if (ipRange.contains("-")) {
        //System.out.println("range" + ipRange);
        String firstIP = ipRange.split("-")[0];
        String lastIP = ipRange.split("-")[1];
        if (getLongIP(firstIP) <= getLongIP(ip) && getLongIP(ip) <= getLongIP(lastIP)) {
          return true;
        }
      } else if (validateSubnet(ipRange)) {
          //System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!" + ipRange);
        String subnetIP = ipRange.split("/")[0];
        System.out.println("subnet" + subnetIP);
        String subnetMask = ipRange.split("/")[1];
        System.out.println("mask" + subnetIP);
        String firstIP = getSubnetIPs(getBinaryIP(subnetIP), Integer.parseInt(subnetMask), true);
        String lastIP = getSubnetIPs(getBinaryIP(subnetIP), Integer.parseInt(subnetMask), false);
        if (getLongIP(firstIP) <= getLongIP(ip) && getLongIP(ip) <= getLongIP(lastIP)) {
          return true;
        }
      } else {
        return false;
      }

      return false;
    } else {
      return false;
    }
  }
}
