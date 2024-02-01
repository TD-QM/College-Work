public class ConfigFactory
{
    // Create a Config object reading from command line arguments.
    public static Config createConfig(String systemSettings)
    {
        switch(systemSettings)
        {
            case "OSX":        return new ConfigOSX();
            case "OSXCHROME":  return new ConfigOSXChrome();
            case "OSXFIREFOX":
            case "OSXSAFARI":  return new ConfigOSXSafari();
            case "WINFIREFOX": return new ConfigWinFirefox();
            default :          return new ConfigASCII(); 
        }
    }
}