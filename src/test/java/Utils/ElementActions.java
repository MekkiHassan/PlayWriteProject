package Utils;

import base.BaseTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Selectors;

public class ElementActions {


    public static void sendData(Locator locator, String data){
        locator.fill(data);
    }
    public static void CLICK(Locator locator){
        locator.click();
    }

    // Get text using Locator
    public static String getText(Locator locator){
        return locator.innerText();
    }

}
